package a2340.uberofcleanwater.model;

import java.util.Date;

/**
 * Abstract parent class for WaterReport and PurityReport
 * @author Seth Triplett
 * @version 1.0
 * @since 2017-02-23
 */
public abstract class AbstractReport {
    final private Date createdDate;
    final private int reportNum;
    final private String author;
    final private Double longitude;
    final private Double latitude;

    /**
     * Constructor for assigning the contained variables in an implemented subclass.
     * @param reportNum - The ID number of the report
     * @param author - The name of the user who submitted the report
     * @param longitude - Longitude entered by the user
     * @param latitude - Latitude entered by the user
     * @param date - The date the report was created
     */
    AbstractReport(String author, Double longitude, Double latitude, Date date, int reportNum) {
        this.createdDate = date;
        this.reportNum = reportNum;
        this.author = author;
        this.longitude = longitude;
        this.latitude = latitude;
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
}
