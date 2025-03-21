package org.yawlfoundation.yawl.util;

import java.util.Optional;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.simplejavamail.mailer.config.TransportStrategy;

/**
 *
 * @author Michael Adams
 * @date 18/12/2024
 */
public class MailSettings {

    private Logger _logger = LogManager.getLogger(getClass());

    public String host = null;
    public int port = 25;
    public TransportStrategy strategy = TransportStrategy.SMTPS;
    public String user = null;
    public String password = null;
    public String fromName = null;

    public String getHost() {
	return host;
    }

    public void setHost(String host) {
	this.host = host;
    }

    public int getPort() {
	return port;
    }

    public void setPort(int port) {
	this.port = port;
    }

    public TransportStrategy getStrategy() {
	return strategy;
    }

    public void setStrategy(TransportStrategy strategy) {
	this.strategy = strategy;
    }

    public String getUser() {
	return user;
    }

    public void setUser(String user) {
	this.user = user;
    }

    public String getPassword() {
	return password;
    }

    public void setPassword(String password) {
	this.password = password;
    }

    public String getFromName() {
	return fromName;
    }

    public void setFromName(String fromName) {
	this.fromName = fromName;
    }

    public String getSenderName() {
	_logger.debug("getSenderName() returning " + fromName);
	return fromName;
    }

    public void setSenderName(String senderName) {
	this.fromName = senderName;
    }

    public String getFromAddress() {
	return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
	this.fromAddress = fromAddress;
    }

    public String getSenderAddress() {
	_logger.debug("getSenderAddress() returning " + fromAddress);
	return fromAddress;
    }

    public void setSenderAddress(String senderAddress) {
	this.fromAddress = senderAddress;
    }

    public String getToName() {
	return toName;
    }

    public void setToName(String toName) {
	this.toName = toName;
    }

    public String getRecipientName() {
	_logger.debug("MailSettings.getRecipientName() returning " + toName);
	return toName;
    }

    public void setRecipientName(String recipientName) {
	this.toName = recipientName;
    }

    public String getToAddress() {
	return toAddress;
    }

    public void setToAddress(String toAddress) {
	this.toAddress = toAddress;
    }

    public String getRecipientAddress() {
	return toAddress;
    }

    public void setRecipientAddress(String recipientAddress) {
	this.toAddress = recipientAddress;
    }

    public String getCcAddress() {
	return ccAddress;
    }

    public void setCcAddress(String ccAddress) {
	this.ccAddress = ccAddress;
    }

    public String getCC() {
	return ccAddress;
    }

    public void setCC(String cc) {
	this.ccAddress = cc;
    }

    public String getBccAddress() {
	return bccAddress;
    }

    public void setBccAddress(String bccAddress) {
	this.bccAddress = bccAddress;
    }

    public String getBCC() {
	return bccAddress;
    }

    public void setBCC(String bcc) {
	this.bccAddress = bcc;
    }

    public String getSubject() {
	return subject;
    }

    public void setSubject(String subject) {
	this.subject = subject;
    }

    public String getContent() {
	return content;
    }

    public void setContent(String content) {
	_logger.debug(String.format("MailSettings.setContent(%s)", content));
	this.content = content;
    }

    public String fromAddress = null;
    public String toName = null;
    public String toAddress = null;
    public String ccAddress = null;
    public String bccAddress = null;
    public String subject = null;
    public String content = null;

    public MailSettings() {
    }

    public String getSetting(String name) {
	switch (name) {
	case "host":
	    return host;
	case "user":
	    return user;
	case "password":
	    return password;
	case "senderName":
	    return fromName;
	case "senderAddress":
	    return fromAddress;
	case "recipientName":
	    return toName;
	case "recipientAddress":
	    return toAddress;
	case "CC":
	    return ccAddress;
	case "BCC":
	    return bccAddress;
	case "subject":
	    return subject;
	case "content":
	    return content;
	}
	return null;
    }

    public MailSettings copyOf() {
	_logger.debug("enter MailSettings.copyOf()");
	MailSettings settings = new MailSettings();
	settings.setHost(this.host);
	settings.setPort(this.port);
	settings.setStrategy(this.strategy);
	settings.setUser(this.user);
	settings.setPassword(this.password);
	settings.setFromName(this.fromName);
	settings.setFromAddress(this.fromAddress);
	settings.setToName(this.toName);
	settings.setToAddress(this.toAddress);
	settings.setCcAddress(this.ccAddress);
	settings.setBccAddress(this.bccAddress);
	settings.setSubject(this.subject);
	settings.setContent(this.content);
	_logger.debug("copyOf() returning " + settings.toXML());
	return settings;
    }

    public String toXML() {

	XNode node = new XNode("mailsettings");

	node.addChild("host", Optional.ofNullable(this.host).orElse(""));
	node.addChild("port", Optional.ofNullable(this.port).orElse(-1));
	node.addChild("user", Optional.ofNullable(this.user).orElse(""));
	node.addChild("password", Optional.ofNullable(this.password).orElse(""));
	node.addChild("fromname", Optional.ofNullable(this.fromName).orElse(""));
	node.addChild("fromaddress", Optional.ofNullable(this.fromAddress).orElse(""));
	node.addChild("toname", Optional.ofNullable(this.toName).orElse(""));
	node.addChild("toaddress", Optional.ofNullable(this.toAddress).orElse(""));
	node.addChild("CC", Optional.ofNullable(this.ccAddress).orElse(""));
	node.addChild("BCC", Optional.ofNullable(this.bccAddress).orElse(""));

	XNode subjectNd = new XNode("subject");
	subjectNd.addChildren(((new XNodeParser()).parse("<f>" + Optional.ofNullable(this.subject).orElse("") + "</f>")
		.getChildren()));
	node.addChild(subjectNd);

	XNode contentNd = new XNode("content");
	contentNd.addChildren(((new XNodeParser()).parse("<f>" + Optional.ofNullable(this.content).orElse("") + "</f>")
		.getChildren()));
	node.addChild(contentNd);

	_logger.debug("toXML() returning " + node.toString());
	return node.toString();
    }

    public void fromXML(String xml) {
	_logger.debug(String.format("enter fromXML(%s)", xml));
	XNode node = (new XNodeParser()).parse(xml);
	if (node != null) {
	    host = node.getChildText("host");
	    port = StringUtil.strToInt(node.getChildText("port"), 25);
	    user = node.getChildText("user");
	    password = node.getChildText("password");
	    fromName = node.getChildText("fromname");
	    fromAddress = node.getChildText("fromaddress");
	    toName = node.getChildText("toname");
	    toAddress = node.getChildText("toaddress");
	    ccAddress = node.getChildText("CC");
	    bccAddress = node.getChildText("BCC");
	    subject = node.hasChildren("subject") ? node.getChild("subject").toString() : node.getChildText("subject");
	    content = node.hasChildren("content") ? node.getChild("content").toString() : node.getChildText("content");
	}
	_logger.debug("returning from fromXML()");
    }
}
