package a2340.uberofcleanwater.model;

import java.util.Date;

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
    private int longitude;
    private int latitude;
    private WaterType type;
    private WaterCondition condition;

    public WaterReport(String author, int longitude, int latitude, WaterType type, WaterCondition condition) {
        createdDate = new Date();
        reportNum = idNum;
        idNum++;
        this.author = author;
        this.longitude = longitude;
        this.latitude = latitude;
        this.type = type;
        this.condition = condition;
    }

    public Date getDate(){
        return createdDate;
    }

    public int getReportNum() {
        return reportNum;
    }

    public String getAuthor() {
        return author;
    }

    public int getLongitude() {
        return longitude;
    }

    public int getLatitude() {
        return latitude;
    }

    public WaterType getType() {
        return type;
    }

    public WaterCondition getCondition() {
        return condition;
    }
}
