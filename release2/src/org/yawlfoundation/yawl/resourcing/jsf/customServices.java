/*
 * This file is made available under the terms of the LGPL licence.
 * This licence can be retrieved from http://www.gnu.org/copyleft/lesser.html.
 * The source remains the property of the YAWL Foundation.  The YAWL Foundation is a
 * collaboration of individuals and organisations who are committed to improving
 * workflow technology.
 */

package org.yawlfoundation.yawl.resourcing.jsf;

import com.sun.rave.web.ui.appbase.AbstractPageBean;
import com.sun.rave.web.ui.component.*;
import org.yawlfoundation.yawl.elements.YAWLServiceReference;

import javax.faces.component.UIColumn;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.FacesException;
import java.util.List;

/**
 *  Backing bean for the case mgt page.
 *
 *  @author Michael Adams
 *  BPM Group, QUT Australia
 *  v0.1, 23/01/2008
 *
 *
 *  Last Date: 09/04/2008
 */

public class customServices extends AbstractPageBean {

    private int __placeholder;

    private void _init() throws Exception { }


    public customServices() { }


    public void init() {
        super.init();

        try {
            _init();
        } catch (Exception e) {
            log("customServices Initialization Failure", e);
            throw e instanceof FacesException ? (FacesException) e: new FacesException(e);
        }
    }

    public void preprocess() { }

    public void destroy() { }


    // Return references to scoped data beans

    protected ApplicationBean getApplicationBean() {
        return (ApplicationBean)getBean("ApplicationBean");
    }

    protected SessionBean getSessionBean() {
        return (SessionBean)getBean("SessionBean");
    }

    protected RequestBean getRequestBean() {
        return (RequestBean)getBean("RequestBean");
    }


    /********************************************************************************/

    private Page page1 = new Page();
    
    public Page getPage1() { return page1; }
    
    public void setPage1(Page p) { page1 = p; }
    

    private Html html1 = new Html();
    
    public Html getHtml1() { return html1; }
    
    public void setHtml1(Html h) { html1 = h; }
    

    private Head head1 = new Head();
    
    public Head getHead1() { return head1; }
    
    public void setHead1(Head h) { head1 = h; }
    

    private Link link1 = new Link();
    
    public Link getLink1() { return link1; }
    
    public void setLink1(Link l) { link1 = l; }
    

    private Body body1 = new Body();
    
    public Body getBody1() { return body1; }
    
    public void setBody1(Body b) { body1 = b; }
    

    private Form form1 = new Form();
    
    public Form getForm1() { return form1; }
    
    public void setForm1(Form f) { form1 = f;}


    private PanelLayout pnlAddService = new PanelLayout();

    public PanelLayout getPnlAddService() { return pnlAddService; }

    public void setPnlAddService(PanelLayout pl) { pnlAddService = pl; }


    private StaticText staticText1 = new StaticText();

    public StaticText getStaticText1() { return staticText1; }

    public void setStaticText1(StaticText st) { staticText1 = st; }


    private TextField txtName = new TextField();

    public TextField getTxtName() { return txtName; }

    public void setTxtName(TextField tf) { txtName = tf; }


    private TextField txtURL = new TextField();

    public TextField getTxtURL() { return txtURL; }

    public void setTxtURL(TextField tf) { txtURL = tf; }


    private TextArea txtDescription = new TextArea();

    public TextArea getTxtDescription() { return txtDescription; }

    public void setTxtDescription(TextArea ta) { txtDescription = ta; }


    private PanelLayout pnlServices = new PanelLayout();

    public PanelLayout getPnlServices() { return pnlServices; }

    public void setPnlServices(PanelLayout pl) { pnlServices = pl; }


    private StaticText staticText2 = new StaticText();

    public StaticText getStaticText2() { return staticText2; }

    public void setStaticText2(StaticText st) { staticText2 = st; }


    private HtmlDataTable dataTable1 = new HtmlDataTable();

    public HtmlDataTable getDataTable1() { return dataTable1; }

    public void setDataTable1(HtmlDataTable hdt) { dataTable1 = hdt; }


    private UIColumn colName = new UIColumn();

    public UIColumn getColName() { return colName; }

    public void setColName(UIColumn uic) { colName = uic; }


    private HtmlOutputText colNameRows = new HtmlOutputText();

    public HtmlOutputText getColNameRows() { return colNameRows; }

    public void setColNameRows(HtmlOutputText hot) { colNameRows = hot; }


    private HtmlOutputText colNameHeader = new HtmlOutputText();

    public HtmlOutputText getColNameHeader() { return colNameHeader; }

    public void setColNameHeader(HtmlOutputText hot) { colNameHeader = hot; }


    private UIColumn colURI = new UIColumn();

    public UIColumn getColURI() { return colURI; }

    public void setColURI(UIColumn uic) { colURI = uic; }


    private HtmlOutputText colURIRows = new HtmlOutputText();

    public HtmlOutputText getColURIRows() { return colURIRows; }

    public void setColURIRows(HtmlOutputText hot) { colURIRows = hot; }


    private HtmlOutputText colURIHeader = new HtmlOutputText();

    public HtmlOutputText getColURIHeader() { return colURIHeader; }

    public void setColURIHeader(HtmlOutputText hot) { colURIHeader = hot; }


    private UIColumn colDescription = new UIColumn();

    public UIColumn getColDescription() { return colDescription; }

    public void setColDescription(UIColumn uic) { colDescription = uic; }


    private HtmlOutputText colDescriptionRows = new HtmlOutputText();

    public HtmlOutputText getColDescriptionRows() { return colDescriptionRows; }

    public void setColDescriptionRows(HtmlOutputText hot) { colDescriptionRows = hot; }


    private HtmlOutputText colDescriptionHeader = new HtmlOutputText();

    public HtmlOutputText getColDescriptionHeader() { return colDescriptionHeader; }

    public void setColDescriptionHeader(HtmlOutputText hot) { colDescriptionHeader = hot; }


    private UIColumn colSBar = new UIColumn();

    public UIColumn getColSBar() { return colSBar; }

    public void setColSBar(UIColumn uic) { colSBar = uic; }


    private Button btnRemove = new Button();

    public Button getBtnRemove() { return btnRemove; }

    public void setBtnRemove(Button b) { btnRemove = b; }


    private Button btnAdd = new Button();

    public Button getBtnAdd() { return btnAdd; }

    public void setBtnAdd(Button b) { btnAdd = b; }


    private Button btnClear = new Button();

    public Button getBtnClear() { return btnClear; }

    public void setBtnClear(Button b) { btnClear = b; }


    private HiddenField hdnRowIndex = new HiddenField();

    public HiddenField getHdnRowIndex() { return hdnRowIndex; }

    public void setHdnRowIndex(HiddenField hf) { hdnRowIndex = hf; }


    private Script script1 = new Script();

    public Script getScript1() { return script1; }

    public void setScript1(Script s) { script1 = s; }


    private List<YAWLServiceReference> dataList ;

    public List<YAWLServiceReference> getDataList() { return dataList; }

    public void setDataList(List<YAWLServiceReference> d) { dataList = d; }


    /********************************************************************************/

    private MessagePanel msgPanel = getSessionBean().getMessagePanel();

    /**
     * Overridden method that is called immediately before the page is rendered
     */
    public void prerender() {
        getSessionBean().checkLogon();
        getSessionBean().getMessagePanel().show(538, 150, "absolute");
        getSessionBean().setActivePage(ApplicationBean.PageRef.customServices);
    }


    // remove the selected service from the engine
    public String btnRemove_action() {
        try {
            Integer selectedRowIndex = new Integer((String) hdnRowIndex.getValue());
            getSessionBean().removeRegisteredService(selectedRowIndex - 1);
        }
        catch (NumberFormatException nfe) {
            msgPanel.error("No service selected to remove.");
        }
        return null;
    }

    // add a new service to the engine
    public String btnAdd_action() {
        String name = (String) txtName.getText() ;
        String uri = (String) txtURL.getText();
        if ((name != null) && (uri != null)) {
            String doco = (String) txtDescription.getText();
            getSessionBean().addRegisteredService(name, uri, doco);
            clearInputs();
        }
        else
            msgPanel.warn("Add Service: name and URI required.");

        return null;
    }

    
    public String btnClear_action() {
        clearInputs();
        return null;
    }

    
    private void clearInputs() {
        txtName.setText("");
        txtURL.setText("");
        txtDescription.setText("");
    }
    
}

