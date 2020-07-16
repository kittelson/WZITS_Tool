package GUI.PDFReports;

import GUI.MainController;
import GUI.PDFReports.XML.XMLGenerator;
import GUI.Tables.Step1TableHelper;
import core.*;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import javax.imageio.ImageIO;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class FactSheetReportCreator {

    private static DecimalFormat formatter_0f = new DecimalFormat("#,##0");
    private static final DecimalFormat formatter1d = new DecimalFormat("#0.0");
    private static final DecimalFormat formatter2d = new DecimalFormat("#0.00");
    private DecimalFormat formatter;
    private String dateSeparator;
//    private String analyst;
//    private String organization;
//    private String scenario;

    private boolean includeSummaryPMTable = true;
    private boolean includeMap = false;
    private boolean includeDemand = false;
    private boolean includeSegmentGeometryTable = false;
    private boolean includeContourSpd = false;

    private boolean includeScenarioCompTable = false;
    private boolean includeScenarioDetails = false;

    public FactSheetReportCreator() {
//        this.scenario = scenario;
//        this.analyst = analyst;
//        this.organization = organization;
        formatter = new DecimalFormat("00");
        dateSeparator = "/";
    }

    private String[][] getAnalysisInformationDetails() {
        DateFormat dateformat = new SimpleDateFormat("MM" + dateSeparator + "dd" + dateSeparator + "yyy");
        Date date = new Date();
        String[][] analysisInformation = new String[5][2];
        analysisInformation[0][0] = "Project Name";
        analysisInformation[0][1] = "removed Seed File reference"; //seed.getValueString(CEConst.IDS_PROJECT_NAME, 0, 0, scen, -1);
        analysisInformation[1][0] = "Scenario Name";
//        analysisInformation[1][1] = scenario;
        analysisInformation[2][0] = "Analyst";
//        analysisInformation[2][1] = analyst;
        analysisInformation[3][0] = "Organization";
//        analysisInformation[3][1] = organization;
        analysisInformation[4][0] = "Analysis Date (MM/DD/YYYY)";
        analysisInformation[4][1] = dateformat.format(date);
        return analysisInformation;
    }

    private String[][] getProjectInformationTable(Project project) {
        DateFormat dateformat = new SimpleDateFormat("MM" + dateSeparator + "dd" + dateSeparator + "yyy");
        Date date = new Date();
        String[][] projectInfo = new String[6][2];
        int ri = 0;
        projectInfo[ri][0] = "State Agency";
        projectInfo[ri][1] = project.getAgency() != null ? project.getAgency() : "";
        ri++;
        projectInfo[ri][0] = "Analyst";
        projectInfo[ri][1] = project.getAnalyst() != null ? project.getAnalyst() : "";
        ri++;
        projectInfo[ri][0] = "Date";
        projectInfo[ri][1] = project.getDateString();
        ri++;
        projectInfo[ri][0] = "Project Name";
        projectInfo[ri][1] = project.getName() != null ? project.getName() : "";
//        ri++;
//        projectInfo[ri][0] = "Project Description:";
//        projectInfo[ri][1] = project.getDescription() != null ? project.getDescription() : "";
//        ri++;
//        projectInfo[ri][0] = "Project Limits:";
//        projectInfo[ri][1] = project.getLimits() != null ? project.getLimits() : "";
        ri++;
        projectInfo[ri][0] = "Project Website:";
        projectInfo[ri][1] = project.getUrlLink() != null ? project.getUrlLink() : "";
        ri++;
        projectInfo[ri][0] = "Signature Sign-Off:";
        projectInfo[ri][1] = "";
        return projectInfo;
    }

    public String[][] getProjectLimitsAndDescriptionTable(Project project) {
        String[][] tableData = new String[2][2];
        tableData[0][0] = "Project Limits:";
        tableData[0][1] = project.getLimits() != null ? project.getLimits() : "None specified";
        tableData[1][0] = "Project Description:";
        tableData[1][1] = project.getDescription() != null ? project.getDescription() : "";
        return tableData;
    }
    public String[][] getProjectDescriptionTable(Project project) {
        String[][] tableData = new String[2][2];
        tableData[0][0] = "Project Description:";
        tableData[0][1] = project.getDescription() != null ? project.getDescription() : "";
        return tableData;
    }

    public String[][] getProjectLimitsTable(Project project) {
        String[][] tableData = new String[1][2];
        tableData[0][0] = "Project Limits:";
        tableData[0][1] = project.getLimits() != null ? project.getLimits() : "";
        return tableData;
    }

    public String[][] getFacilityAndBaseConditions(Project project) {
        String[][] tableData = new String[11][3];
        int ri = 0;
        tableData[ri][0] = "Name";
        tableData[ri][1] = "Value";
        tableData[ri][2] = "Comment";
        ri++;
        tableData[ri][0] = "Average Annual Daily Traffic (AADT):";
        tableData[ri][1] = String.format("%,d", project.getAadt());
        tableData[ri][2] = project.getAadtComment() != null ? project.getAadtComment() : "";
        ri++;
        tableData[ri][0] = "Functional Class of Roadway:";
        tableData[ri][1] = project.getFunctionalClass() != null ? project.getFunctionalClass() : "";
        tableData[ri][2] = project.getFcrComment() != null ? project.getFcrComment() : "";
        ri++;
        tableData[ri][0] = "Maintaining Agency:";
        tableData[ri][1] = project.getMaintainingAgency() != null ? project.getMaintainingAgency() : "";
        tableData[ri][2] = project.getMaComment() != null ? project.getMaComment() : "";
        ri++;
        tableData[ri][0] = "Area Type:";
        tableData[ri][1] = project.getAreaType() != null ? project.getAreaType() : "";
        tableData[ri][2] = project.getAtComment() != null ? project.getAtComment() : "";
        ri++;
        tableData[ri][0] = "Number of Roadway Lanes (1 Direction):";
        tableData[ri][1] = String.valueOf(project.getNumRoadwayLanes());
        tableData[ri][2] = project.getNrlComment() != null ? project.getNrlComment() : "";
        ri++;
        tableData[ri][0] = "Shoulder Width (ft):";
        tableData[ri][1] = String.valueOf(project.getShoulderWidth());
        tableData[ri][2] = project.getSwComment() != null ? project.getSwComment() : "";
        ri++;
        tableData[ri][0] = "Posted Speed Limit (mph):";
        tableData[ri][1] = String.valueOf(project.getSpeedLimit());
        tableData[ri][2] = project.getPslComment() != null ? project.getPslComment() : "";
        ri++;
        tableData[ri][0] = "Lane Width:";
        tableData[ri][1] = String.valueOf(project.getLaneWidthBase());
        tableData[ri][2] = project.getLwComment() != null ? project.getLwComment() : "";
        ri++;
        tableData[ri][0] = "Signalized Corridor:";
        tableData[ri][1] = project.getSignalizedCorridor() != null ? project.getSignalizedCorridor() : "";
        tableData[ri][2] = project.getScComment() != null ? project.getScComment() : "";
        ri++;
        tableData[ri][0] = "National Highway System:";
        tableData[ri][1] = project.getNationalHighwaySystem() != null ? project.getNationalHighwaySystem() : "";
        tableData[ri][2] = project.getNhsComment() != null ? project.getNhsComment() : "";

        return tableData;
    }

    /*
    Creates the table for PDF 3, WZITS Selected Applications and Refinement
    in the "WZITS Selected Applications section"
     */
    public String[][] getWZITSselectedApp(Project project) {
        ObservableList<Application> qList = project.getApplicationMatrix().getAppList();
        String[][] tableData = new String[qList.size() + 1][3]; // [row][col]
        int ri = 0;
        tableData[ri][0] = "Application"; // sets col 0 heading
        tableData[ri][1] = "High";       // sets col 1 heading
        tableData[ri][2] = "Selected";    // sets col 2 heading
        ri++; // row 1 beginning application questions
        for (Application app : project.getApplicationMatrix().getAppList()) {
            tableData[ri][0] = app.getName(); //Application
            tableData[ri][1] = Application.getScoreString(app.getScore());
            tableData[ri][2] = app.isSelected() ? "Yes" : "No"; // Selected
            ri++;  // next row
        }
        return tableData;
    }
    /*
   Creates the table for PDF 3, WZITS Selected Applications and Refinement
   in the Application Refinement section
    */
    public String[][] getBenefitRefinement(Project project) {
        QuestionOptionMS beneQ = project.getQGen().qBenefitList.get(0);
        String[][] tableData = new String[beneQ.getOptions().length + 1][2];

        int ri = 0; // row for heading
        tableData[ri][0] = "Application Refinement"; //heading at row 0 col 0
        tableData[ri][1] = "Response"; // heading at row 0 col 1
//        tableData[ri][2] = "Comment"; // heading at row 0 col 2
        ri++; // now on row 1 to begin populating questions
        for (int bIdx = 0; bIdx < beneQ.getOptions().length; bIdx++) {
            tableData[ri][0] = beneQ.getOption(bIdx); // gets the question text of beneQ at position of bIdx
            tableData[ri][1] = beneQ.getOptionIncluded(bIdx) ? "Yes" : "No"; // gets the comment of beneQ at position of bIdx
//            tableData[ri][2] = beneQ.getComment() != null ? beneQ.getComment() : "";
            ri++;
        }
        return tableData;
    }

    public String[][] getBenefitRefinementComment(Project project) {
        String[][] tableData = new String[2][1];
        tableData[0][0] = "Benefits Comment";
        tableData[1][0] = project.getQGen().qBenefitList.get(0).getComment() != null ? project.getQGen().qBenefitList.get(0).getComment() : "--";
        return tableData;
    }
    /*
   Creates the table for PDF 3, WZITS Selected Applications and Refinement
   in the institutuional/jurisdictional section
    */
    public String[][] getInstitutionalJurisdictional(Project project) {
        ObservableList<QuestionYN>qList = project.getQGen().qJurisdictionalList;
        String[][] tableData = new String[qList.size() + 1][3];
        int ri = 0;
        tableData[ri][0] = "Application Refinement";
        tableData[ri][1] = "Response";
        tableData[ri][2] = "Comment";
        ri++;
        for (QuestionYN question : qList) {
            tableData[ri][0] = question.getQuestionText(); //Application
            tableData[ri][1] = question.getAnswerString(); // High
            tableData[ri][2] = question.getComment() != null ? question.getComment() : ""; // Selected
            ri++;
        }
        return tableData;
    }
    /*
   Creates the table for PDF 3, WZITS Selected Applications and Refinement
   in the Legal/policy
    */
    public String[][] getLegalPolicy(Project project) {
        ObservableList<QuestionYN>qList = project.getQGen().qLegalList;
        String[][] tableData = new String[qList.size() + 1][3];
        int ri = 0;
        tableData[ri][0] = "Application Refinement";
        tableData[ri][1] = "Response";
        tableData[ri][2] = "Comment";
        ri++;
        for (QuestionYN question : qList) {
            tableData[ri][0] = question.getQuestionText(); //Application
            tableData[ri][1] = question.getAnswerString(); // High
            tableData[ri][2] = question.getComment() != null ? question.getComment() : ""; // Selected
            ri++;
        }
        return tableData;
    }
    /*
   Creates the table for PDF 3, WZITS Selected Applications and Refinement
   in the Stakeholder buy-in section
    */
    public String[][] getStakeholderBuyin(Project project) {
        ObservableList<QuestionYN>qList = project.getQGen().qStakeholderBuyInList;
        String[][] tableData = new String[qList.size() + 1][3];
        int ri = 0;
        tableData[ri][0] = "Application Refinement";
        tableData[ri][1] = "Response";
        tableData[ri][2] = "Comment";
        ri++;
        for (QuestionYN question : qList) {
            tableData[ri][0] = question.getQuestionText(); //Application
            tableData[ri][1] = question.getAnswerString(); // High
            tableData[ri][2] = question.getComment() != null ? question.getComment() : ""; // Selected
            ri++;
        }
        return tableData;
    }
    //todo PDF 4
    /*
    Creates the table for PDF 5, Document Concept of Operations
    in the System Planning and Design section
     */
    public String[][] getDocumentConsOperations(Project project) {
        ObservableList<QuestionYN>qList = project.getQGen().qConOpsList;
        String[][] tableData = new String[qList.size() + 1][3];
        int ri = 0;
        tableData[ri][0] = "Question";
        tableData[ri][1] = "Response";
        tableData[ri][2] = "Comment";
        ri++;
        for (QuestionYN question : qList) {
            tableData[ri][0] = question.getQuestionText(); //question
            tableData[ri][1] = question.getAnswerString(); // response
            tableData[ri][2] = question.getComment() != null ? question.getComment() : ""; // comment
            ri++;
        }
        return tableData;
    }
    /*
    Creates the table for PDF 5, Requirements
    in the System Planning and Design section
     */
    public String[][] getSysPlanDesgnRequirements(Project project) {
        ObservableList<QuestionYN>qList = project.getQGen().qSysReqList;
        String[][] tableData = new String[qList.size() + 1][3];
        int ri = 0;
        tableData[ri][0] = "Question";
        tableData[ri][1] = "Response";
        tableData[ri][2] = "Comment";
        ri++;
        for (QuestionYN question : qList) {
            tableData[ri][0] = question.getQuestionText(); //question
            tableData[ri][1] = question.getAnswerString(); // response
            tableData[ri][2] = question.getComment() != null ? question.getComment() : ""; // comment
            ri++;
        }
        return tableData;
    }

    ;

    /*
    Creates the table for PDF 5, Testing strategy
    in the System Planning and Design section
     */
    public String[][] getTestStrategy(Project project) {
        ObservableList<QuestionYN>qList = project.getQGen().qTestingStratList;
        String[][] tableData = new String[qList.size() + 1][3];
        int ri = 0;
        tableData[ri][0] = "Question";
        tableData[ri][1] = "Response";
        tableData[ri][2] = "Comment";
        ri++;
        for (QuestionYN question : qList) {
            tableData[ri][0] = question.getQuestionText(); //question
            tableData[ri][1] = question.getAnswerString(); // response
            tableData[ri][2] = question.getComment() != null ? question.getComment() : ""; // comment
        }
        return tableData;
    }

    ;

    /*
    Creates the table for PDF 5, Operations & Maintenance
    in the System Planning and Design section
     */
    public String[][] getOpsandMaitenance(Project project) {
        ObservableList<QuestionYN>qList = project.getQGen().qOpsMaintList;
        String[][] tableData = new String[qList.size() + 1][3];
        int ri = 0;
        tableData[ri][0] = "Question";
        tableData[ri][1] = "Response";
        tableData[ri][2] = "Comment";
        ri++;
        for (QuestionYN question : qList) {
            tableData[ri][0] = question.getQuestionText(); //question
            tableData[ri][1] = question.getAnswerString(); // response
            tableData[ri][2] = question.getComment() != null ? question.getComment() : "";
            ri++;
        }
        return tableData;
    }

    ;

    /*
    Creates the table for PDF 5, Staff Training Needs
    in the System Planning and Design section
     */
    public String[][] getStaffTrainingNeeds(Project project) {
        ObservableList<QuestionYN>qList = project.getQGen().qStaffTrainingList;
        String[][] tableData = new String[qList.size() + 1][3];
        int ri = 0;
        tableData[ri][0] = "Question";
        tableData[ri][1] = "Response";
        tableData[ri][2] = "Comment";
        ri++;
        for (QuestionYN question : qList) {
            tableData[ri][0] = question.getQuestionText(); //question
            tableData[ri][1] = question.getAnswerString(); // response
            tableData[ri][2] = question.getComment() != null ? question.getComment() : "";
            ri++;
        }
        return tableData;
    }

    ;

    /*
    Creates the table for PDF 5, System Security
    in the System Planning and Design section
     */
    public String[][] getSysSecurity(Project project) {
        ObservableList<QuestionYN>qList = project.getQGen().qSysSecurityList;
        String[][] tableData = new String[qList.size() + 1][3];
        int ri = 0;
        tableData[ri][0] = "Question";
        tableData[ri][1] = "Response";
        tableData[ri][2] = "Comment";
        ri++;
        for (QuestionYN question : qList) {
            tableData[ri][0] = question.getQuestionText(); //question
            tableData[ri][1] = question.getAnswerString(); // response
            tableData[ri][2] = question.getComment() != null ? question.getComment() : "";
            ri++;
        }
        return tableData;
    }
    /*
    Creates the table for PDF 5, Evaluation
    in the System Planning and Design section
     */
    public String[][] getEvaluation(Project project) {
        ObservableList<QuestionYN> qList = project.getQGen().qProjectEvalList;
        String[][] tableData = new String[qList.size() + 1][3];
        int ri = 0;
        tableData[ri][0] = "Question";
        tableData[ri][1] = "Response";
        tableData[ri][2] = "Comment";
        ri++;
        for (QuestionYN question : qList) {
            tableData[ri][0] = question.getQuestionText(); //question
            tableData[ri][1] = question.getAnswerString(); // response
            tableData[ri][2] = question.getComment() != null ? question.getComment() : "";
            ri++;
        }
        return tableData;
    }
    /*
    Creates the table for PDF 5, Benefit/Cost
    in the System Planning and Design section
     */
    public String[][] getBenefitCost(Project project) {
        ObservableList<QuestionYN> qList = project.getQGen().qSysBCList;
        String[][] tableData = new String[qList.size() + 1][3];
        int ri = 0;
        tableData[ri][0] = "Question";
        tableData[ri][1] = "Response";
        tableData[ri][2] = "Comment";
        ri++;
        for (QuestionYN question : qList) {
            tableData[ri][0] = question.getQuestionText(); //question
            tableData[ri][1] = question.getAnswerString(); // response
            tableData[ri][2] = question.getComment() != null ? question.getComment() : "";
            ri++;
        }
        return tableData;
    }
    /*
    Creates the table for PDF 6, direct/indirect
    in the System Planning and Design section
     */
    public String[][] getDirectOrIndirect(Project project) {
        ObservableList<QuestionYN> qList = project.getQGen().qDirectIndirectList;
        String[][] tableData = new String[qList.size() + 1][3];
        int ri = 0;  // row 0 for headings
        tableData[ri][0] = "Question"; // heading
        tableData[ri][1] = "Response"; // heading
        tableData[ri][2] = "Comment"; // heading
        ri++; // new for to begin questions
        for (QuestionYN question : qList) {
            tableData[ri][0] = question.getQuestionText();
            tableData[ri][1] = question.getAnswerString(); // response
            tableData[ri][2] = question.getComment() != null ? question.getComment() : ""; // comment
            ri++;
        }
        return tableData;
    }
    /*
    Creates the table for PDF 6, Award Mechanism
    in the System Planning and Design section
     */
    public String[][] getAwardMechanism(Project project) {
        ObservableList<QuestionYN> qList = project.getQGen().qMechanismList;

        String[][] tableData = new String[qList.size() + 1][3]; //row, col, sets the size of the array to the size of the question list
        System.out.println("The size of stepList is " + project.getQGen().qDirectIndirectList);
        int ri = 0; // "zero" row to hold the column headings
        tableData[ri][0] = "Question"; // assigns header rows
        tableData[ri][1] = "Response"; // assigns header rows
        tableData[ri][2] = "Comment"; // assigns header rows
        ri++; // change row index to 1 to begin adding question,answer, comments to the table

        for (QuestionYN question : qList) {
            tableData[ri][0] = question.getQuestionText(); // gets question text
            tableData[ri][1] = question.getAnswerString(); // gets question answer
            tableData[ri][2] = question.getComment() != null ? question.getComment() : "";
            ri++;
        }
        return tableData;
    }
    /*
   Creates the table for PDF 6, Award Mechanism
   in the System Planning and Design section
    */
    public String[][] getRFPrequirements(Project project) {
        ObservableList<QuestionYN> qList = project.getQGen().qRFPList; // accesses rfp questions
        String[][] tableData = new String[qList.size() + 1][3]; // sets the two-dim array to the size of the number of questions and the cols to 3.. + 1 to account for the headings
        int ri = 0; // row 0 for headings
        tableData[ri][0] = "Question"; // sets col 0 heading
        tableData[ri][1] = "Response"; // sets col 1 heading
        tableData[ri][2] = "Comment"; // sets col 2 heading
        ri++; // updates row to begin populating questions
        for (QuestionYN question : qList) {
            tableData[ri][0] = question.getQuestionText(); // gets question text
            tableData[ri][1] = question.getAnswerString(); // gets question answer
            tableData[ri][2] = question.getComment() != null ? question.getComment() : ""; // gets the user commment
            ri++; // updates the row, accessing the next question, answer, comment in qList
        }
        return tableData;
    }
    /*
   Creates the table for PDF 6, Selected Vendor
   in the System Planning and Design section
    */
    public String[][] getSelectedVendor(Project project) {
        ObservableList<QuestionYN> qList = project.getQGen().qVendorSelectionList; // accesses vendor related questions

        String[][] tableData = new String[qList.size() + 1][3]; // rows, columns
        int ri = 0; // row 0 for headings
        tableData[ri][0] = "Question";
        tableData[ri][1] = "Response";
        tableData[ri][2] = "Comment";
        ri++;
        for (QuestionYN question : qList) {
            tableData[ri][0] = question.getQuestionText();
            tableData[ri][1] = question.getAnswerString();
            tableData[ri][2] = question.getComment() != null ? question.getComment() : "";
            ri++;
        }
        return tableData;
    }
    /*
    Creates the table for PDF 7, implementing system plans
    in the System Planning and Design section
    */
    public String[][] getImplementSysPlans(Project project) {
        ObservableList<QuestionYN> qList = project.getQGen().qSysPlansList;
        String[][] tableData = new String[qList.size() + 1][3];
        int ri = 0;
        tableData[ri][0] = "Question";
        tableData[ri][1] = "Response";
        tableData[ri][2] = "Comment";
        ri++;
        for (QuestionYN questionYN : qList) {
            tableData[ri][0] = questionYN.getQuestionText(); //question
            tableData[ri][1] = questionYN.getAnswerString(); // response
            tableData[ri][2] = questionYN.getComment() != null ? questionYN.getComment() : ""; // comment
            ri++;
        }
        return tableData;
    }
    /*
    Creates the table for PDF 7, scheduling decisions
    in the scheduling decisions section
    */
    public String[][] getScheduleDecisions(Project project) {
        ObservableList<QuestionYN>qList = project.getQGen().qAcceptanceTrainingList;
        String[][] tableData = new String[qList.size() + 1][3]; // 5 rows, 3 columns
        int ri = 0;
        tableData[ri][0] = "Question";
        tableData[ri][1] = "Response";
        tableData[ri][2] = "Comment";
        ri++;
        for (QuestionYN question : qList) {
            tableData[ri][0] = question.getQuestionText(); //question
            tableData[ri][1] = question.getAnswerString(); // response
            tableData[ri][2] = question.getComment() != null ? question.getComment() : ""; // comment
            ri++;
        }
        return tableData;
    }

    /*
    Creates the table for PDF 7, system acceptance testing
    in the System Planning and Design section
    */
    public String[][] getSystemAcceptance(Project project) {
        ObservableList<QuestionYN> qList = project.getQGen().qAcceptanceTrainingList;
        String[][] tableData = new String[qList.size() + 1][3];
        int ri = 0;
        tableData[ri][0] = "Question";
        tableData[ri][1] = "Response";
        tableData[ri][2] = "Comment";
        ri++;
        for (QuestionYN questionYN : qList) {
            tableData[ri][0] = questionYN.getQuestionText(); //question
            tableData[ri][1] = questionYN.getAnswerString(); // response
            tableData[ri][2] = questionYN.getComment() != null ? questionYN.getComment() : ""; // comment
            ri++;
        }
        return tableData;
    }
    /*
    Creates the table for PDF 7, Handling Deployment Issues
    in the System Planning and Design section
    */
    public String[][] getHandleDeployIssues(Project project) {
        ObservableList<QuestionYN>qList = project.getQGen().qDeploymentIssuesList;
        String[][] tableData = new String[qList.size() + 1][3];
        int ri = 0;
        tableData[ri][0] = "Question";
        tableData[ri][1] = "Response";
        tableData[ri][2] = "Comment";
        ri++;
        for (QuestionYN question : qList) {
            tableData[ri][0] = question.getQuestionText(); //question
            tableData[ri][1] = question.getAnswerString(); // response
            tableData[ri][2] = question.getComment() != null ? question.getComment() : ""; // comment
            ri++;
        }
        return tableData;
    }
    /*
   Creates the table for PDF 8, Changing Work Zone
   in the System Operation, Maintenance & Evaluation section
   */
    public String[][] getChangingWorkZone(Project project) {
        ObservableList<QuestionYN>qList = project.getQGen().qChangingConditionsList;
        String[][] tableData = new String[qList.size() + 1][3];
        int ri = 0;
        tableData[ri][0] = "Question";
        tableData[ri][1] = "Response";
        tableData[ri][2] = "Comment";
        ri++;
        for (QuestionYN question : qList) {
            tableData[ri][0] = question.getQuestionText(); //question
            tableData[ri][1] = question.getAnswerString(); // response
            tableData[ri][2] = question.getComment() != null ? question.getComment() : ""; // comment
            ri++;
        }
        return tableData;
    }
    /*
  Creates the table for PDF 8, Using/Sharing ITS Info
  in the System Operation, Maintenance & Evaluation section
  */
    public String[][] getUsingSharingITSinfo(Project project) {
        ObservableList<QuestionYN>qList = project.getQGen().qSharingInfoList;
        String[][] tableData = new String[qList.size() + 1][3];
        int ri = 0;
        tableData[ri][0] = "Question";
        tableData[ri][1] = "Response";
        tableData[ri][2] = "Comment";
        ri++;
        for (QuestionYN question : qList) {
            tableData[ri][0] = question.getQuestionText(); //question
            tableData[ri][1] = question.getAnswerString(); // response
            tableData[ri][2] = question.getComment() != null ? question.getComment() : ""; // comment
            ri++;
        }
        return tableData;
    };
    /*
    Creates the table for PDF 8, maintaining adequate staff
    in the System  Operation, Maintenance & Evaluation section
    */
    public String[][] getMaintainAdequateStaff(Project project) {
        ObservableList<QuestionYN> qList = project.getQGen().qStaffingList;
        String[][] tableData = new String[qList.size() + 1][3];
        int ri = 0;
        tableData[ri][0] = "Question";
        tableData[ri][1] = "Response";
        tableData[ri][2] = "Comment";
        ri++;
        for (QuestionYN question : qList) {
            tableData[ri][0] = question.getQuestionText(); //question
            tableData[ri][1] = question.getAnswerString(); // response
            tableData[ri][2] = question.getComment() != null ? question.getComment() : ""; // comment
        }
        return tableData;
    }
    /*
    Creates the table for PDF 8, Leveraging Public support
    in the System Operation, Maintenance & Evaluation section
    */
    public String[][] getLeverageSupport(Project project) {
        ObservableList<QuestionYN>qList = project.getQGen().qPublicSupportList;
        String[][] tableData = new String[qList.size() + 1][3];
        int ri = 0;
        tableData[ri][0] = "Question";
        tableData[ri][1] = "Response";
        tableData[ri][2] = "Comment";
        ri++;
        for (QuestionYN question : qList) {
            tableData[ri][0] = question.getQuestionText(); //question
            tableData[ri][1] = question.getAnswerString(); // response
            tableData[ri][2] = question.getComment() != null ? question.getComment() : ""; // comment
            ri++;
        }
        return tableData;
    }
    /*
    Creates the table for PDF 8, System Monitoring Evaluation
    in the System Operation, Maintenance & Evaluation section
    */
    public String[][] getSysMonitoringEvaluation(Project project) {
        ObservableList<QuestionYN> qList = project.getQGen().qMonitoringEvalList; // accesses vendor related questions

        String[][] tableData = new String[qList.size() + 1][3];
        int ri = 0;
        tableData[ri][0] = "Question";
        tableData[ri][1] = "Response";
        tableData[ri][2] = "Comment";
        ri++;
        for (QuestionYN question : qList) {
            tableData[ri][0] = question.getQuestionText(); //question
            tableData[ri][1] = question.getAnswerString(); // response
            tableData[ri][2] = question.getComment() != null ? question.getComment() : ""; // comment
            ri++;
        }
        return tableData;
    }
    public String[][] getWorkZoneConfiguration(Project project) {
        String[][] tableData = new String[8][3];
        int ri = 0;
        tableData[ri][0] = "Name";
        tableData[ri][1] = "Value";
        tableData[ri][2] = "Comment";
        ri++;
        tableData[ri][0] = "Length of Work Zone (mi):";
        tableData[ri][1] = String.format("%.1f", (project.getWzLength()));
        tableData[ri][2] = project.getWzlComment() != null ? project.getWzlComment() : "";
        ri++;
        tableData[ri][0] = "Type of Work Zone (MUTCD):";
        tableData[ri][1] = project.getWzType() != null ? project.getWzType() : "";
        tableData[ri][2] = project.getWztComment() != null ? project.getWztComment() : "";
        ri++;
        tableData[ri][0] = "Work Zone Speed Limit:";
        tableData[ri][1] = String.valueOf(project.getWzSpeedLimit());
        tableData[ri][2] = project.getWzslComment() != null ? project.getWzslComment() : "";
        ri++;
        tableData[ri][0] = "Number of Lanes to be Closed:";
        tableData[ri][1] = String.valueOf(project.getNumLanesClosed());
        tableData[ri][2] = project.getNlcComment() != null ? project.getNlcComment() : "";
        ri++;
        tableData[ri][0] = "Work Zone Lane Width:";
        tableData[ri][1] = String.format("%.1f", project.getLaneWidthWZ());
        tableData[ri][2] = project.getWzlwComment() != null ? project.getWzlwComment() : "";
        ri++;
        tableData[ri][0] = "Shoulder Closure:";
        tableData[ri][1] = project.getShoulderClosure() != null ? project.getShoulderClosure() : "";
        tableData[ri][2] = project.getShcComment() != null ? project.getShcComment() : "";
        ri++;
        tableData[ri][0] = "Federal-Aid Project:";
        tableData[ri][1] = project.getFederalAid() != null ? project.getFederalAid() : "";
        tableData[ri][2] = project.getFapComment() != null ? project.getFapComment() : "";
        return tableData;
    }

    public String[][] getITSGoalsTable(Project project) {
        String[][] tableData;
        int overallNumSelected = 0;

        GoalNeedsMatrix gnm = project.getGoalNeedsMatrix();
        gnm.computeScores();
        // Mobility Goals
        ObservableList<Need> ml = gnm.getGoalListByType(Question.GOAL_MOBILITY);
        // Safety Goals
        ObservableList<Need> sl = gnm.getGoalListByType(Question.GOAL_SAFETY);
        // Productivity Goals
        ObservableList<Need> pl = gnm.getGoalListByType(Question.GOAL_PROD);
        // Regulatory Goals
        ObservableList<Need> rl = gnm.getGoalListByType(Question.GOAL_REG);
        // Traveler Information Goals
        ObservableList<Need> tl = gnm.getGoalListByType(Question.GOAL_TRAVELER_INFO);
        overallNumSelected += Step1TableHelper.countSelected(sl);
        overallNumSelected += Step1TableHelper.countSelected(ml);
        overallNumSelected += Step1TableHelper.countSelected(pl);
        overallNumSelected += Step1TableHelper.countSelected(rl);
        overallNumSelected += Step1TableHelper.countSelected(tl);

        if (overallNumSelected == 0) {
            tableData = new String[2][3];
            tableData[0][0] = "Category";
            tableData[0][1] = "Selected Work Zone ITS Goals";
            tableData[0][2] = "Ranking";
            tableData[1][0] = "N/A";
            tableData[1][1] = "No goals selected for the project.";
            tableData[1][2] = "--";
        } else {
            tableData = new String[1 + overallNumSelected][3];
            tableData[0][0] = "Category";
            tableData[0][1] = "Selected Work Zone ITS Goals";
            tableData[0][2] = "Ranking";

            int ri = 1;
            if (Step1TableHelper.countSelected(sl) > 0) {
                for (Need n : sl) {
                    if (!n.isPlaceholder && n.isSelected()) {
                        tableData[ri][0] = Question.GOAL_SAFETY;
                        tableData[ri][1] = n.getDescription();
                        tableData[ri][2] = Need.getScoreString(n.getScore());
                        ri++;
                    }
                }
            }

            if (Step1TableHelper.countSelected(ml) > 0) {
                for (Need n : ml) {
                    if (!n.isPlaceholder && n.isSelected()) {
                        tableData[ri][0] = Question.GOAL_MOBILITY;
                        tableData[ri][1] = n.getDescription();
                        tableData[ri][2] = Need.getScoreString(n.getScore());
                        ri++;
                    }
                }
            }

            if (Step1TableHelper.countSelected(pl) > 0) {
                for (Need n : pl) {
                    if (!n.isPlaceholder && n.isSelected()) {
                        tableData[ri][0] = Question.GOAL_PROD;
                        tableData[ri][1] = n.getDescription();
                        tableData[ri][2] = Need.getScoreString(n.getScore());
                        ri++;
                    }
                }
            }

            if (Step1TableHelper.countSelected(rl) > 0) {
                for (Need n : rl) {
                    if (!n.isPlaceholder && n.isSelected()) {
                        tableData[ri][0] = Question.GOAL_REG;
                        tableData[ri][1] = n.getDescription();
                        tableData[ri][2] = Need.getScoreString(n.getScore());
                        ri++;
                    }
                }
            }

            if (Step1TableHelper.countSelected(tl) > 0) {
                for (Need n : tl) {
                    if (!n.isPlaceholder && n.isSelected()) {
                        tableData[ri][0] = Question.GOAL_TRAVELER_INFO;
                        tableData[ri][1] = n.getDescription();
                        tableData[ri][2] = Need.getScoreString(n.getScore());
                        ri++;
                    }
                }
            }
        }
        return tableData;
    }

    public String[][] getFeasibilityTable(Project project) {

        FeasibilityMatrix feasibilityMatrix = project.getFeasibilityMatrix();
        feasibilityMatrix.computeFeasibility();
        String descriptionText;
        if (feasibilityMatrix.getFeasibility() >= 30) {
            descriptionText = FeasibilityMatrix.DESC_30PLUS;
        } else if (feasibilityMatrix.getFeasibility() >= 10) {
            descriptionText = FeasibilityMatrix.DESC_10_TO_29;
        } else {
            descriptionText = FeasibilityMatrix.DESC_LESS_THAN_10;
        }

        String[][] tableData = new String[2][2];
        tableData[0][0] = "Score: " + feasibilityMatrix.getFeasibility();
        tableData[0][1] = descriptionText;
        tableData[1][0] = "Justification";
        tableData[1][1] = ""; // TODO Add justification to project (and save, etc.)

        return tableData;
    }

    public String[][] createCoreTeamTable(Project project) {
        // Core Team Members
        StakeholderMatrix sMat = project.getStakeholderMatrix();
        //sMat.computeStakeholders();
        ArrayList<Stakeholder> coreTeam = new ArrayList();
        for (Stakeholder sh : sMat.stakeholders) {
            if (sh.isCoreTeamMember()) {
                coreTeam.add(sh);
            }
        }
        String[][] tableData = new String[Math.max(coreTeam.size(), 1) + 1][4];
        tableData[0][0] = "#";
        tableData[0][1] = "Core Team Member";
        tableData[0][2] = "Email";
        tableData[0][3] = "Phone";
        // Core Team Members
        if (coreTeam.size() == 0) {
            tableData[1][0] = "-";
            tableData[1][1] = "No Core Team Members Selected";
            tableData[1][2] = "--";
            tableData[1][3] = "--";
        } else {
            int rowIdx = 1;
            for (Stakeholder sh : coreTeam) {
                tableData[rowIdx][0] = String.valueOf(rowIdx);
                tableData[rowIdx][1] = sh.getName() != null ? sh.getName() : "";
                tableData[rowIdx][2] = sh.getEmail() != null ? sh.getEmail() : "";
                tableData[rowIdx][3] = sh.getPhone() != null ? sh.getPhone() : "";
                rowIdx++;
            }
        }
        return tableData;
    }

    public String[][] createOtherStakeholdersTable(Project project) {

        // Stakeholders
        StakeholderMatrix sMat = project.getStakeholderMatrix();
        //sMat.computeStakeholders();
        ArrayList<Stakeholder> stakeholders = new ArrayList();
        for (Stakeholder sh : sMat.stakeholders) {
            if (sh.isStakeholder()) {
                stakeholders.add(sh);
            }
        }
        String[][] tableData = new String[Math.max(stakeholders.size(), 1) + 1][4];
        tableData[0][0] = "#";
        tableData[0][1] = "Stakeholder";
        tableData[0][2] = "Email";
        tableData[0][3] = "Phone";
        // Other Stakeholders
        if (stakeholders.size() == 0) {
            tableData[1][0] = "-";
            tableData[1][1] = "No Additional Stakeholders Selected";
            tableData[1][2] = "--";
            tableData[1][3] = "--";
        } else {
            int rowIdx = 1;
            for (Stakeholder sh : stakeholders) {
                tableData[rowIdx][0] = String.valueOf(rowIdx);
                tableData[rowIdx][1] = sh.getName() != null ? sh.getName() : "";
                tableData[rowIdx][2] = sh.getEmail() != null ? sh.getEmail() : "";
                tableData[rowIdx][3] = sh.getPhone() != null ? sh.getPhone() : "";
                rowIdx++;
            }
        }

        return tableData;
    }

    public String[][] createITSResourcesTable(Project project) {
        String[][] tableData = new String[6][4];
        int ri = 0;
        tableData[ri][0] = "#";
        tableData[ri][1] = "Type";
        tableData[ri][2] = "Response";
        tableData[ri][3] = "Comment";
        ri++;
        QuestionYN currQ = project.getQGen().qITSResourcesList.get(ri - 1);
        tableData[ri][0] = String.valueOf(ri);
        tableData[ri][1] = "Weather Monitoring Stations";
        tableData[ri][2] = currQ.getAnswerString();
        tableData[ri][3] = currQ.getComment() != null ? currQ.getComment() : "";
        ri++;
        currQ = project.getQGen().qITSResourcesList.get(ri - 1);
        tableData[ri][0] = String.valueOf(ri);
        tableData[ri][1] = "TMC Monitoring Roadway";
        tableData[ri][2] = currQ.getAnswerString();
        tableData[ri][3] = currQ.getComment() != null ? currQ.getComment() : "";
        ri++;
        currQ = project.getQGen().qITSResourcesList.get(ri - 1);
        tableData[ri][0] = String.valueOf(ri);
        tableData[ri][1] = "Website/Traveler Information System";
        tableData[ri][2] = currQ.getAnswerString();
        tableData[ri][3] = currQ.getComment() != null ? currQ.getComment() : "";
        ri++;
        currQ = project.getQGen().qITSResourcesList.get(ri - 1);
        tableData[ri][0] = String.valueOf(ri);
        tableData[ri][1] = "ITS On-Call Contract(s) Available";
        tableData[ri][2] = currQ.getAnswerString();
        tableData[ri][3] = currQ.getComment() != null ? currQ.getComment() : "";
        ri++;
        currQ = project.getQGen().qITSResourcesList.get(ri - 1);
        tableData[ri][0] = String.valueOf(ri);
        tableData[ri][1] = "Access to Leased Devices";
        tableData[ri][2] = currQ.getAnswerString();
        tableData[ri][3] = currQ.getComment() != null ? currQ.getComment() : "";

        return tableData;

    }

    public String[][] getContourTable() {
//        boolean isGreyScale = false;
//        final int numPer = seed.getValueInt(CEConst.IDS_NUM_PERIOD);
//        final int numSeg = seed.getValueInt(CEConst.IDS_NUM_SEGMENT);
//        String[][] contourTable = null;
//
//        String[] header = new String[numSeg + 1];
//        header[0] = "Analysis Period   (24 hr Format)";
//        for (int seg = 1; seg < header.length; seg++) {
//            header[seg] = String.valueOf(seg);
//        }
//        String[] rowNames = new String[numPer];
//        CETime inc = new CETime(0, 15);
//        for (int per = 0; per < rowNames.length; per++) {
//            rowNames[per] = CETime.addTime(seed.getStartTime(), inc, per).toString()
//                    + "-" + CETime.addTime(seed.getStartTime(), inc, per + 1).toString();
//        }
//        contourTable = new String[numPer + 1][];
//        contourTable[0] = new String[header.length];
//
//        System.arraycopy(header, 0, contourTable[0], 0, header.length);
//
//        for (int i = 0; i < numPer; i++) {
//            contourTable[i + 1] = new String[header.length];
//            contourTable[i + 1][0] = rowNames[i];
//
//            for (int j = 0; j < numSeg; j++) {
////                Color c = contour.getCellRenderer(i, j).getTableCellRendererComponent(contour, contour.getValueAt(i, j), false, false, i, j).getBackground();
////                String bgColor = String.format("#%02x%02x%02x", c.getRed(), c.getGreen(), c.getBlue());
//                Color c = ContourTextFieldTableCell.getContourColor(modelType, seed.getValueFloat(modelType, j, i, scen, atdm));
//                String bgColor = toHexString(c);
//                contourTable[i + 1][j + 1] = bgColor;
//            }
//        }
//
//        return contourTable;
        return null;
    }

    // https://stackoverflow.com/a/56733608
    private String hexFormat(double val) {
        String in = Integer.toHexString((int) Math.round(val * 255));
        return in.length() == 1 ? "0" + in : in;
    }

    // https://stackoverflow.com/a/56733608
    private String toHexString(Color value) {
        return "#" + (hexFormat(value.getRed()) + hexFormat(value.getGreen()) + hexFormat(value.getBlue()) + hexFormat(value.getOpacity()))
                .toUpperCase();
    }


    public void createReport(MainController controller, int factSheetIdx) {
        switch (factSheetIdx) {
            default:
            case -1:
                createAllFactSheets(controller);
                break;
            case 1:
                createFactSheet1(controller);
                break;
            case 2:
                createFactSheet2(controller);
                break;
            case 3:
                createFactSheet3(controller);
                break;
            case 4:
                createFactSheet4(controller);
                break;
            case 5:
                createFactSheet5(controller);
                break;
            case 6:
                createFactSheet6(controller);
                break;
            case 7:
                createFactSheet7(controller);
                break;
            case 8:
                createFactSheet8(controller);
                break;
        }
    }

    private void createFactSheet1(MainController controller) {
        String xmlFilePath = PDFReportHelper.getResFolderLocation() + "fact-sheet-pdf-resources.xml";

        try {
            String projectFileName = "[Unsaved Project File]";
            File projectFile = controller.getProject().getSaveFile();
            if (projectFile != null) {
                projectFileName = projectFile.getAbsolutePath();
            }

            XMLGenerator xmlGenerator = new XMLGenerator(projectFileName,
                    xmlFilePath,
                    "Work Zone ITS Tool",
                    "Fact Sheet #1"
            );
            generateFactSheet1Content(controller, xmlGenerator, false);
//            final String reportTitle = getFactSheetName(1);
//            xmlGenerator.generateTitle(reportTitle, Pos.CENTER);
//            xmlGenerator.generateTable(
//                    getProjectInformationTable(controller.getProject()),
//                    "projectInformation",
//                    false,
//                    false,
//                    false,
//                    "Project Information",
//                    "",
//                    100,
//                    null,
//                    null
//            );
//            xmlGenerator.generateTable(
//                    getProjectLimitsAndDescriptionTable(controller.getProject()),
//                    "projectDescription",
//                    false,
//                    false,
//                    false,
//                    "Project Limits and Additional Information",
//                    "",
//                    100,
//                    new float[]{15, 85},
//                    null
//            );
//            double imgWidth = 5.21;
//            double imgHeight = 3.91;
//            Image projectImage = controller.getProject().getProjPhoto();
//            String imgResourcePath = PDFReportHelper.getResFolderLocation();
//            xmlGenerator.generateImage(imgResourcePath + PDFReportHelper.FILE_FS1_WZIMAGE_TEMP, //src/Toolbox/XML/output/hourly-system-delay.png
//                    imgWidth,  // projectImage.getWidth(),
//                    imgHeight,  // projectImage.getHeight(),
//                    "Work Zone Image", "");
//            xmlGenerator.generateTable(
//                    getFacilityAndBaseConditions(controller.getProject()),
//                    "facilityAndBaseConditions",
//                    false,
//                    true,
//                    false,
//                    "Facility and Base Conditions",
//                    "",
//                    100,
//                    new float[]{23, 12, 65},
//                    new String[]{"left", "center", "left"}
//            );
//            xmlGenerator.generateTable(
//                    getWorkZoneConfiguration(controller.getProject()),
//                    "workZoneConfiguration",
//                    false,
//                    true,
//                    false,
//                    "Facility and Base Conditions",
//                    "",
//                    100,
//                    new float[]{25, 10, 65},
//                    new String[]{"left", "center", "left"}
//            );
//            // getITSGoalsTable
//            xmlGenerator.generateTable(
//                    getITSGoalsTable(controller.getProject()),
//                    "itsGoalsTable",
//                    false,
//                    true,
//                    false,
//                    "ITS Goals Summary",
//                    "",
//                    100,
//                    new float[]{20, 70, 10},
//                    new String[]{"center", "left", "center"}
//            );
//            xmlGenerator.generateTable(
//                    getFeasibilityTable(controller.getProject()),
//                    "feasibilityTable",
//                    false,
//                    true,
//                    false,
//                    "Feasibility Assessment",
//                    "",
//                    100,
//                    new float[]{50, 50},
//                    new String[]{"center", "left"}
//            );

            xmlGenerator.generate();

        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
    }

    public void createFactSheet2(MainController controller) {
        String xmlFilePath = PDFReportHelper.getResFolderLocation() + "fact-sheet-pdf-resources.xml";
        try {
            String projectFileName = "[Unsaved Project File]";
            File projectFile = controller.getProject().getSaveFile();
            if (projectFile != null) {
                projectFileName = projectFile.getAbsolutePath();
            }

            XMLGenerator xmlGenerator = new XMLGenerator(projectFileName,
                    xmlFilePath,
                    "Work Zone ITS Tool",
                    "Fact Sheet #2"
            );
            generateFactSheet2Content(controller, xmlGenerator, false);
//            final String reportTitle = getFactSheetName(2);
//            xmlGenerator.generateTitle(reportTitle, Pos.CENTER);
//            xmlGenerator.generateTable(
//                    getProjectInformationTable(controller.getProject()),
//                    "projectInformation",
//                    false,
//                    false,
//                    false,
//                    "Project Information:",
//                    "",
//                    100,
//                    null,
//                    null
//            );
//            xmlGenerator.generateTable(
//                    getProjectLimitsAndDescriptionTable(controller.getProject()),
//                    "projectDescription",
//                    false,
//                    false,
//                    false,
//                    "Project Limits and Additional Information",
//                    "",
//                    100,
//                    new float[]{15, 85},
//                    null
//            );
//            xmlGenerator.generateTable(
//                    createCoreTeamTable(controller.getProject()),
//                    "coreTeamTable",
//                    false,
//                    true,
//                    false,
//                    "Selected Core Team and Stakeholders",
//                    "",
//                    100,
//                    new float[]{5, 45, 35, 15},
//                    new String[]{"center", "left", "center", "center"}
//            );
//            xmlGenerator.generateTable(
//                    createOtherStakeholdersTable(controller.getProject()),
//                    "otherStakeholdersTable",
//                    false,
//                    true,
//                    false,
//                    "",
//                    "",
//                    100,
//                    new float[]{5, 45, 35, 15},
//                    new String[]{"center", "left", "center", "center"}
//            );
//
//            xmlGenerator.generateTable(
//                    createITSResourcesTable(controller.getProject()),
//                    "itsResourcesTable",
//                    false,
//                    true,
//                    false,
//                    "ITS Resources",
//                    "",
//                    100,
//                    new float[]{5, 30, 10, 55},
//                    new String[]{"center", "left", "center", "left"}
//            );

            xmlGenerator.generate();

        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
    }

    public void createFactSheet3(MainController controller) {
        String xmlFilePath = PDFReportHelper.getResFolderLocation() + "fact-sheet-pdf-resources.xml";
        try {
            String projectFileName = "[Unsaved Project File]";
            File projectFile = controller.getProject().getSaveFile();
            if (projectFile != null) {
                projectFileName = projectFile.getAbsolutePath();
            }
            XMLGenerator xmlGenerator = new XMLGenerator(projectFileName,
                    xmlFilePath,
                    "Work Zone ITS Tool",
                    "Fact Sheet #3" // TODO always update this index
            );
            generateFactSheet3Content(controller, xmlGenerator, false);
//            final String reportTitle = getFactSheetName(3);
//            xmlGenerator.generateTitle(reportTitle, Pos.CENTER);
//            xmlGenerator.generateTable(
//                    getProjectInformationTable(controller.getProject()),
//                    "projectInformation",
//                    false,
//                    false,
//                    false,
//                    "Project Information",
//                    "",
//                    100,
//                    null,
//                    null
//            );
//            xmlGenerator.generateTable(
//                    getProjectLimitsAndDescriptionTable(controller.getProject()),
//                    "projectDescription",
//                    false,
//                    false,
//                    false,
//                    "Project Limits and Additional Information",
//                    "",
//                    100,
//                    new float[]{15, 85},
//                    null
//            );
//            xmlGenerator.generateTable(
//                    getWZITSselectedApp(controller.getProject()),
//                    "wzitsSelctApplications",
//                    false,
//                    true,
//                    false,
//                    "WZITS Selected Applications",
//                    "",
//                    100,
//                    new float[]{33,33,33},
//                    new String[] {"left", "center", "center"}
//            );
////            xmlGenerator.generateTable(
////                    getBenefitRefinement(controller.getProject()),
////                    "applicationRefinementBenefits",
////                    false,
////                    true,
////                    false,
////                    "Benefits",
////                    "",
////                    100,
////                    new float[]{40,10,50},
////                    new String[] {"left", "center", "left"}
////            );
//            xmlGenerator.generateTable(
//                    getBenefitRefinement(controller.getProject()),
//                    "applicationRefinementBenefits",
//                    false,
//                    true,
//                    false,
//                    "Benefits",
//                    "",
//                    50,
//                    new float[]{80,20},
//                    new String[] {"left", "center"}
//            );
//            xmlGenerator.generateTable(
//                    getBenefitRefinementComment(controller.getProject()),
//                    "applicationRefinementBenefitsComment",
//                    false,
//                    true,
//                    false,
//                    "",
//                    "",
//                    100,
//                    new float[]{100},
//                    new String[] {"left"}
//            );
//            xmlGenerator.generateTable(
//                    getInstitutionalJurisdictional(controller.getProject()),
//                    "institutionalJusdictional",
//                    false,
//                    true,
//                    false,
//                    "Institutional/Jurisdictional",
//                    "",
//                    100,
//                    new float[]{40,10,50},
//                    new String[] {"left", "center", "left"}
//            );
//            xmlGenerator.generateTable(
//                    getLegalPolicy(controller.getProject()),
//                    "legalPolicy",
//                    false,
//                    true,
//                    false,
//                    "Legal/Policy",
//                    "",
//                    100,
//                    new float[]{40,10,50},
//                    new String[] {"left", "center", "left"}
//            );
//            xmlGenerator.generateTable(
//                    getStakeholderBuyin(controller.getProject()),
//                    "StakeholderBuyin",
//                    false,
//                    true,
//                    false,
//                    "Stakeholder Buy-in",
//                    "",
//                    100,
//                    new float[]{40,10,50},
//                    new String[] {"left", "center", "left"}
//            );
            xmlGenerator.generate();
        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
    }

    public void createFactSheet4(MainController controller) {
        String xmlFilePath = PDFReportHelper.getResFolderLocation() + "fact-sheet-pdf-resources.xml";
        try {
            String projectFileName = "[Unsaved Project File]";
            File projectFile = controller.getProject().getSaveFile();
            if (projectFile != null) {
                projectFileName = projectFile.getAbsolutePath();
            }
            XMLGenerator xmlGenerator = new XMLGenerator(projectFileName,
                    xmlFilePath,
                    "Work Zone ITS Tool",
                    "Fact Sheet #4"
            );
            generateFactSheet4Content(controller, xmlGenerator, false);
//            final String reportTitle = getFactSheetName(4);
//            xmlGenerator.generateTitle(reportTitle, Pos.CENTER);
//            xmlGenerator.generateTable(
//                    getProjectInformationTable(controller.getProject()),
//                    "projectInformation",
//                    false,
//                    false,
//                    false,
//                    "Project Information:",
//                    "",
//                    100,
//                    null,
//                    null
//            );
            xmlGenerator.generate();
        } catch (ParserConfigurationException | TransformerException e) {  // "Error related to exceptions will disappear when you've added the "xmlGenerator.generate();" line
            e.printStackTrace();
        }
    }

    public void createFactSheet5(MainController controller) {
        String xmlFilePath = PDFReportHelper.getResFolderLocation() + "fact-sheet-pdf-resources.xml";
        try {
            String projectFileName = "[Unsaved Project File]";
            File projectFile = controller.getProject().getSaveFile();
            if (projectFile != null) {
                projectFileName = projectFile.getAbsolutePath();
            }
            XMLGenerator xmlGenerator = new XMLGenerator(projectFileName,
                    xmlFilePath,
                    "Work Zone ITS Tool",
                    "Fact Sheet #5"
            );
            generateFactSheet5Content(controller, xmlGenerator, false);
//            final String reportTitle = getFactSheetName(5);
//            xmlGenerator.generateTitle(reportTitle, Pos.CENTER);
//            xmlGenerator.generateTable(
//                    getProjectInformationTable(controller.getProject()),
//                    "projectInformation",
//                    false,
//                    false,
//                    false,
//                    "Project Information",
//                    "",
//                    100,
//                    null,
//                    null
//            );
//            xmlGenerator.generateTable(
//                    getProjectLimitsAndDescriptionTable(controller.getProject()),
//                    "projectDescription",
//                    false,
//                    false,
//                    false,
//                    "Project Limits and Additional Information",
//                    "",
//                    100,
//                    new float[]{15, 85},
//                    null
//            );
//            xmlGenerator.generateTable(
//                    getDocumentConsOperations(controller.getProject()),
//                    "docConOps",
//                    false,
//                    true,
//                    false,
//                    "Document Concept of Operations",
//                    "",
//                    100,
//                    new float[]{40, 10, 50},
//                    new String[] {"left", "center", "left"}
//            );
//            xmlGenerator.generateTable(
//                    getSysPlanDesgnRequirements(controller.getProject()),
//                    "requirements",
//                    false,
//                    true,
//                    false,
//                    "Requirements",
//                    "",
//                    100,
//                    new float[]{40, 10, 50},
//                    new String[] {"left", "center", "left"}
//            );
//            xmlGenerator.generateTable(
//                    getTestStrategy(controller.getProject()),
//                    "testStrategy",
//                    false,
//                    true,
//                    false,
//                    "Testing Strategy",
//                    "",
//                    100,
//                    new float[]{40, 10, 50},
//                    new String[] {"left", "center", "left"}
//            );
//            xmlGenerator.generateTable(
//                    getOpsandMaitenance(controller.getProject()),
//                    "opsAndMaintenance",
//                    false,
//                    true,
//                    false,
//                    "Operations & Maintenance",
//                    "",
//                    100,
//                    new float[]{40, 10, 50},
//                    new String[] {"left", "center", "left"}
//            );
//            xmlGenerator.generateTable(
//                    getStaffTrainingNeeds(controller.getProject()),
//                    "staffTrainNeeds",
//                    false,
//                    true,
//                    false,
//                    "Staff Training Needs",
//                    "",
//                    100,
//                    new float[]{40, 10, 50},
//                    new String[] {"left", "center", "left"}
//            );
//            xmlGenerator.generateTable(
//                    getSysSecurity(controller.getProject()),
//                    "systemSecurity",
//                    false,
//                    true,
//                    false,
//                    "System Security",
//                    "",
//                    100,
//                    new float[]{40, 10, 50},
//                    new String[] {"left", "center", "left"}
//            );
//            xmlGenerator.generateTable(
//                    getEvaluation(controller.getProject()),
//                    "evaluation",
//                    false,
//                    true,
//                    false,
//                    "Evaluation",
//                    "",
//                    100,
//                    new float[]{40, 10, 50},
//                    new String[] {"left", "center", "left"}
//            );
//            xmlGenerator.generateTable(
//                    getBenefitCost(controller.getProject()),
//                    "benefitCost",
//                    false,
//                    true,
//                    false,
//                    "Benefit/Cost",
//                    "",
//                    100,
//                    new float[]{40, 10, 50},
//                    new String[] {"left", "center", "left"}
//            );
            xmlGenerator.generate();
        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
    }

    public void createFactSheet6(MainController controller) {
        String xmlFilePath = PDFReportHelper.getResFolderLocation() + "fact-sheet-pdf-resources.xml";
        try {
            String projectFileName = "[Unsaved Project File]";
            File projectFile = controller.getProject().getSaveFile();
            if (projectFile != null) {
                projectFileName = projectFile.getAbsolutePath();
            }
            XMLGenerator xmlGenerator = new XMLGenerator(projectFileName,
                    xmlFilePath,
                    "Work Zone ITS Tool",
                    "Fact Sheet #6"
            );
            generateFactSheet6Content(controller, xmlGenerator, false);
//            final String reportTitle = getFactSheetName(6);
//            xmlGenerator.generateTitle(reportTitle, Pos.CENTER);
//            xmlGenerator.generateTable(
//                    getProjectInformationTable(controller.getProject()),
//                    "projectInformation",
//                    false,
//                    false,
//                    false,
//                    "Project Information",
//                    "",
//                    100,
//                    null,
//                    null
//            );
//            xmlGenerator.generateTable(
//                    getDirectOrIndirect(controller.getProject()),
//                    "directOrIndirect",
//                    false,
//                    true,
//                    false,
//                    "Direct/Indirect",
//                    "",
//                    100,
//                    new float[]{40, 10, 50},
//                    new String[] {"left", "center", "left"}
//            );
//            xmlGenerator.generateTable(
//                    getAwardMechanism(controller.getProject()),
//                    "awardMechanism",
//                    false,
//                    true,
//                    false,
//                    "Award Mechanism",
//                    "",
//                    100,
//                    new float[]{40, 10, 50},
//                    new String[] {"left", "center", "left"}
//            );
//            xmlGenerator.generateTable(
//                    getRFPrequirements(controller.getProject()),
//                    "rfpRequirements",
//                    false,
//                    true,
//                    false,
//                    "RFP Requirements",
//                    "",
//                    100,
//                    new float[]{40, 10, 50},
//                    new String[] {"left", "center", "left"}
//            );
//            xmlGenerator.generateTable(
//                    getSelectedVendor(controller.getProject()),
//                    "selectedVendor",
//                    false,
//                    true,
//                    false,
//                    "Selected Vendor",
//                    "",
//                    100,
//                    new float[]{40, 10, 50},
//                    new String[] {"left", "center", "left"}
//            );
            xmlGenerator.generate();

        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
    }

    public void createFactSheet7(MainController controller) {
        String xmlFilePath = PDFReportHelper.getResFolderLocation() + "fact-sheet-pdf-resources.xml";
        try {
            String projectFileName = "[Unsaved Project File]";
            File projectFile = controller.getProject().getSaveFile();
            if (projectFile != null) {
                projectFileName = projectFile.getAbsolutePath();
            }
            XMLGenerator xmlGenerator = new XMLGenerator(projectFileName,
                    xmlFilePath,
                    "Work Zone ITS Tool",
                    "Fact Sheet #7"
            );
            generateFactSheet7Content(controller, xmlGenerator, false);
//            final String reportTitle = getFactSheetName(7);
//            xmlGenerator.generateTitle(reportTitle, Pos.CENTER);
//            xmlGenerator.generateTable(
//                    getProjectInformationTable(controller.getProject()),
//                    "projectInformation",
//                    false,
//                    false,
//                    false,
//                    "Project Information",
//                    "",
//                    100,
//                    null,
//                    null
//            );
//            xmlGenerator.generateTable(
//                    getImplementSysPlans(controller.getProject()),
//                    "implementSysPlans",
//                    false,
//                    true,
//                    false,
//                    "Implementing System Plans",
//                    "",
//                    100,
//                    new float[]{40, 10, 50},
//                    new String[] {"left", "center", "left"}
//            );
//            xmlGenerator.generateTable(
//                    getScheduleDecisions(controller.getProject()),
//                    "schedulingDecisions",
//                    false,
//                    true,
//                    false,
//                    "Scheduling Decisions",
//                    "",
//                    100,
//                    new float[]{40, 10, 50},
//                    new String[] {"left", "center", "left"}
//            );
//            xmlGenerator.generateTable(
//                    getSystemAcceptance(controller.getProject()),
//                    "systemAcceptanceTesting",
//                    false,
//                    true,
//                    false,
//                    "System Acceptance Testing",
//                    "",
//                    100,
//                    new float[]{40, 10, 50},
//                    new String[] {"left", "center", "left"}
//            );
//            xmlGenerator.generateTable(
//                    getHandleDeployIssues(controller.getProject()),
//                    "handlingDeployIssues",
//                    false,
//                    true,
//                    false,
//                    "Handling Deployment Issues",
//                    "",
//                    100,
//                    new float[]{40, 10, 50},
//                    new String[] {"left", "center", "left"}
//            );
            xmlGenerator.generate();
        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
    }

    public void createFactSheet8(MainController controller) {
        String xmlFilePath = PDFReportHelper.getResFolderLocation() + "fact-sheet-pdf-resources.xml";
        try {
            String projectFileName = "[Unsaved Project File]";
            File projectFile = controller.getProject().getSaveFile();
            if (projectFile != null) {
                projectFileName = projectFile.getAbsolutePath();
            }
            XMLGenerator xmlGenerator = new XMLGenerator(projectFileName,
                    xmlFilePath,
                    "Work Zone ITS Tool",
                    "Fact Sheet #8"
            );
            generateFactSheet8Content(controller, xmlGenerator, false);
//            final String reportTitle = getFactSheetName(8);
//            xmlGenerator.generateTitle(reportTitle, Pos.CENTER);
//            xmlGenerator.generateTable(
//                    getProjectInformationTable(controller.getProject()),
//                    "projectInformation",
//                    false,
//                    false,
//                    false,
//                    "Project Information",
//                    "",
//                    100,
//                    null,
//                    null
//            );
//            xmlGenerator.generateTable(
//                    getChangingWorkZone(controller.getProject()),
//                    "changeWorkZone",
//                    false,
//                    true,
//                    false,
//                    "Changing Work Zone",
//                    "",
//                    100,
//                    new float[]{40, 10, 50},
//                    new String[] {"left", "center", "left"}
//            );
//            xmlGenerator.generateTable(
//                    getUsingSharingITSinfo(controller.getProject()),
//                    "usingSharingITSinfo",
//                    false,
//                    true,
//                    false,
//                    "Using/Sharing ITS Info",
//                    "",
//                    100,
//                    new float[]{40, 10, 50},
//                    new String[] {"left", "center", "left"}
//            );
//            xmlGenerator.generateTable(
//                    getMaintainAdequateStaff(controller.getProject()),
//                    "maintaingAdequteStaff",
//                    false,
//                    true,
//                    false,
//                    "Maintaining Adequate Staff",
//                    "",
//                    100,
//                    new float[]{40, 10, 50},
//                    new String[] {"left", "center", "left"}
//            );
//            xmlGenerator.generateTable(
//                    getLeverageSupport(controller.getProject()),
//                    "leveraginPublicSupport",
//                    false,
//                    true,
//                    false,
//                    "Leveraging Public Support",
//                    "",
//                    100,
//                    new float[]{40, 10, 50},
//                    new String[] {"left", "center", "left"}
//            );
//            xmlGenerator.generateTable(
//                    getSysMonitoringEvaluation(controller.getProject()),
//                    "systemMonitoringEvaluation",
//                    false,
//                    true,
//                    false,
//                    "System Monitoring/Evaluation",
//                    "",
//                    100,
//                    new float[]{40, 10, 50},
//                    new String[] {"left", "center", "left"}
//            );
            xmlGenerator.generate();
        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
    }

    public void createAllFactSheets(MainController controller) {
        String xmlFilePath = PDFReportHelper.getResFolderLocation() + "fact-sheet-pdf-resources.xml";

        try {
            String projectFileName = "[Unsaved Project File]";
            File projectFile = controller.getProject().getSaveFile();
            if (projectFile != null) {
                projectFileName = projectFile.getAbsolutePath();
            }

            XMLGenerator xmlGenerator = new XMLGenerator(projectFileName,
                    xmlFilePath,
                    "Work Zone ITS Tool",
                    "All Fact Sheets"
            );
            generateFactSheet1Content(controller, xmlGenerator, true);
            xmlGenerator.addPageBreak();
            generateFactSheet2Content(controller, xmlGenerator, true);
            xmlGenerator.addPageBreak();
            generateFactSheet3Content(controller, xmlGenerator, true);
            xmlGenerator.addPageBreak();
            generateFactSheet4Content(controller, xmlGenerator, true);
            xmlGenerator.addPageBreak();
            generateFactSheet5Content(controller, xmlGenerator, true);
            xmlGenerator.addPageBreak();
            generateFactSheet6Content(controller, xmlGenerator, true);
            xmlGenerator.addPageBreak();
            generateFactSheet7Content(controller, xmlGenerator, true);
            xmlGenerator.addPageBreak();
            generateFactSheet8Content(controller, xmlGenerator, true);
            xmlGenerator.generate();

        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
    }

    private void generateFactSheet1Content(MainController controller, XMLGenerator xmlGenerator, boolean includeFactSheetIndexLabel) {
        Image projPhoto = controller.getProject().getProjPhoto();
        String imgResourcePath = PDFReportHelper.getResFolderLocation();
        if (projPhoto != null) {
            try {
                ImageIO.write(SwingFXUtils.fromFXImage(projPhoto, null), "png", new File(imgResourcePath + PDFReportHelper.FILE_FS1_WZIMAGE));
            } catch (IOException ie) {
                // Set to null so that the image will not be included in the report
                System.out.println("Something went wrong with image creation");
                ie.printStackTrace();
                projPhoto = null;
            }
        }
        final String factSheetTitle = (includeFactSheetIndexLabel ? "Fact Sheet #1: " : "") + getFactSheetName(1);
        xmlGenerator.generateTitle(factSheetTitle, Pos.CENTER);
        xmlGenerator.generateTable(
                getProjectInformationTable(controller.getProject()),
                "projectInformation",
                false,
                false,
                false,
                "Project Information",
                "",
                100,
                null,
                null
        );
        xmlGenerator.generateTable(
                getProjectLimitsAndDescriptionTable(controller.getProject()),
                "projectDescription",
                false,
                false,
                false,
                "Project Limits and Additional Information",
                "",
                100,
                new float[]{15, 85},
                null
        );
        double imgWidth = 5.21;
        double imgHeight = 3.91;
        if (projPhoto != null) {
            xmlGenerator.generateImage(imgResourcePath + PDFReportHelper.FILE_FS1_WZIMAGE, //src/Toolbox/XML/output/hourly-system-delay.png
                    imgWidth,  // projectImage.getWidth(),
                    imgHeight,  // projectImage.getHeight(),
                    "Project Photo", "");
        }
        xmlGenerator.generateTable(
                getFacilityAndBaseConditions(controller.getProject()),
                "facilityAndBaseConditions",
                false,
                true,
                false,
                "Facility and Base Conditions",
                "",
                100,
                new float[]{23, 12, 65},
                new String[]{"left", "center", "left"}
        );
        xmlGenerator.generateTable(
                getWorkZoneConfiguration(controller.getProject()),
                "workZoneConfiguration",
                false,
                true,
                false,
                "Facility and Base Conditions",
                "",
                100,
                new float[]{25, 10, 65},
                new String[]{"left", "center", "left"}
        );
        // getITSGoalsTable
        xmlGenerator.generateTable(
                getITSGoalsTable(controller.getProject()),
                "itsGoalsTable",
                false,
                true,
                false,
                "ITS Goals Summary",
                "",
                100,
                new float[]{20, 70, 10},
                new String[]{"center", "left", "center"}
        );
        xmlGenerator.generateTable(
                getFeasibilityTable(controller.getProject()),
                "feasibilityTable",
                false,
                true,
                false,
                "Feasibility Assessment",
                "",
                100,
                new float[]{50, 50},
                new String[]{"center", "left"}
        );
    }

    private void generateFactSheet2Content(MainController controller, XMLGenerator xmlGenerator, boolean includeFactSheetIndexLabel) {
        final String factSheetTitle = (includeFactSheetIndexLabel ? "Fact Sheet #2: " : "") + getFactSheetName(2);
//        final String reportTitle = getFactSheetName(2);
        xmlGenerator.generateTitle(factSheetTitle, Pos.CENTER);
        xmlGenerator.generateTable(
                getProjectInformationTable(controller.getProject()),
                "projectInformation",
                false,
                false,
                false,
                "Project Information:",
                "",
                100,
                null,
                null
        );
        xmlGenerator.generateTable(
                getProjectLimitsAndDescriptionTable(controller.getProject()),
                "projectDescription",
                false,
                false,
                false,
                "Project Limits and Additional Information",
                "",
                100,
                new float[]{15, 85},
                null
        );
        xmlGenerator.generateTable(
                createCoreTeamTable(controller.getProject()),
                "coreTeamTable",
                false,
                true,
                false,
                "Selected Core Team and Stakeholders",
                "",
                100,
                new float[]{5, 45, 35, 15},
                new String[]{"center", "left", "center", "center"}
        );
        xmlGenerator.generateTable(
                createOtherStakeholdersTable(controller.getProject()),
                "otherStakeholdersTable",
                false,
                true,
                false,
                "",
                "",
                100,
                new float[]{5, 45, 35, 15},
                new String[]{"center", "left", "center", "center"}
        );

        xmlGenerator.generateTable(
                createITSResourcesTable(controller.getProject()),
                "itsResourcesTable",
                false,
                true,
                false,
                "ITS Resources",
                "",
                100,
                new float[]{5, 30, 10, 55},
                new String[]{"center", "left", "center", "left"}
        );
    }

    private void generateFactSheet3Content(MainController controller, XMLGenerator xmlGenerator, boolean includeFactSheetIndexLabel) {
//        final String reportTitle = getFactSheetName(3);
        final String factSheetTitle = (includeFactSheetIndexLabel ? "Fact Sheet #3: " : "") + getFactSheetName(3);
        xmlGenerator.generateTitle(factSheetTitle, Pos.CENTER);
        xmlGenerator.generateTable(
                getProjectInformationTable(controller.getProject()),
                "projectInformation",
                false,
                false,
                false,
                "Project Information",
                "",
                100,
                null,
                null
        );
        xmlGenerator.generateTable(
                getProjectLimitsAndDescriptionTable(controller.getProject()),
                "projectDescription",
                false,
                false,
                false,
                "Project Limits and Additional Information",
                "",
                100,
                new float[]{15, 85},
                null
        );
        xmlGenerator.generateTable(
                getWZITSselectedApp(controller.getProject()),
                "wzitsSelctApplications",
                false,
                true,
                false,
                "WZITS Selected Applications",
                "",
                100,
                new float[]{33,33,33},
                new String[] {"left", "center", "center"}
        );
//            xmlGenerator.generateTable(
//                    getBenefitRefinement(controller.getProject()),
//                    "applicationRefinementBenefits",
//                    false,
//                    true,
//                    false,
//                    "Benefits",
//                    "",
//                    100,
//                    new float[]{40,10,50},
//                    new String[] {"left", "center", "left"}
//            );
        xmlGenerator.generateTable(
                getBenefitRefinement(controller.getProject()),
                "applicationRefinementBenefits",
                false,
                true,
                false,
                "Benefits",
                "",
                50,
                new float[]{80,20},
                new String[] {"left", "center"}
        );
        xmlGenerator.generateTable(
                getBenefitRefinementComment(controller.getProject()),
                "applicationRefinementBenefitsComment",
                false,
                true,
                false,
                "",
                "",
                100,
                new float[]{100},
                new String[] {"left"}
        );
        xmlGenerator.generateTable(
                getInstitutionalJurisdictional(controller.getProject()),
                "institutionalJusdictional",
                false,
                true,
                false,
                "Institutional/Jurisdictional",
                "",
                100,
                new float[]{40,10,50},
                new String[] {"left", "center", "left"}
        );
        xmlGenerator.generateTable(
                getLegalPolicy(controller.getProject()),
                "legalPolicy",
                false,
                true,
                false,
                "Legal/Policy",
                "",
                100,
                new float[]{40,10,50},
                new String[] {"left", "center", "left"}
        );
        xmlGenerator.generateTable(
                getStakeholderBuyin(controller.getProject()),
                "StakeholderBuyin",
                false,
                true,
                false,
                "Stakeholder Buy-in",
                "",
                100,
                new float[]{40,10,50},
                new String[] {"left", "center", "left"}
        );
    }

    private void generateFactSheet4Content(MainController controller, XMLGenerator xmlGenerator, boolean includeFactSheetIndexLabel) {
//        final String reportTitle = getFactSheetName(4);

        Image conceptOfOperationsImage = controller.getProject().getConOpsDiagram();
        String imgResourcePath = PDFReportHelper.getResFolderLocation();
        if (conceptOfOperationsImage != null) {
            try {
                ImageIO.write(SwingFXUtils.fromFXImage(conceptOfOperationsImage, null), "png", new File(imgResourcePath + PDFReportHelper.FILE_FS2_WZIMAGE));
            } catch (IOException ie) {
                // Set to null so that the image will not be included in the report
                System.out.println("Something went wrong with image creation");
                ie.printStackTrace();
                conceptOfOperationsImage = null;
            }
        }

        final String factSheetTitle = (includeFactSheetIndexLabel ? "Fact Sheet #4: " : "") + getFactSheetName(4);

        xmlGenerator.generateTitle(factSheetTitle, Pos.CENTER);
        xmlGenerator.generateTable(
                getProjectInformationTable(controller.getProject()),
                "projectInformation",
                false,
                false,
                false,
                "Project Information:",
                "",
                100,
                null,
                null
        );

        if (conceptOfOperationsImage != null) {
//            double imgWidth = 5.21;
//            double imgHeight = 3.91;
            double imgWidth = conceptOfOperationsImage.getWidth();
            double imgHeight = conceptOfOperationsImage.getHeight();
            System.out.println("Width 1: " + String.format("%.2f", imgWidth));
            System.out.println("Height 1: " + String.format("%.2f", imgHeight));
            double MAX_IMG_WIDTH_PX = 525;
            double MAX_IMG_HEIGHT_PX = 500;
            if (imgWidth > MAX_IMG_WIDTH_PX) {
                imgHeight = imgHeight * MAX_IMG_WIDTH_PX / imgWidth;
                imgWidth = MAX_IMG_WIDTH_PX;
            }
            if (imgHeight > MAX_IMG_HEIGHT_PX) {
                imgWidth = imgWidth * MAX_IMG_HEIGHT_PX / imgHeight;
                imgHeight = MAX_IMG_HEIGHT_PX;
            }
            System.out.println("Width 2: " + String.format("%.2f", imgWidth));
            System.out.println("Height 2: " + String.format("%.2f", imgHeight));
            xmlGenerator.generateImagePixels(imgResourcePath + PDFReportHelper.FILE_FS2_WZIMAGE, //src/Toolbox/XML/output/hourly-system-delay.png
                    imgWidth,  // projectImage.getWidth(),
                    imgHeight,  // projectImage.getHeight(),
                    "Concept of Operations", "");
        }
    }

    private void generateFactSheet5Content(MainController controller, XMLGenerator xmlGenerator, boolean includeFactSheetIndexLabel) {
//        final String reportTitle = getFactSheetName(5);
        final String factSheetTitle = (includeFactSheetIndexLabel ? "Fact Sheet #5: " : "") + getFactSheetName(5);
        xmlGenerator.generateTitle(factSheetTitle, Pos.CENTER);
        xmlGenerator.generateTable(
                getProjectInformationTable(controller.getProject()),
                "projectInformation",
                false,
                false,
                false,
                "Project Information",
                "",
                100,
                null,
                null
        );
        xmlGenerator.generateTable(
                getProjectLimitsAndDescriptionTable(controller.getProject()),
                "projectDescription",
                false,
                false,
                false,
                "Project Limits and Additional Information",
                "",
                100,
                new float[]{15, 85},
                null
        );
        xmlGenerator.generateTable(
                getDocumentConsOperations(controller.getProject()),
                "docConOps",
                false,
                true,
                false,
                "Document Concept of Operations",
                "",
                100,
                new float[]{40, 10, 50},
                new String[] {"left", "center", "left"}
        );
        xmlGenerator.generateTable(
                getSysPlanDesgnRequirements(controller.getProject()),
                "requirements",
                false,
                true,
                false,
                "Requirements",
                "",
                100,
                new float[]{40, 10, 50},
                new String[] {"left", "center", "left"}
        );
        xmlGenerator.generateTable(
                getTestStrategy(controller.getProject()),
                "testStrategy",
                false,
                true,
                false,
                "Testing Strategy",
                "",
                100,
                new float[]{40, 10, 50},
                new String[] {"left", "center", "left"}
        );
        xmlGenerator.generateTable(
                getOpsandMaitenance(controller.getProject()),
                "opsAndMaintenance",
                false,
                true,
                false,
                "Operations & Maintenance",
                "",
                100,
                new float[]{40, 10, 50},
                new String[] {"left", "center", "left"}
        );
        xmlGenerator.generateTable(
                getStaffTrainingNeeds(controller.getProject()),
                "staffTrainNeeds",
                false,
                true,
                false,
                "Staff Training Needs",
                "",
                100,
                new float[]{40, 10, 50},
                new String[] {"left", "center", "left"}
        );
        xmlGenerator.generateTable(
                getSysSecurity(controller.getProject()),
                "systemSecurity",
                false,
                true,
                false,
                "System Security",
                "",
                100,
                new float[]{40, 10, 50},
                new String[] {"left", "center", "left"}
        );
        xmlGenerator.generateTable(
                getEvaluation(controller.getProject()),
                "evaluation",
                false,
                true,
                false,
                "Evaluation",
                "",
                100,
                new float[]{40, 10, 50},
                new String[] {"left", "center", "left"}
        );
        xmlGenerator.generateTable(
                getBenefitCost(controller.getProject()),
                "benefitCost",
                false,
                true,
                false,
                "Benefit/Cost",
                "",
                100,
                new float[]{40, 10, 50},
                new String[] {"left", "center", "left"}
        );
    }

    private void generateFactSheet6Content(MainController controller, XMLGenerator xmlGenerator, boolean includeFactSheetIndexLabel) {
//        final String reportTitle = getFactSheetName(6);
        final String factSheetTitle = (includeFactSheetIndexLabel ? "Fact Sheet #6: " : "") + getFactSheetName(6);
        xmlGenerator.generateTitle(factSheetTitle, Pos.CENTER);
        xmlGenerator.generateTable(
                getProjectInformationTable(controller.getProject()),
                "projectInformation",
                false,
                false,
                false,
                "Project Information",
                "",
                100,
                null,
                null
        );
        xmlGenerator.generateTable(
                getDirectOrIndirect(controller.getProject()),
                "directOrIndirect",
                false,
                true,
                false,
                "Direct/Indirect",
                "",
                100,
                new float[]{40, 10, 50},
                new String[] {"left", "center", "left"}
        );
        xmlGenerator.generateTable(
                getAwardMechanism(controller.getProject()),
                "awardMechanism",
                false,
                true,
                false,
                "Award Mechanism",
                "",
                100,
                new float[]{40, 10, 50},
                new String[] {"left", "center", "left"}
        );
        xmlGenerator.generateTable(
                getRFPrequirements(controller.getProject()),
                "rfpRequirements",
                false,
                true,
                false,
                "RFP Requirements",
                "",
                100,
                new float[]{40, 10, 50},
                new String[] {"left", "center", "left"}
        );
        xmlGenerator.generateTable(
                getSelectedVendor(controller.getProject()),
                "selectedVendor",
                false,
                true,
                false,
                "Selected Vendor",
                "",
                100,
                new float[]{40, 10, 50},
                new String[] {"left", "center", "left"}
        );
    }

    private void generateFactSheet7Content(MainController controller, XMLGenerator xmlGenerator, boolean includeFactSheetIndexLabel) {
//        final String reportTitle = getFactSheetName(7);
        final String factSheetTitle = (includeFactSheetIndexLabel ? "Fact Sheet #7: " : "") + getFactSheetName(7);
        xmlGenerator.generateTitle(factSheetTitle, Pos.CENTER);
        xmlGenerator.generateTable(
                getProjectInformationTable(controller.getProject()),
                "projectInformation",
                false,
                false,
                false,
                "Project Information",
                "",
                100,
                null,
                null
        );
        xmlGenerator.generateTable(
                getImplementSysPlans(controller.getProject()),
                "implementSysPlans",
                false,
                true,
                false,
                "Implementing System Plans",
                "",
                100,
                new float[]{40, 10, 50},
                new String[] {"left", "center", "left"}
        );
        xmlGenerator.generateTable(
                getScheduleDecisions(controller.getProject()),
                "schedulingDecisions",
                false,
                true,
                false,
                "Scheduling Decisions",
                "",
                100,
                new float[]{40, 10, 50},
                new String[] {"left", "center", "left"}
        );
        xmlGenerator.generateTable(
                getSystemAcceptance(controller.getProject()),
                "systemAcceptanceTesting",
                false,
                true,
                false,
                "System Acceptance Testing",
                "",
                100,
                new float[]{40, 10, 50},
                new String[] {"left", "center", "left"}
        );
        xmlGenerator.generateTable(
                getHandleDeployIssues(controller.getProject()),
                "handlingDeployIssues",
                false,
                true,
                false,
                "Handling Deployment Issues",
                "",
                100,
                new float[]{40, 10, 50},
                new String[] {"left", "center", "left"}
        );
    }

    private void generateFactSheet8Content(MainController controller, XMLGenerator xmlGenerator, boolean includeFactSheetIndexLabel) {
//        final String reportTitle = getFactSheetName(8);
        final String factSheetTitle = (includeFactSheetIndexLabel ? "Fact Sheet #8: " : "") + getFactSheetName(8);
        xmlGenerator.generateTitle(factSheetTitle, Pos.CENTER);
        xmlGenerator.generateTable(
                getProjectInformationTable(controller.getProject()),
                "projectInformation",
                false,
                false,
                false,
                "Project Information",
                "",
                100,
                null,
                null
        );
        xmlGenerator.generateTable(
                getChangingWorkZone(controller.getProject()),
                "changeWorkZone",
                false,
                true,
                false,
                "Changing Work Zone",
                "",
                100,
                new float[]{40, 10, 50},
                new String[] {"left", "center", "left"}
        );
        xmlGenerator.generateTable(
                getUsingSharingITSinfo(controller.getProject()),
                "usingSharingITSinfo",
                false,
                true,
                false,
                "Using/Sharing ITS Info",
                "",
                100,
                new float[]{40, 10, 50},
                new String[] {"left", "center", "left"}
        );
        xmlGenerator.generateTable(
                getMaintainAdequateStaff(controller.getProject()),
                "maintaingAdequteStaff",
                false,
                true,
                false,
                "Maintaining Adequate Staff",
                "",
                100,
                new float[]{40, 10, 50},
                new String[] {"left", "center", "left"}
        );
        xmlGenerator.generateTable(
                getLeverageSupport(controller.getProject()),
                "leveraginPublicSupport",
                false,
                true,
                false,
                "Leveraging Public Support",
                "",
                100,
                new float[]{40, 10, 50},
                new String[] {"left", "center", "left"}
        );
        xmlGenerator.generateTable(
                getSysMonitoringEvaluation(controller.getProject()),
                "systemMonitoringEvaluation",
                false,
                true,
                false,
                "System Monitoring/Evaluation",
                "",
                100,
                new float[]{40, 10, 50},
                new String[] {"left", "center", "left"}
        );
    }

    public static String getFactSheetName(int factSheetIdx) {
        switch (factSheetIdx) {
            default:
            case 1:
                return "Project Information and WZ Metadata";
            case 2:
                return "WZITS Stakeholders";
            case 3:
                return "WZITS Applications and Refinement";
            case 4:
                return "WZITS Concept of Operations";
            case 5:
                return "WZITS Project Documentation";
            case 6:
                return "WZITS Procurement";
            case 7:
                return "WZITS System Deployment";
            case 8:
                return "WZITS Operations and Maintenance";

        }
    }
}
