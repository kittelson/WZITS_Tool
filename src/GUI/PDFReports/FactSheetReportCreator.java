package GUI.PDFReports;

import GUI.Helper.NodeFactory;
import GUI.MainController;
import GUI.PDFReports.XML.XMLGenerator;
import GUI.Tables.Step1TableHelper;
import core.*;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
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
        String[][] projectInfo = new String[5][2];
        int ri = 0;
        projectInfo[ri][0] = "State Agency:";
        projectInfo[ri][1] = project.getAgency() != null ? project.getAgency() : "";
        ri++;
        projectInfo[ri][0] = "Analyst:";
        projectInfo[ri][1] = project.getAnalyst() != null ? project.getAnalyst() : "";
        ri++;
        projectInfo[ri][0] = "Date:";
        projectInfo[ri][1] = project.getDateString();
        ri++;
        projectInfo[ri][0] = "Project Name:";
        projectInfo[ri][1] = project.getName() != null ? project.getName() : "";
//        ri++;
//        projectInfo[ri][0] = "Project Description:";
//        projectInfo[ri][1] = project.getDescription() != null ? project.getDescription() : "";
//        ri++;
//        projectInfo[ri][0] = "Project Limits:";
//        projectInfo[ri][1] = project.getLimits() != null ? project.getLimits() : "";
        ri++;
        projectInfo[ri][0] = "Signature Sign-Off:";
        projectInfo[ri][1] = "";
        return projectInfo;
    }

    public String[][] getProjectLimitsAndDescriptionTable(Project project) {
        String[][] tableData = new String[2][2];
        tableData[0][0] = "Project Limits";
        tableData[0][1] = project.getLimits() != null ? project.getLimits() : "None specified";
        tableData[1][0] = "Project Description";
        tableData[1][1] = project.getDescription() != null ? project.getDescription() : "";
        return tableData;
    }

    public String[][] getProjectDescriptionTable(Project project) {
        String[][] tableData = new String[2][2];
        tableData[0][0] = "Project Description";
        tableData[0][1] = project.getDescription() != null ? project.getDescription() : "";
        return tableData;
    }

    public String[][] getProjectLimitsTable(Project project) {
        String[][] tableData = new String[1][2];
        tableData[0][0] = "Project Limits";
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
        tableData[ri][0] = "Average Annual Daily Traffic (AADT)";
        tableData[ri][1] = String.format("%,d", project.getAadt());
        tableData[ri][2] = project.getAadtComment() != null ? project.getAadtComment() : "";
        ri++;
        tableData[ri][0] = "Functional Class of Roadway";
        tableData[ri][1] = project.getFunctionalClass() != null ? project.getFunctionalClass() : "";
        tableData[ri][2] = project.getFcrComment() != null ? project.getFcrComment() : "";
        ri++;
        tableData[ri][0] = "Maintaining Agency";
        tableData[ri][1] = project.getMaintainingAgency() != null ? project.getMaintainingAgency() : "";
        tableData[ri][2] = project.getMaComment() != null ? project.getMaComment() : "";
        ri++;
        tableData[ri][0] = "Area Type";
        tableData[ri][1] = project.getAreaType() != null ? project.getAreaType() : "";
        tableData[ri][2] = project.getAtComment() != null ? project.getAtComment() : "";
        ri++;
        tableData[ri][0] = "Number of Roadway Lanes (1 Direction)";
        tableData[ri][1] = String.valueOf(project.getNumRoadwayLanes());
        tableData[ri][2] = project.getNrlComment() != null ? project.getNrlComment() : "";
        ri++;
        tableData[ri][0] = "Shoulder Width (ft)";
        tableData[ri][1] = String.valueOf(project.getShoulderWidth());
        tableData[ri][2] = project.getSwComment() != null ? project.getSwComment() : "";
        ri++;
        tableData[ri][0] = "Posted Speed Limit (mph)";
        tableData[ri][1] = String.valueOf(project.getSpeedLimit());
        tableData[ri][2] = project.getPslComment() != null ? project.getPslComment() : "";
        ri++;
        tableData[ri][0] = "Lane Width";
        tableData[ri][1] = String.valueOf(project.getLaneWidthBase());
        tableData[ri][2] = project.getLwComment() != null ? project.getLwComment() : "";
        ri++;
        tableData[ri][0] = "Signalized Corridor";
        tableData[ri][1] = project.getSignalizedCorridor() != null ? project.getSignalizedCorridor() : "";
        tableData[ri][2] = project.getScComment() != null ? project.getScComment() : "";
        ri++;
        tableData[ri][0] = "National Highway System";
        tableData[ri][1] = project.getNationalHighwaySystem() != null ? project.getNationalHighwaySystem() : "";
        tableData[ri][2] = project.getNhsComment() != null ? project.getNhsComment() : "";

        return tableData;
    }

    public String[][] getWorkZoneConfiguration(Project project) {
        String[][] tableData = new String[8][3];
        int ri = 0;
        tableData[ri][0] = "Name";
        tableData[ri][1] = "Value";
        tableData[ri][2] = "Comment";
        ri++;
        tableData[ri][0] = "Length of Work Zone (mi)";
        tableData[ri][1] = String.format("%.1f", (project.getWzLength()));
        tableData[ri][2] = project.getWzlComment() != null ? project.getWzlComment() : "";
        ri++;
        tableData[ri][0] = "Type of Work Zone (MUTCD)";
        tableData[ri][1] = project.getWzType() != null ? project.getWzType() : "";
        tableData[ri][2] = project.getWztComment() != null ? project.getWztComment() : "";
        ri++;
        tableData[ri][0] = "Work Zone Speed Limit";
        tableData[ri][1] = String.valueOf(project.getWzSpeedLimit());
        tableData[ri][2] = project.getWzslComment() != null ? project.getWzslComment() : "";
        ri++;
        tableData[ri][0] = "Number of Lanes to be Closed";
        tableData[ri][1] = String.valueOf(project.getNumLanesClosed());
        tableData[ri][2] = project.getNlcComment() != null ? project.getNlcComment() : "";
        ri++;
        tableData[ri][0] = "Work Zone Lane Width";
        tableData[ri][1] = String.format("%.1f", project.getLaneWidthWZ());
        tableData[ri][2] = project.getWzlwComment() != null ? project.getWzlwComment() : "";
        ri++;
        tableData[ri][0] = "Shoulder Closure";
        tableData[ri][1] = project.getShoulderClosure() != null ? project.getShoulderClosure() : "";
        tableData[ri][2] = project.getShcComment() != null ? project.getShcComment() : "";
        ri++;
        tableData[ri][0] = "Federal-Aid Project";
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
                tableData[rowIdx][1] = sh.getName();
                tableData[rowIdx][2] = sh.getEmail();
                tableData[rowIdx][3] = sh.getPhone();
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
                tableData[rowIdx][1] = sh.getName();
                tableData[rowIdx][2] = sh.getEmail();
                tableData[rowIdx][3] = sh.getPhone();
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
        QuestionYN currQ = project.getQGen().qITSResourcesList.get(ri-1);
        tableData[ri][0] = String.valueOf(ri);
        tableData[ri][1] = "Weather Monitoring Stations";
        tableData[ri][2] = currQ.getAnswerString();
        tableData[ri][3] = currQ.getComment() != null ? currQ.getComment() : "";
        ri++;
        currQ = project.getQGen().qITSResourcesList.get(ri-1);
        tableData[ri][0] = String.valueOf(ri);
        tableData[ri][1] = "TMC Monitoring Roadway";
        tableData[ri][2] = currQ.getAnswerString();
        tableData[ri][3] = currQ.getComment() != null ? currQ.getComment() : "";
        ri++;
        currQ = project.getQGen().qITSResourcesList.get(ri-1);
        tableData[ri][0] = String.valueOf(ri);
        tableData[ri][1] = "Website/Traveler Information System";
        tableData[ri][2] = currQ.getAnswerString();
        tableData[ri][3] = currQ.getComment() != null ? currQ.getComment() : "";
        ri++;
        currQ = project.getQGen().qITSResourcesList.get(ri-1);
        tableData[ri][0] = String.valueOf(ri);
        tableData[ri][1] = "ITS On-Call Contract(s) Available";
        tableData[ri][2] = currQ.getAnswerString();
        tableData[ri][3] = currQ.getComment() != null ? currQ.getComment() : "";
        ri++;
        currQ = project.getQGen().qITSResourcesList.get(ri-1);
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
            final String reportTitle = getFactSheetName(1);
            xmlGenerator.generateTitle(reportTitle, Pos.CENTER);
            xmlGenerator.generateTable(
                    getProjectInformationTable(controller.getProject()),
                    "projectInformation",
                    false,
                    false,
                    false,
                    "Project Information:",
                    "",
                    50,
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
                    new float[] {15, 85},
                    null
            );
            String imgResourcePath = PDFReportHelper.getResFolderLocation();
            xmlGenerator.generateImage(imgResourcePath + PDFReportHelper.FILE_FS1_WZIMAGE, //src/Toolbox/XML/output/hourly-system-delay.png
                    7.8, 3.25,
                    "Work Zone Image", "");
            xmlGenerator.generateTable(
                    getFacilityAndBaseConditions(controller.getProject()),
                    "facilityAndBaseConditions",
                    false,
                    true,
                    false,
                    "Facility and Base Conditions",
                    "",
                    100,
                    new float[] {23, 12, 65},
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
                    new float[] {25, 10, 65},
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
                    new float[] {20, 70, 10},
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
                    new float[] {50,50},
                    new String[] {"center", "left"}
            );
//            if (this.includeScenarioCompTable) {
//                float rowNameColWidth = 25.0f;
//                float[] customColWidths = new float[seed.getValueInt(CEConst.IDS_NUM_SCEN) + 2]; // +2, 1 for row names, 1 for base scen
//                customColWidths[0] = rowNameColWidth;
//                for (int i = 1; i < customColWidths.length; i++) {
//                    customColWidths[i] = (100.0f - rowNameColWidth) / (customColWidths.length - 1);
//                }
//                xmlGenerator.generateTable(
//                        getScenarioCompTable(seed), // table data (String[][])
//                        "scenarioCompTable", // XML id
//                        false, // headerColumn
//                        true, // headerRow
//                        true, // generateCellId
//                        "Performance Measures Summary - All Scenarios", // Header/title
//                        "", // Footer
//                        customColWidths // Column widths
//                );
//            }
//            String imgResourcePath = PDFReportHelper.getResFolderLocation();
//            if (this.includeSystemDelayChart) {
//                xmlGenerator.generateImage(imgResourcePath + PDFReportHelper.FILE_CHART_HSDTT, //src/Toolbox/XML/output/hourly-system-delay.png
//                        systemDelayChartWidth, 3.25,
//                        "Actual Travel Time & System Delay:", "");
//            }
//
//            if ((this.includeMap || this.includeDemand) && (this.includeSegmentGeometryTable || this.includeScenarioDetails)) {
//                xmlGenerator.addPageBreak();
//                pageBroken = true;
//            }
//            if (this.includeSegmentGeometryTable) {
//                if (!pageBroken) {
//                    xmlGenerator.addPageBreak();
//                    pageBroken = true;
//                }
//                float[] customColWidths = new float[7];
//                customColWidths[0] = 3.5f;
//                customColWidths[1] = 10.0f;
//                customColWidths[2] = 10.0f;
//                customColWidths[3] = 10.0f;
//                customColWidths[4] = 10.0f;
//                customColWidths[5] = 10.0f;
//                customColWidths[6] = 46.5f;
//                xmlGenerator.generateTable(getSegmentGeometryDetails(seed, scen), //getHCMSegmentationDetails(seed, scen) (use segmentationDetails as id below)
//                        "segmentationGeometry", false,
//                        true, true, "Detailed Segment Geometry Inputs:", "",
//                        customColWidths);
//            }
//
//            xmlGenerator.generateCommentBlock();
//
//            pageBroken = false;
//            if (this.includeContourSpd) {
//                if (!pageBroken) {
//                    xmlGenerator.addPageBreak();
//                    pageBroken = true;
//                }
//                if (reportType == PDFReportDialog.SCENARIO_COMPARISON_ANALYSIS) {
//                    for (int scenIdx = 0; scenIdx < seed.getValueInt(CEConst.IDS_NUM_SCEN) + 1; scenIdx++) {
//                        xmlGenerator.generateContour(getContourTable(seed, CEConst.IDS_SPACE_MEAN_SPEED, scenIdx, -1), "speedContour", true, true, true,
//                                "Speed Contour: " + (scenIdx == 0 ? "Base" : seed.getRLScenarioInfo().get(scenIdx).getDemandPatternName()), "");
//                        if (scenIdx < seed.getValueInt(CEConst.IDS_NUM_SCEN)) {
//                            xmlGenerator.addPageBreak();
//                        }
//                    }
//                } else {
//                    xmlGenerator.generateContour(getContourTable(seed, CEConst.IDS_SPACE_MEAN_SPEED, scen, -1), "speedContour", true, true, true, "Speed Contour:", "");
//                }
//            }
//
            xmlGenerator.generate();
//
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
            final String reportTitle = getFactSheetName(2);
            xmlGenerator.generateTitle(reportTitle, Pos.CENTER);
            xmlGenerator.generateTable(
                    getProjectInformationTable(controller.getProject()),
                    "projectInformation",
                    false,
                    false,
                    false,
                    "Project Information:",
                    "",
                    50,
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
                    new float[] {15, 85},
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
                    new float[] {5, 55, 25, 15},
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
                    new float[] {5, 55, 25, 15},
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
                    new float[] {5, 30, 10, 55},
                    new String[] {"center", "left", "center", "left"}
            );

            xmlGenerator.generate();

        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
    }

    public void createFactSheet3(MainController controller) {
        String xmlFilePath = PDFReportHelper.getResFolderLocation() + "fact-sheet-pdf-resources.xml";
        try {

        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
    }

    public void createFactSheet4(MainController controller) {
        String xmlFilePath = PDFReportHelper.getResFolderLocation() + "fact-sheet-pdf-resources.xml";
        try {

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
                    "Fact Sheet #5" // TODO always update this index
            );

            xmlGenerator.generate();
        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
    }

    public void createFactSheet6(MainController controller) {
        String xmlFilePath = PDFReportHelper.getResFolderLocation() + "fact-sheet-pdf-resources.xml";
        try {

        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
    }

    public void createFactSheet7(MainController controller) {
        String xmlFilePath = PDFReportHelper.getResFolderLocation() + "fact-sheet-pdf-resources.xml";
        try {

        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
    }

    public void createFactSheet8(MainController controller) {
        String xmlFilePath = PDFReportHelper.getResFolderLocation() + "fact-sheet-pdf-resources.xml";
        try {

        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
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
