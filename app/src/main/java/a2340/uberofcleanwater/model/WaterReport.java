package a2340.uberofcleanwater.model;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Object designed to hold information for a user created water report.
 * @author Seth Triplett
 * @version 1.0
 * @since 2017-02-23
 */

public class WaterReport {
    private static int idNum = 0;

    private Date createdDate;
    private int reportNum;
    private String author;
    private Double longitude;
    private Double latitude;
    private WaterType type;
    private WaterCondition condition;

    public static List<String> legalTypes = Arrays.asList("Bottled", "Well", "Stream", "Lake", "Spring", "Other");
    public static List<String> legalConditions = Arrays.asList("Waste", "Treatable/Clear", "Treatable/Muddy", "Potable");


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
        createdDate = new Date();
        reportNum = idNum;
        idNum++;
        this.author = author;
        this.longitude = longitude;
        this.latitude = latitude;
        this.type = type;
        this.condition = condition;
    }

    /**
     * Getter for the createdDate.
     * @return - the createdDate
     */
    public Date getDate(){
        return createdDate;
    }

    /**
     * Getter for the report number.
     * @return - the report number
     */
    public int getReportNum() {
        return reportNum;
    }

    /**
     * Getter for the report's author.
     * @return - the name of the user who submitted the report
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Getter for the longitude.
     * @return - longitude of the report.
     */
    public Double getLongitude() {
        return longitude;
    }

    /**
     * Getter for the latitude.
     * @return - latitude of the report
     */
    public Double getLatitude() {
        return latitude;
    }

    /**
     * Getter for the WaterType of the report.
     * @return - the water type as specified in the report
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

    public static WaterCondition stringToWC(String waterCondition) {
        for (WaterCondition wc: WaterCondition.values()) {
            if (wc.toString().equals(waterCondition)) {
                return wc;
            }
        }
        return null;
    }

    public static WaterType stringToWT(String waterType) {
        for (WaterType wt: WaterType.values()) {
            if (wt.toString().equals(waterType)) {
                return wt;
            }
        }
        return null;
    }
}
