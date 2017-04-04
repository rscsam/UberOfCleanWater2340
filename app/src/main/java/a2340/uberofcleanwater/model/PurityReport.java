package a2340.uberofcleanwater.model;

import java.util.Date;
import java.util.List;
import java.util.Arrays;

/**
 * Class representing a purity report submitted by a Worker.
 * @author Seth Triplett
 * @version 1.0
 * @since 2017-03-08
 */
public class PurityReport extends AbstractReport {
    private static final int idNum = 0;
    private final PurityCondition condition;
    private final int contaminantPPM;
    private final int virusPPM;

    public static final List<String> legalConditions = Arrays.asList("Safe", "Treatable", "Unsafe");
    public static final List<String> latitudeHemispheres = Arrays.asList("North", "South");
    public static final List<String> longitudeHemispheres = Arrays.asList("West", "East");

    /**
     * Full Constructor with all related parameters.
     * Does NOT automatically assign dateCreated and reportNum, instead taking them from the parameters.
     * @param author - The name of the worker who submitted the report.
     * @param longitude - The longitude of the location for the report.
     * @param latitude - The latitude of the location for the report.
     * @param condition - The PurityCondition (safe/treatable/unsafe) of the water as reported.
     * @param ContaminantPPM - The parts per million of contaminants in the water as reported.
     * @param VirusPPM - The parts per million of viruses in the water as reported.
     * @param date - The given date of the report. Not automatically generated.
     * @param id - The given reportNum of the report. Not automatically generated.
     */
    PurityReport(String author, Double longitude, Double latitude, PurityCondition condition, int ContaminantPPM, int VirusPPM, Date date, int id) {
        super(author, longitude, latitude, date, id);
        this.condition = condition;
        this.contaminantPPM = ContaminantPPM;
        this.virusPPM = VirusPPM;
    }

    /**
     * Full Constructor with all related parameters.
     * Automatically assigns dateCreated and reportNum.
     * @param author - The name of the worker who submitted the report.
     * @param longitude - The longitude of the location for the report.
     * @param latitude - The latitude of the location for the report.
     * @param condition - The PurityCondition (safe/treatable/unsafe) of the water as reported.
     * @param ContaminantPPM - The parts per million of contaminants in the water as reported.
     * @param VirusPPM - The parts per million of viruses in the water as reported.
     */
    public PurityReport(String author, Double longitude, Double latitude, PurityCondition condition, int ContaminantPPM, int VirusPPM) {
        super(author, longitude, latitude, new Date(), idNum);
        this.condition = condition;
        this.contaminantPPM = ContaminantPPM;
        this.virusPPM = VirusPPM;
    }

    /**
     * Getter for the purity condition of the report.
     * @return - The purity condition of the report.
     */
    public PurityCondition getCondition() {
        return condition;
    }

    /**
     * Getter for the contaminantPPM of the report.
     * @return - The contaminantPPM of the report.
     */
    public int getContaminantPPM() {
        return contaminantPPM;
    }

    /**
     * Getter for the virusPPM of the report.
     * @return - The virusPPM of the report
     */
    public int getVirusPPM() {
        return virusPPM;
    }
}
