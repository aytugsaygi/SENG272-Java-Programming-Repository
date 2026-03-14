import java.util.ArrayList;
import java.util.HashMap;

public class SWSystemData {

    public static HashMap<String, ArrayList<SWSystem>> getAllSystems() {

        HashMap<String, ArrayList<SWSystem>> map = new HashMap<>();
        ArrayList<SWSystem> webList = new ArrayList<>();
        webList.add(createShopSphere());
        map.put("Web", webList);

        ArrayList<SWSystem> mobileList = new ArrayList<>();
        mobileList.add(createHealthApp());
        map.put("Mobile", mobileList);
        return map;
    }

    private static SWSystem createShopSphere() {

        SWSystem s = new SWSystem("ShopSphere","Web","3.2.1");

        QualityDimension func = new QualityDimension("Functional Suitability","QC.FS",25);
        func.addCriterion(new Criterion("Functional Completeness Ratio",50,"higher",50,100,"%"));
        func.addCriterion(new Criterion("Functional Correctness Ratio",50,"higher",50,100,"%"));
        s.addDimension(func);

        QualityDimension reliability = new QualityDimension("Reliability","QC.RE",25);
        reliability.addCriterion(new Criterion("Availability Ratio",50,"higher",95,100,"%"));
        reliability.addCriterion(new Criterion("Defect Density",50,"lower",0,20,"defect/KLOC"));
        s.addDimension(reliability);

        QualityDimension performance = new QualityDimension("Performance Efficiency","QC.PE",25);
        performance.addCriterion(new Criterion("Response Time",50,"lower",100,500,"ms"));
        performance.addCriterion(new Criterion("CPU Utilization Ratio",50,"lower",10,100,"%"));
        s.addDimension(performance);

        QualityDimension maintain = new QualityDimension("Maintainability","QC.MA",25);
        maintain.addCriterion(new Criterion("Test Coverage Ratio",50,"higher",30,100,"%"));
        maintain.addCriterion(new Criterion("Cyclomatic Complexity",50,"lower",0,20,"score"));
        s.addDimension(maintain);

        return s;
    }

    private static SWSystem createHealthApp() {

        SWSystem s = new SWSystem("HealthApp","Mobile","1.5.0");

        QualityDimension func = new QualityDimension("Functional Suitability","QC.FS",25);
        func.addCriterion(new Criterion("Functional Completeness Ratio",50,"higher",50,100,"%"));
        func.addCriterion(new Criterion("Functional Correctness Ratio",50,"higher",50,100,"%"));
        s.addDimension(func);

        QualityDimension reliability = new QualityDimension("Reliability","QC.RE",25);
        reliability.addCriterion(new Criterion("Availability Ratio",50,"higher",95,100,"%"));
        reliability.addCriterion(new Criterion("Defect Density",50,"lower",0,20,"defect/KLOC"));
        s.addDimension(reliability);

        QualityDimension performance = new QualityDimension("Performance Efficiency","QC.PE",25);
        performance.addCriterion(new Criterion("Response Time",50,"lower",100,500,"ms"));
        performance.addCriterion(new Criterion("CPU Utilization Ratio",50,"lower",10,100,"%"));
        s.addDimension(performance);

        QualityDimension maintain = new QualityDimension("Maintainability","QC.MA",25);
        maintain.addCriterion(new Criterion("Test Coverage Ratio",50,"higher",30,100,"%"));
        maintain.addCriterion(new Criterion("Cyclomatic Complexity",50,"lower",0,20,"score"));
        s.addDimension(maintain);

        return s;
    }
}