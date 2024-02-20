package com.titania.harp.workflow;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.CookieSpecRegistries;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jdom2.Document;
import org.yawlfoundation.yawl.elements.YAWLServiceReference;
import org.yawlfoundation.yawl.elements.YTask;
import org.yawlfoundation.yawl.elements.state.YIdentifier;
import org.yawlfoundation.yawl.engine.ObserverGateway;
import org.yawlfoundation.yawl.engine.YSpecificationID;
import org.yawlfoundation.yawl.engine.YWorkItem;
import org.yawlfoundation.yawl.engine.YWorkItemStatus;
import org.yawlfoundation.yawl.engine.announcement.YAnnouncement;
import org.yawlfoundation.yawl.engine.announcement.YEngineEvent;
import org.yawlfoundation.yawl.engine.interfce.EngineGateway;
import org.yawlfoundation.yawl.engine.interfce.EngineGatewayImpl;
import org.yawlfoundation.yawl.exceptions.YPersistenceException;
import org.yawlfoundation.yawl.util.JDOMUtil;

public class TitaniaObserver implements ObserverGateway {

    private String portalUrl;
    private String loginPage;;
    private String observerPage;
    private String _user;
    private String _pwd;
    private HttpClientContext _context;
    private CloseableHttpClient _httpclient;

    private EngineGateway _engine;

    protected static final Logger _logger = LogManager.getLogger(TitaniaObserver.class);

    public TitaniaObserver() {
	final Properties props = new Properties();
	try (final InputStream is = this.getClass()
		.getResourceAsStream("/com/titania/harp/workflow/titania-observer.properties")) {
	    props.load(is);
	    this.portalUrl = StringUtils.removeEnd(props.getProperty("portalUrl"), "/");
	    this.loginPage = props.getProperty("loginPage");
	    this.observerPage = props.getProperty("observerPage");
	    this._user = props.getProperty("user");
	    this._pwd = props.getProperty("password");
	} catch (IOException e) {
	    _logger.error("error loading properties", e);
	}

	_httpclient = HttpClients.custom().setConnectionManager(HttpClientConnectionManager.getConnectionManager())
		.setDefaultCookieSpecRegistry(CookieSpecRegistries.createDefault())
		.setRedirectStrategy(new LaxRedirectStrategy()).build();

	try {
	    _engine = new EngineGatewayImpl(false);
	} catch (YPersistenceException e) {
	    _logger.error("error getting engine instance", e);
	}

	/*
	 * try { YEngine engine = YEngine.getInstance(false);
	 * engine.registerInterfaceBObserverGateway(this); } catch
	 * (YPersistenceException e) { _logger.error("error getting engine instance",
	 * e); } catch (YAWLException e) {
	 * _logger.error("error registering observer gateway", e); }
	 */
    }

    @Override
    public String getScheme() {
	return "http";
    }

    @Override
    public void announceFiredWorkItem(YAnnouncement announcement) {
	Map<String, String> params = new HashMap<String, String>();
	params.put("action", YEngineEvent.ITEM_ADD.label());
	params.put("workItem", announcement.getItem().toXML());
	executePost(params);
    }

    @Override
    public void announceWorkItemStatusChange(Set<YAWLServiceReference> services, YWorkItem workItem,
	    YWorkItemStatus oldStatus, YWorkItemStatus newStatus) {
	Map<String, String> params = new HashMap<String, String>();
	params.put("action", YEngineEvent.ITEM_STATUS.label());
	params.put("workItem", workItem.toXML());
	params.put("oldStatus", oldStatus.toString());
	params.put("newStatus", newStatus.toString());
	executePost(params);
    }

    @Override
    public void announceCancelledWorkItem(YAnnouncement announcement) {
	Map<String, String> params = new HashMap<String, String>();
	params.put("action", YEngineEvent.ITEM_CANCEL.label());
	params.put("workItem", announcement.getItem().toXML());
	executePost(params);
    }

    @Override
    public void announceTimerExpiry(YAnnouncement announcement) {
	Map<String, String> params = new HashMap<String, String>();
	params.put("action", YEngineEvent.TIMER_EXPIRED.label());
	params.put("workItem", announcement.getItem().toXML());
	executePost(params);
    }

    @Override
    public void announceCaseCompletion(YAWLServiceReference yawlService, YIdentifier caseID, Document caseData) {
	Map<String, String> params = new HashMap<String, String>();
	params.put("action", YEngineEvent.CASE_COMPLETE.label());
	params.put("caseID", caseID.get_idString());
	params.put("casedata", JDOMUtil.documentToString(caseData));
	executePost(params);
    }

    @Override
    public void announceCaseCompletion(Set<YAWLServiceReference> services, YIdentifier caseID, Document caseData) {
	announceCaseCompletion(services.toArray(new YAWLServiceReference[] {})[0], caseID, caseData);
    }

    @Override
    public void announceCaseStarted(Set<YAWLServiceReference> services, YSpecificationID specID, YIdentifier caseID,
	    String launchingService, boolean delayed) {
	Map<String, String> params = new HashMap<String, String>();
	params.put("action", YEngineEvent.CASE_START.label());
	params.put("caseID", caseID.get_idString());
	params.put("specuri", specID.getUri());
	params.put("specversion", specID.getVersionAsString());
	params.put("specidentifier", specID.getIdentifier());
	params.put("launchingService",
		services.stream().map(s -> s.get_serviceName()).collect(Collectors.joining(",")));
	params.put("delayed", String.valueOf(delayed));
	executePost(params);
    }

    @Override
    public void announceCaseSuspended(Set<YAWLServiceReference> services, YIdentifier caseID) {
	Map<String, String> params = new HashMap<String, String>();
	params.put("action", YEngineEvent.CASE_SUSPENDED.label());
	params.put("caseID", caseID.get_idString());
	executePost(params);
    }

    @Override
    public void announceCaseSuspending(Set<YAWLServiceReference> services, YIdentifier caseID) {
	Map<String, String> params = new HashMap<String, String>();
	params.put("action", YEngineEvent.CASE_SUSPENDING.label());
	params.put("caseID", caseID.get_idString());
	executePost(params);
    }

    @Override
    public void announceCaseResumption(Set<YAWLServiceReference> services, YIdentifier caseID) {
	Map<String, String> params = new HashMap<String, String>();
	params.put("action", YEngineEvent.CASE_RESUMED.label());
	params.put("caseID", caseID.get_idString());
	executePost(params);
    }

    @Override
    public void announceCaseCancellation(Set<YAWLServiceReference> services, YIdentifier id) {
	Map<String, String> params = new HashMap<String, String>();
	params.put("action", YEngineEvent.CASE_CANCELLED.label());
	params.put("caseID", id.get_idString());
	executePost(params);
    }

    @Override
    public void announceDeadlock(Set<YAWLServiceReference> services, YIdentifier id, Set<YTask> tasks) {
	Map<String, String> params = new HashMap<String, String>();
	params.put("action", YEngineEvent.CASE_DEADLOCKED.label());
	params.put("caseID", id.get_idString());
	params.put("tasks", tasks.stream().map(t -> t.getID()).collect(Collectors.joining(",")));
	executePost(params);
    }

    @Override
    public void announceEngineInitialised(Set<YAWLServiceReference> services, int maxWaitSeconds) {
	Map<String, String> params = new HashMap<String, String>();
	params.put("action", YEngineEvent.ENGINE_INIT.label());
	executePost(params);
    }

    @Override
    public void shutdown() {
	if (null != _httpclient) {
	    try {
		_httpclient.close();
	    } catch (IOException e) {
		_logger.error("error closing httpclient", e);
		_httpclient = null;
	    }
	}
    }

    private boolean checkOrConnect() {
	boolean rc = false;
	CloseableHttpResponse resp = null;
	try {
	    if (null != _context) {
		// Try existing connection and context
		resp = _httpclient.execute(new HttpHead(portalUrl), _context);
		if (resp.getStatusLine().getStatusCode() == 200) {
		    resp.close();
		    return true;
		}
	    }

	    List<NameValuePair> loginParams = new ArrayList<NameValuePair>();
	    loginParams.add(new BasicNameValuePair("username", _user));
	    loginParams.add(new BasicNameValuePair("password", _pwd));
	    UrlEncodedFormEntity entity = new UrlEncodedFormEntity(loginParams, Consts.UTF_8);
	    HttpPost post = new HttpPost(portalUrl + loginPage);
	    post.setEntity(entity);
	    resp = _httpclient.execute(post, _context);
	    rc = resp.getStatusLine().getStatusCode() == 200;
	    resp.close();
	} catch (ClientProtocolException e) {
	    _logger.error("error checking connection", e);
	} catch (IOException e) {
	    _logger.warn("failed to close http response", e);
	    resp = null;
	}
	return rc;
    }

    private boolean executePost(Map<String, String> params) {
	boolean rc = checkOrConnect();
	if (!rc)
	    return rc;
	CloseableHttpResponse resp = null;
	try {
	    List<NameValuePair> postParams = new ArrayList<NameValuePair>();
	    params.forEach((p, v) -> postParams.add(new BasicNameValuePair(p, v)));
	    UrlEncodedFormEntity entity = new UrlEncodedFormEntity(postParams, Consts.UTF_8);
	    HttpPost post = new HttpPost(portalUrl + observerPage);
	    post.setEntity(entity);
	    resp = _httpclient.execute(post, _context);
	    rc = resp.getStatusLine().getStatusCode() == 200;
	    resp.close();
	} catch (ClientProtocolException e) {
	    _logger.error("error executing POST", e);
	} catch (IOException e) {
	    _logger.warn("failed to close http response", e);
	    resp = null;
	}
	return rc;
    }

    private static class HttpClientConnectionManager {

	private static PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();

	public static PoolingHttpClientConnectionManager getConnectionManager() {
	    return cm;
	}

    }

}
