package a2340.uberofcleanwater.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by annabergstrom on 23/02/17.
 */

public class Model {

    private static List<WaterReport> _reports = new ArrayList<>();

    public static void addReport(WaterReport newReport) {
        _reports.add(newReport);
    }
}
