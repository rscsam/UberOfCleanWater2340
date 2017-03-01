package a2340.uberofcleanwater.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Adds new water report to report list
 *
 * @author Anna Bergstrom
 * @author Sylvia Necula
 * @version 1.0
 * @since 2017-02-28
 */

public class ReportList {

    private static List<WaterReport> _reports = new ArrayList<>();

    /**
     * adds a report to the list
     * @param newReport the report to add
     */
    public static void addReport(WaterReport newReport) {
        _reports.add(newReport);
    }
}
