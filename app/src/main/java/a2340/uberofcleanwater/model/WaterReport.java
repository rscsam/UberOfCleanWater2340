package a2340.uberofcleanwater.model;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Object designed to hold information for a user created water report.
 * @author Seth Triplett
 * @author Ryan Anderson
 * @author Sam Costley
 * @version 1.0
 * @since 2017-02-23
 */

public class WaterReport extends AbstractReport{
    private static int idNum = 0;

    private WaterType type;
    private WaterCondition condition;

    public static List<String> legalTypes = Arrays.asList("Bottled", "Well", "Stream", "Lake", "Spring", "Other");
    public static List<String> legalConditions = Arrays.asList("Waste", "Treatable/Clear", "Treatable/Muddy", "Potable");
    public static List<String> latitudeHemispheres = Arrays.asList("North", "South");
    public static List<String> longitudeHemispheres = Arrays.asList("West", "East");

    /**
     * Constructor with necessary parameters
     * Does not automatically generate createdDate and reportNum
     * Used for pulling WaterReports out of the database
     * @param author - The name of the user who submitted the report
     * @param longitude - Longitude entered by the user
     * @param latitude - Latitude entered by the user
     * @param type - Type of the water (bottled, lake, etc...)
     * @param condition - Condition of the water (potable, waste, etc...)
     * @param date - The date the report was created
     * @param idNum - The ID number of the report
     */
    WaterReport(String author, double longitude, double latitude, WaterType type, WaterCondition condition, Date date, int idNum) {
        super(author, longitude, latitude, date, idNum);
        this.type = type;
        this.condition = condition;
    }

    /**
     * Constructor with necessary parameters.
     * createdDate and reportNum are generated automatically.
     * @param author - The name of the user who submitted the report
     * @param longitude - Longitude entered by the user
     * @param latitude - Latitude entered by the user
     * @param type - Type of the water (bottled, lake, etc...)
     * @param condition - Condition of the water (potable, waste, etc...)
     */
    public WaterReport(String author, double longitude, double latitude, WaterType type, WaterCondition condition) {
        super(author, longitude, latitude, new Date(), idNum);
        idNum++;
        this.type = type;
        this.condition = condition;
    }

    /**
     * Getter for the WaterType of the report.
     * @return - the water type as specified in the report.
     */
    public WaterType getType() {
        return type;
    }

    /**
     * Getter for the WaterCondition of the report.
     * @return - the water condition as specified in the report.
     */
    public WaterCondition getCondition() {
        return condition;
    }

    /**
     * Takes a String from the database and finds the corresponding WaterCondition value
     *
     * @param waterCondition the String representation
     * @return the enum representation
     */
    public static WaterCondition stringToWC(String waterCondition) {
        if (waterCondition.equals("Treatable/Clear"))
            waterCondition = "TreatableClear";
        else if (waterCondition.equals("Treatable/Muddy"))
            waterCondition = "TreatableMuddy";
        for (WaterCondition wc: WaterCondition.values()) {
            if (wc.toString().equals(waterCondition)) {
                return wc;
            }
        }
        return null;
    }

    /**
     * Takes a String from the database and finds the corresponding WaterType value
     *
     * @param waterType the String representation
     * @return the enum representation
     */
    public static WaterType stringToWT(String waterType) {
        for (WaterType wt: WaterType.values()) {
            if (wt.toString().equals(waterType)) {
                return wt;
            }
        }
        return null;
    }
}
