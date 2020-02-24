/*
 * Copyright (c) 2004-2012 The YAWL Foundation. All rights reserved.
 * The YAWL Foundation is a collaboration of individuals and
 * organisations who are committed to improving workflow technology.
 *
 * This file is part of YAWL. YAWL is free software: you can
 * redistribute it and/or modify it under the terms of the GNU Lesser
 * General Public License as published by the Free Software Foundation.
 *
 * YAWL is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General
 * Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with YAWL. If not, see <http://www.gnu.org/licenses/>.
 */

package org.yawlfoundation.yawl.resourcing.codelets;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.jdom2.Element;
import org.yawlfoundation.yawl.elements.data.YParameter;
import org.yawlfoundation.yawl.resourcing.ResourceManager;
import org.yawlfoundation.yawl.resourcing.datastore.orgdata.ResourceDataSet;
import org.yawlfoundation.yawl.resourcing.resource.Participant;

/**
 * @author Michael Adams
 * @date 7/03/2011
 */
public class DirectReports extends AbstractCodelet {

    public DirectReports() {
	super();
	setDescription("This codelet returns a list of userids that report directly to the<br> "
		+ "participant with the userid specified.<br> " + "Input: userid (string type).<br>"
		+ "Output: directreportids (string type, comma-separated list of ids)");
    }

    public Element execute(Element inData, List<YParameter> inParams, List<YParameter> outParams)
	    throws CodeletExecutionException {
	ResourceManager rm = ResourceManager.getInstance();
	setInputs(inData, inParams, outParams);
	String userid = getValue("userid");
	Participant p = rm.getParticipantFromUserID(userid);
	if (p == null) {
	    throw new CodeletExecutionException("Unknown userid: " + userid);
	}

	ResourceDataSet rds = rm.getOrgDataSet();

	String reportIds = "";
	Set<Participant> reports = rds.getParticipantsReportingTo(p.getID());
	if (null != reports) {
	    reportIds = reports.stream().filter(a -> rds.getImmediateSupervisor(a).equals(p)).map(a -> a.getUserID())
		    .collect(Collectors.joining(","));
	}
	setParameterValue("directreportids", reportIds);

	return getOutputData();
    }

    public List<YParameter> getRequiredParams() {
	List<YParameter> params = new ArrayList<YParameter>();

	YParameter param = new YParameter(null, YParameter._INPUT_PARAM_TYPE);
	param.setDataTypeAndName("string", "userid", XSD_NAMESPACE);
	param.setDocumentation("The userid of a participant");
	params.add(param);

	param = new YParameter(null, YParameter._OUTPUT_PARAM_TYPE);
	param.setDataTypeAndName("string", "directreportids", XSD_NAMESPACE);
	param.setDocumentation("The userids of the given participant's direct reports");
	params.add(param);

	return params;
    }

}