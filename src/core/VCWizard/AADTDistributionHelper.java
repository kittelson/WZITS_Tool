package core.VCWizard;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Optional;
//import javax.swing.JOptionPane;

/**
 *
 * @author jtrask
 */
public class AADTDistributionHelper {

    public static final String TYPE_DEFAULT_DISTRIBUTION = "TYPE_DEFAULT_DISTRIBUTION";
    public static final String TYPE_FACILITY_SPECIFIC = "TYPE_FACILITY_SPECIFIC";
    public static final String TYPE_USER_SPECIFIED = "TYPE_USER_SPECIFIED";

    public static final int TYPE_DEFAULT_UNIMODAL = 0;
    public static final int TYPE_DEFAULT_BIMODAL_AM = 1;
    public static final int TYPE_DEFAULT_BIMODAL_PM = 2;
    public static final int TYPE_DEFAULT_NATIONAL_WEEKDAY = 3;
    public static final int TYPE_DEFAULT_NATIONAL_WEEKEND = 4;

    public static final int TYPE_DEFAULT_SUB_MIN = 0;
    public static final int TYPE_DEFAULT_SUB_25 = 1;
    public static final int TYPE_DEFAULT_SUB_AVG = 2;
    public static final int TYPE_DEFAULT_SUB_75 = 3;
    public static final int TYPE_DEFAULT_SUB_MAX = 4;
    public static final int TYPE_DEFAULT_SUB_MEDIAN = 5;

    public static String[] sub_types = new String[6];

    public static String[] getSub_types() {
        return sub_types;
    }

    public static void setSub_types() {
        sub_types[0] = "MIN";
        sub_types[1] = "25";
        sub_types[2] = "AVG";
        sub_types[3] = "75";
        sub_types[4] = "MAX";
        sub_types[5] = "MEDIAN";
    }

    /**
     * Extracts the default distribution profiles corresponding to a one of the
     * three main types: Unimodal, Bimodal-AM, Bimodal-PM. Returns a 2D array
     * with six sub-profiles: Minimum, 25th percentile, average, 75th
     * percentile, maximum, and median (in that order).
     *
     * @param mainType Default Distribution Main Type:
     * TYPE_DEFUALT_UNIMODAL,TYPE_DEFUALT_BIMODAL_AM,TYPE_DEFUALT_BIMODAL_PM
     * @return 2D array of the sub-profiles
     */
    public static float[][] extractDefaultProfiles(int mainType) {
        float[][] profile = new float[6][24];

        int numSubType = 6;
        String openFileName;
        switch (mainType) {
            case TYPE_DEFAULT_BIMODAL_AM:
                openFileName = "/core/VCWizard/database/bimodal_am.db";
                break;
            case TYPE_DEFAULT_BIMODAL_PM:
                openFileName = "/core/VCWizard/database/bimodal_pm.db";
                break;
            default:
            case TYPE_DEFAULT_UNIMODAL:
                openFileName = "/core/VCWizard/database/unimodal.db";
                break;
            case TYPE_DEFAULT_NATIONAL_WEEKDAY:
                numSubType = 2;
                openFileName = "/core/VCWizard/database/national_weekday.db";
                break;
            case TYPE_DEFAULT_NATIONAL_WEEKEND:
                numSubType = 2;
                openFileName = "/core/VCWizard/database/national_weekend.db";
                break;

        }

        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(AADTDistributionHelper.class.getResourceAsStream(openFileName)));
            br.readLine();
            br.readLine();
            String[] line;
            for (int subType = 0; subType < numSubType; subType++) {
                line = br.readLine().split(",");
                profile[subType][0] = Float.parseFloat(line[1]);
                profile[subType][1] = Float.parseFloat(line[2]);
                profile[subType][2] = Float.parseFloat(line[3]);
                profile[subType][3] = Float.parseFloat(line[4]);
                profile[subType][4] = Float.parseFloat(line[5]);
                profile[subType][5] = Float.parseFloat(line[6]);
                profile[subType][6] = Float.parseFloat(line[7]);
                profile[subType][7] = Float.parseFloat(line[8]);
                profile[subType][8] = Float.parseFloat(line[9]);
                profile[subType][9] = Float.parseFloat(line[10]);
                profile[subType][10] = Float.parseFloat(line[11]);
                profile[subType][11] = Float.parseFloat(line[12]);
                profile[subType][12] = Float.parseFloat(line[13]);
                profile[subType][13] = Float.parseFloat(line[14]);
                profile[subType][14] = Float.parseFloat(line[15]);
                profile[subType][15] = Float.parseFloat(line[16]);
                profile[subType][16] = Float.parseFloat(line[17]);
                profile[subType][17] = Float.parseFloat(line[18]);
                profile[subType][18] = Float.parseFloat(line[19]);
                profile[subType][19] = Float.parseFloat(line[20]);
                profile[subType][20] = Float.parseFloat(line[21]);
                profile[subType][21] = Float.parseFloat(line[22]);
                profile[subType][22] = Float.parseFloat(line[23]);
                profile[subType][23] = Float.parseFloat(line[24]);
            }
        } catch (IOException e) {
//            JOptionPane.showMessageDialog(null, "<HTML><CENTER>Error reading AADT distribution from file.  The database file could<br>not be found or is corrupted.", "ERROR", JOptionPane.ERROR_MESSAGE);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Reading Profile Database");
            alert.setContentText("An error occurred reading the default AADT profile database files, the file could not be found or is corrupted.");
            alert.getButtonTypes().addAll(ButtonType.OK);
            Optional<ButtonType> result = alert.showAndWait();
//            if (result.isPresent() && result.get() == ButtonType.OK) {
//                formatSystem();
//            }
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                }
            }
        }

        return profile;
    }

    /**
     * Extract a facility specific profile from the database. Use the static
     * TYPE_FS_LOCATION variables to indicate the facility to extract.
     *
     * @param facilityID Integer facility identifier.
     * @return Float array (size 24) of profile values
     */
    public static float[] extractFacilitySpecificProfile(int facilityID) {
        // Creating profile array
        float[] profile = new float[24];

        String openFileName = "/Toolbox/WZ/database/facility_specific.db";;
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(AADTDistributionHelper.class.getResourceAsStream(openFileName)));
            br.readLine();
            String currline = br.readLine();
            int lineCounter = 0;
            while (currline != null && lineCounter < facilityID) {
                currline = br.readLine();
                lineCounter++;
            }
            if (currline == null) {
//                // Search custom facility file
//                br.close();
//                br = new BufferedReader(new FileReader(ConfigIO.getCfgFolderLocation() + "userSpecifiedFS_names.csv"));
//                br.readLine();  // First line is a header
//                currline = br.readLine();
//                while (currline != null && lineCounter < facilityID) {
//                    currline = br.readLine();
//                    lineCounter++;
//                }
//                if (currline == null) {
//                    JOptionPane.showMessageDialog(null, "The facility was not found in the database", "Error: Facility Not Found", JOptionPane.ERROR_MESSAGE);
//                } else {
//                    String[] line = currline.split(",");
//                    profile[0] = Float.parseFloat(line[5]);
//                    profile[1] = Float.parseFloat(line[6]);
//                    profile[2] = Float.parseFloat(line[7]);
//                    profile[3] = Float.parseFloat(line[8]);
//                    profile[4] = Float.parseFloat(line[9]);
//                    profile[5] = Float.parseFloat(line[10]);
//                    profile[6] = Float.parseFloat(line[11]);
//                    profile[7] = Float.parseFloat(line[12]);
//                    profile[8] = Float.parseFloat(line[13]);
//                    profile[9] = Float.parseFloat(line[14]);
//                    profile[10] = Float.parseFloat(line[15]);
//                    profile[11] = Float.parseFloat(line[16]);
//                    profile[12] = Float.parseFloat(line[17]);
//                    profile[13] = Float.parseFloat(line[18]);
//                    profile[14] = Float.parseFloat(line[19]);
//                    profile[15] = Float.parseFloat(line[20]);
//                    profile[16] = Float.parseFloat(line[21]);
//                    profile[17] = Float.parseFloat(line[22]);
//                    profile[18] = Float.parseFloat(line[23]);
//                    profile[19] = Float.parseFloat(line[24]);
//                    profile[20] = Float.parseFloat(line[25]);
//                    profile[21] = Float.parseFloat(line[26]);
//                    profile[22] = Float.parseFloat(line[27]);
//                    profile[23] = Float.parseFloat(line[28]);
//                }
            } else {
                String[] line = currline.split(",");
                profile[0] = Float.parseFloat(line[1]);
                profile[1] = Float.parseFloat(line[2]);
                profile[2] = Float.parseFloat(line[3]);
                profile[3] = Float.parseFloat(line[4]);
                profile[4] = Float.parseFloat(line[5]);
                profile[5] = Float.parseFloat(line[6]);
                profile[6] = Float.parseFloat(line[7]);
                profile[7] = Float.parseFloat(line[8]);
                profile[8] = Float.parseFloat(line[9]);
                profile[9] = Float.parseFloat(line[10]);
                profile[10] = Float.parseFloat(line[11]);
                profile[11] = Float.parseFloat(line[12]);
                profile[12] = Float.parseFloat(line[13]);
                profile[13] = Float.parseFloat(line[14]);
                profile[14] = Float.parseFloat(line[15]);
                profile[15] = Float.parseFloat(line[16]);
                profile[16] = Float.parseFloat(line[17]);
                profile[17] = Float.parseFloat(line[18]);
                profile[18] = Float.parseFloat(line[19]);
                profile[19] = Float.parseFloat(line[20]);
                profile[20] = Float.parseFloat(line[21]);
                profile[21] = Float.parseFloat(line[22]);
                profile[22] = Float.parseFloat(line[23]);
                profile[23] = Float.parseFloat(line[24]);
            }

        } catch (IOException e) {
//            JOptionPane.showMessageDialog(null, "<HTML><CENTER>Error reading AADT distribution from file.  The database file could<br>"
//                    + "not be found or is corrupted.", "Error: Facility Specific Database File", JOptionPane.ERROR_MESSAGE);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Reading Profile Database");
            alert.setContentText("An error occurred reading the facility specific AADT profile database files, the file could not be found or is corrupted.");
            alert.getButtonTypes().addAll(ButtonType.OK);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                }
            }
        }

        return profile;
    }

    public static String[] getFacilityNames() {
        String[] names = new String[52];
        String[] codes = new String[52];

        String openFileName = "/Toolbox/WZ/database/facility_specific.db";
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(AADTDistributionHelper.class.getResourceAsStream(openFileName)));
            br.readLine();
            for (int id = 0; id < codes.length; id++) {
                codes[id] = br.readLine().split(",")[0];
            }
            br.close();

        } catch (IOException e) {
//            JOptionPane.showMessageDialog(null, "<HTML><CENTER>Error reading AADT distributions from file.  The database file could<br>"
//                    + "not be found or is corrupted.", "Error: Facility Specific Database File", JOptionPane.ERROR_MESSAGE);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Reading Profile Database");
            alert.setContentText("An error occurred reading the facility specific AADT profile database files, the file could not be found or is corrupted.");
            alert.getButtonTypes().addAll(ButtonType.OK);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                }
            }
        }

        String facilityFileName = "/Toolbox/WZ/database/facility_names.db";
        try {
            br = new BufferedReader(new InputStreamReader(AADTDistributionHelper.class.getResourceAsStream(facilityFileName)));
            br.readLine();
            int id = 0;
            String line = br.readLine();
            while (line != null) {
                String[] tokens = line.split(",");
                String idCode = tokens[0];
                while (id < 52 && idCode.equals(codes[id].split("_")[0])) {
                    names[id] = tokens[4] + ", " + tokens[2] + " " + codes[id].split("_")[1] + ", " + tokens[3];
                    id++;
                }
                line = br.readLine();
            }
        } catch (IOException e) {
//            JOptionPane.showMessageDialog(null, "<HTML><CENTER>Error reading AADT distribution from file.  The database file could<br>"
//                    + "not be found or is corrupted.", "Error: Facility Specific Database File", JOptionPane.ERROR_MESSAGE);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Reading Profile Database");
            alert.setContentText("An error occurred reading the facility specific AADT profile database files, the file could not be found or is corrupted.");
            alert.getButtonTypes().addAll(ButtonType.OK);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                }
            }
        }

        return names;

//        File userSpecifiedList = new File(ConfigIO.getCfgFolderLocation() + "userSpecifiedFS_names.csv");
//
//        ArrayList<Facility> auxFacilities = new ArrayList();
//        if (userSpecifiedList.exists()) {
//            auxFacilities = getAuxFacilities(userSpecifiedList);
//        }
//        if (auxFacilities.isEmpty()) {
//            return names;
//        } else {
//            // Combine names and auxNames
//            String[] auxNames = new String[auxFacilities.size()];
//            for (int i = 0; i < auxNames.length; i++) {
//                auxNames[i] = auxFacilities.get(i).county + "," + auxFacilities.get(i).routeNum + " " + auxFacilities.get(i).direction + "," + auxFacilities.get(i).description;
//            }
//            String[] allNames = new String[names.length + auxNames.length];
//            System.arraycopy(names, 0, allNames, 0, names.length);
//            System.arraycopy(auxNames, 0, allNames, names.length, auxNames.length);
//            return allNames;
//        }
    }

//    public static ArrayList<Facility> getAuxFacilities(File dbFile) {
//
//        BufferedReader br = null;
//        ArrayList<Facility> facilities = new ArrayList<>();
//        try {
//            // Read any additional facilities
//            br = new BufferedReader(new FileReader(dbFile));
//            br.readLine();  // First line is title line
//            String currline = br.readLine();
//            String[] tokens;
//            float[] tempArr = new float[24];
//            while (currline != null) { // && !br.readLine().trim().equalsIgnoreCase("")) {
//                tokens = currline.split(",");
//                facilities.add(new Facility(tokens[0], tokens[2], tokens[1], tokens[3], tokens[4]));
//                tempArr[0] = Float.valueOf(tokens[5]);
//                tempArr[1] = Float.valueOf(tokens[6]);
//                tempArr[2] = Float.valueOf(tokens[7]);
//                tempArr[3] = Float.valueOf(tokens[8]);
//                tempArr[4] = Float.valueOf(tokens[9]);
//                tempArr[5] = Float.valueOf(tokens[10]);
//                tempArr[6] = Float.valueOf(tokens[11]);
//                tempArr[7] = Float.valueOf(tokens[12]);
//                tempArr[8] = Float.valueOf(tokens[13]);
//                tempArr[9] = Float.valueOf(tokens[14]);
//                tempArr[10] = Float.valueOf(tokens[15]);
//                tempArr[11] = Float.valueOf(tokens[16]);
//                tempArr[12] = Float.valueOf(tokens[17]);
//                tempArr[13] = Float.valueOf(tokens[18]);
//                tempArr[14] = Float.valueOf(tokens[19]);
//                tempArr[15] = Float.valueOf(tokens[20]);
//                tempArr[16] = Float.valueOf(tokens[21]);
//                tempArr[17] = Float.valueOf(tokens[22]);
//                tempArr[18] = Float.valueOf(tokens[23]);
//                tempArr[19] = Float.valueOf(tokens[24]);
//                tempArr[20] = Float.valueOf(tokens[25]);
//                tempArr[21] = Float.valueOf(tokens[26]);
//                tempArr[22] = Float.valueOf(tokens[27]);
//                tempArr[23] = Float.valueOf(tokens[28]);
//                arrayFill(facilities.get(facilities.size() - 1).profile, tempArr);
//                currline = br.readLine();
//            }
//        } catch (IOException e) {
//            JOptionPane.showMessageDialog(null, "<HTML><CENTER>Error reading custom AADT distributions from file.  The database file could<br>"
//                    + "not be found or is corrupted.", "Error: Custom Facility Specific Database File", JOptionPane.ERROR_MESSAGE);
//        }
//
//        return facilities;
//    }

    /**
     * Add a new Facility Specific Profile to the database.
     *
     * @param routeNum
     * @param direction
     * @param description
     * @param county
     * @param profile
     */
    public static void addFacilitySpecificProfile(String routeNum, String direction, String description, String county, float[] profile) {

//        // Reading custom facility counter index
//        int customCounter = 1;
//        BufferedReader br = null;
//        String currLine;
//        String prevLine = " ";
//        try {
//            br = new BufferedReader(new FileReader(ConfigIO.getCfgFolderLocation() + "userSpecifiedFS_names.csv"));
//            currLine = br.readLine();
//
//            while (currLine != null) {
//                prevLine = currLine;
//                currLine = br.readLine();
//            }
//            if (prevLine.charAt(0) == 'u') {
//                customCounter = Integer.valueOf(String.valueOf(prevLine.charAt(1))) + 1;
//            }
//        } catch (IOException e) {
//            MainWindow.printLog("File read 1 failed");
//        } finally {
//            if (br != null) {
//                try {
//                    br.close();
//                } catch (IOException e) {
//                }
//            }
//        }
//
//        // Adding name to facility names database
//        BufferedWriter bw = null;
//        boolean nameAdded = false;
//        try {
//            bw = new BufferedWriter(new FileWriter(ConfigIO.getCfgFolderLocation() + "userSpecifiedFS_names.csv", true));
//            bw.write("\nu" + String.valueOf(customCounter) + "," + direction + ","
//                    + routeNum + ", " + description + ","
//                    + county + ","
//                    + String.valueOf(profile[0]) + ","
//                    + String.valueOf(profile[1]) + ","
//                    + String.valueOf(profile[2]) + ","
//                    + String.valueOf(profile[3]) + ","
//                    + String.valueOf(profile[4]) + ","
//                    + String.valueOf(profile[5]) + ","
//                    + String.valueOf(profile[6]) + ","
//                    + String.valueOf(profile[7]) + ","
//                    + String.valueOf(profile[8]) + ","
//                    + String.valueOf(profile[9]) + ","
//                    + String.valueOf(profile[10]) + ","
//                    + String.valueOf(profile[11]) + ","
//                    + String.valueOf(profile[12]) + ","
//                    + String.valueOf(profile[13]) + ","
//                    + String.valueOf(profile[14]) + ","
//                    + String.valueOf(profile[15]) + ","
//                    + String.valueOf(profile[16]) + ","
//                    + String.valueOf(profile[17]) + ","
//                    + String.valueOf(profile[18]) + ","
//                    + String.valueOf(profile[19]) + ","
//                    + String.valueOf(profile[20]) + ","
//                    + String.valueOf(profile[21]) + ","
//                    + String.valueOf(profile[22]) + ","
//                    + String.valueOf(profile[23]));
//
//        } catch (IOException e) {
//            JOptionPane.showMessageDialog(null, "<HTML><CENTER>Failed to add new User Specified facility to database.<br>"
//                            + "The database file could not be found.",
//                    "Error Adding New Facility Profile",
//                    JOptionPane.ERROR_MESSAGE);
//        } finally {
//            if (bw != null) {
//                try {
//                    bw.close();
//                } catch (IOException e) {
//                }
//            }
//        }

    }

    /**
     * Returns the demand values for the mainline segment for the period from
     * 12am to 12am based on the specified bidirectional AADT.
     *
     * @param profile
     * @param aadtVal
     * @return Array of length 96 that returns the 15 minute demand values for
     * use with the mainline segment input.
     */
    public static int[] get24HourMainlineDemand(float[] profile, float aadtVal) {
        float adjAADT = aadtVal;
        int[] demand = new int[96];
        float[] currHourBinPct = new float[4];
        for (int hour = 0; hour < 24; hour++) {
            float diff = (profile[(hour + 1) % 24] - profile[hour]);
            currHourBinPct[0] = (profile[hour] + (0 * diff) / 4.0f);// / 4.0f;
            currHourBinPct[1] = (profile[hour] + (1 * diff) / 4.0f);// / 4.0f;
            currHourBinPct[2] = (profile[hour] + (2 * diff) / 4.0f);// / 4.0f;
            currHourBinPct[3] = (profile[hour] + (3 * diff) / 4.0f);// / 4.0f;
            demand[hour * 4] = Math.round(currHourBinPct[0] * adjAADT);
            demand[hour * 4 + 1] = Math.round(currHourBinPct[1]* adjAADT);
            demand[hour * 4 + 2] = Math.round(currHourBinPct[2] * adjAADT);
            demand[hour * 4 + 3] = Math.round(currHourBinPct[3] * adjAADT);
        }
        return demand;
    }

    /**
     * Returns just the 15 minute percentages for each bin from 12am to 12am.
     * Can be used with a mainline or a ramp AADT to calculate distributed
     * demands.
     *
     * @param profileArray
     * @return
     */
    public static float[] getDemandProfileIn15MinBins(float[] profileArray) {
        float[] demandProfile = new float[96];
        float[] currHourBinPct = new float[4];
        for (int hour = 0; hour < 24; hour++) {
            float diff = (profileArray[(hour + 1) % 24] - profileArray[hour]);
            currHourBinPct[0] = (profileArray[hour] + (0 * diff) / 4.0f) / 4.0f;
            currHourBinPct[1] = (profileArray[hour] + (1 * diff) / 4.0f) / 4.0f;
            currHourBinPct[2] = (profileArray[hour] + (2 * diff) / 4.0f) / 4.0f;
            currHourBinPct[3] = (profileArray[hour] + (3 * diff) / 4.0f) / 4.0f;
            demandProfile[hour * 4] = currHourBinPct[0];
            demandProfile[hour * 4 + 1] = currHourBinPct[1];
            demandProfile[hour * 4 + 2] = currHourBinPct[2];
            demandProfile[hour * 4 + 3] = currHourBinPct[3];
        }
        return demandProfile;
    }

    /**
     * Returns just the 15 minute percentages for each bin from 12am to 12am.
     * Can be used with a mainline or a ramp AADT to calculate distributed
     * demands.
     *
     * @param profileArray
     * @return
     */
    public static float[] getNormalizedDemandProfileIn15MinBins(float[] profileArray) {
        float[] demandProfile = new float[96];
        float[] currHourBinPct = new float[4];
        for (int hour = 0; hour < 24; hour++) {
            float diff = (profileArray[(hour + 1) % 24] - profileArray[hour]);
            currHourBinPct[0] = (profileArray[hour] + (0 * diff) / 4.0f) / 4.0f;
            currHourBinPct[1] = (profileArray[hour] + (1 * diff) / 4.0f) / 4.0f;
            currHourBinPct[2] = (profileArray[hour] + (2 * diff) / 4.0f) / 4.0f;
            currHourBinPct[3] = (profileArray[hour] + (3 * diff) / 4.0f) / 4.0f;
            demandProfile[hour * 4] = currHourBinPct[0];
            demandProfile[hour * 4 + 1] = currHourBinPct[1];
            demandProfile[hour * 4 + 2] = currHourBinPct[2];
            demandProfile[hour * 4 + 3] = currHourBinPct[3];
        }
        normalizeInPlace(demandProfile);
        return demandProfile;
    }

    public static float[] getDefaultProfileInHourlyPct(int mainType, int subType) {
        float[][] mainProf = extractDefaultProfiles(mainType);
        return mainProf[subType];
    }

    public static float[] getFacilityProfileInHourlyPct(int facilityID) {
        return extractFacilitySpecificProfile(facilityID);
    }

    public static float[] normalize(float[] arr) {
        float[] normArr = new float[arr.length];
        float currMax = Float.MIN_VALUE;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > currMax) {
                currMax = arr[i];
            }
        }
        for (int i = 0; i < arr.length; i++) {
            normArr[i] = arr[i] / currMax;
        }
        return normArr;
    }

    public static void normalizeInPlace(float[] arr) {
        float currMax = Float.MIN_VALUE;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > currMax) {
                currMax = arr[i];
            }
        }
        for (int i = 0; i < arr.length; i++) {
            arr[i] = arr[i] / currMax;
        }
    }

    /**
     * Fills arr1 with arr2
     *
     * @param arr1
     * @param arr2
     */
    private static void arrayFill(float[] arr1, float[] arr2) {
        for (int index = 0; index < arr1.length; index++) {
            arr1[index] = arr2[index];
        }
    }

    public static String getDefaultProfileCombinedString(int defaultProfileType1, int defaultProfileType2) {
        String nameStr = "";
        switch (defaultProfileType1) {
            case AADTDistributionHelper.TYPE_DEFAULT_BIMODAL_AM:
                nameStr = nameStr + "Bimodal AM";
                break;
            case AADTDistributionHelper.TYPE_DEFAULT_BIMODAL_PM:
                nameStr = nameStr + "Bimodal PM";
                break;
            case AADTDistributionHelper.TYPE_DEFAULT_UNIMODAL:
                nameStr = nameStr + "Unimodal";
                break;
        }

        switch (defaultProfileType2) {
            case AADTDistributionHelper.TYPE_DEFAULT_SUB_AVG:
                nameStr = nameStr + " (Average)";
                break;
            case AADTDistributionHelper.TYPE_DEFAULT_SUB_MIN:
                nameStr = nameStr + " (Minimum)";
                break;
            case AADTDistributionHelper.TYPE_DEFAULT_SUB_25:
                nameStr = nameStr + " (25th Percentile)";
                break;
            case AADTDistributionHelper.TYPE_DEFAULT_SUB_75:
                nameStr = nameStr + " (75th Percentile)";
                break;
            case AADTDistributionHelper.TYPE_DEFAULT_SUB_MAX:
                nameStr = nameStr + " (Maximum)";
                break;
            case AADTDistributionHelper.TYPE_DEFAULT_SUB_MEDIAN:
                nameStr = nameStr + " (Median)";
                break;
        }

        return nameStr;
    }

    public static String getDefaultProfilePrimaryString(int defaultProfileType1) {
        String nameStr = "";
        switch (defaultProfileType1) {
            case AADTDistributionHelper.TYPE_DEFAULT_BIMODAL_AM:
                nameStr = nameStr + "Bimodal AM";
                break;
            case AADTDistributionHelper.TYPE_DEFAULT_BIMODAL_PM:
                nameStr = nameStr + "Bimodal PM";
                break;
            case AADTDistributionHelper.TYPE_DEFAULT_UNIMODAL:
                nameStr = nameStr + "Unimodal";
                break;
        }

        return nameStr;
    }

    public static String getDefaultProfileSecondaryString(int defaultProfileType2) {
        String nameStr = "";
        switch (defaultProfileType2) {
            case AADTDistributionHelper.TYPE_DEFAULT_SUB_AVG:
                nameStr = nameStr + "Average";
                break;
            case AADTDistributionHelper.TYPE_DEFAULT_SUB_MIN:
                nameStr = nameStr + "Minimum";
                break;
            case AADTDistributionHelper.TYPE_DEFAULT_SUB_25:
                nameStr = nameStr + "25th Percentile";
                break;
            case AADTDistributionHelper.TYPE_DEFAULT_SUB_75:
                nameStr = nameStr + "75th Percentile";
                break;
            case AADTDistributionHelper.TYPE_DEFAULT_SUB_MAX:
                nameStr = nameStr + "Maximum";
                break;
            case AADTDistributionHelper.TYPE_DEFAULT_SUB_MEDIAN:
                nameStr = nameStr + "Median";
                break;
        }

        return nameStr;

    }

    public static String getFacilitySpecificNameStr(int facilityIndex) {
        return getFacilityNames()[facilityIndex];
    }
}
