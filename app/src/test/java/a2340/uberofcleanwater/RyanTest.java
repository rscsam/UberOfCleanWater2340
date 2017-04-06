package a2340.uberofcleanwater;

import junit.framework.Assert;
import org.junit.Test;

import a2340.uberofcleanwater.model.WaterCondition;
import a2340.uberofcleanwater.model.WaterReport;

/**
 * Ryan's test for M10 on StringtoWC in WaterReport.java
 *
 * @author Ryan Anderson
 * @version 1.0
 * @since 4/5/2017
 */
public class RyanTest {

    @Test
    public void TestNull() {
        Assert.assertNull("Passing a null into the method", WaterReport.stringToWC(null));
    }

    @Test
    public void TestNonWaterCondition() {
        Assert.assertNull("Passing a string which does not correspond to a water condition",
                WaterReport.stringToWC("ootsm dootsm"));
    }

    @Test
    public void TestTreatableClear() {
        Assert.assertSame("Passing the string 'Treatable/Clear'", WaterReport.stringToWC(
                "Treatable/Clear"), WaterCondition.TreatableClear);
    }

    @Test
    public void TestTreatableMuddy() {
        Assert.assertSame("Passing the string 'Treatable/Muddy'", WaterReport.stringToWC(
                "Treatable/Muddy"), WaterCondition.TreatableMuddy);
    }

    @Test
    public void TestWaste() {
        Assert.assertSame("Passing the string 'Waste'", WaterReport.stringToWC(
                "Waste"), WaterCondition.Waste);
    }

    @Test
    public void TestPotable() {
        Assert.assertSame("Passing the string 'Potable'", WaterReport.stringToWC(
                "Potable"), WaterCondition.Potable);
    }

}
