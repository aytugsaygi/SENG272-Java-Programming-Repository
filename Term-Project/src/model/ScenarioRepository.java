package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScenarioRepository {

    // mode -> list of scenarios
    private static final Map<String, List<Scenario>> scenarioMap = new HashMap<>();

    static {
        // ========== HEALTH MODE ==========
        List<Scenario> healthScenarios = new ArrayList<>();

        // Health - Scenario A
        Scenario healthA = new Scenario("Scenario A — Hospital Portal");
        QualityDimension hA_usability = new QualityDimension("Usability", 25);
        hA_usability.addMetric(new Metric("Task success rate", 50, "Higher is better", 0, 100, "%", 82));
        hA_usability.addMetric(new Metric("Error recovery time", 50, "Lower is better", 0, 120, "sec", 30));
        healthA.addDimension(hA_usability);

        QualityDimension hA_perf = new QualityDimension("Performance Efficiency", 20);
        hA_perf.addMetric(new Metric("Page load time", 50, "Lower is better", 0, 10, "sec", 2));
        hA_perf.addMetric(new Metric("Concurrent users", 50, "Higher is better", 0, 1000, "users", 750));
        healthA.addDimension(hA_perf);

        QualityDimension hA_sec = new QualityDimension("Security", 30);
        hA_sec.addMetric(new Metric("Auth failure rate", 50, "Lower is better", 0, 100, "%", 5));
        hA_sec.addMetric(new Metric("Data encryption coverage", 50, "Higher is better", 0, 100, "%", 95));
        healthA.addDimension(hA_sec);

        QualityDimension hA_rel = new QualityDimension("Reliability", 25);
        hA_rel.addMetric(new Metric("Uptime", 50, "Higher is better", 95, 100, "%", 99));
        hA_rel.addMetric(new Metric("MTTR", 50, "Lower is better", 0, 180, "min", 20));
        healthA.addDimension(hA_rel);
        healthScenarios.add(healthA);

        // Health - Scenario B
        Scenario healthB = new Scenario("Scenario B — Patient Monitoring");
        QualityDimension hB_acc = new QualityDimension("Accessibility", 20);
        hB_acc.addMetric(new Metric("WCAG compliance", 50, "Higher is better", 0, 100, "%", 88));
        hB_acc.addMetric(new Metric("Screen reader score", 50, "Higher is better", 0, 100, "%", 72));
        healthB.addDimension(hB_acc);

        QualityDimension hB_perf = new QualityDimension("Performance Efficiency", 25);
        hB_perf.addMetric(new Metric("Alert response time", 50, "Lower is better", 0, 60, "sec", 8));
        hB_perf.addMetric(new Metric("Data refresh rate", 50, "Higher is better", 0, 100, "%", 95));
        healthB.addDimension(hB_perf);

        QualityDimension hB_rel = new QualityDimension("Reliability", 30);
        hB_rel.addMetric(new Metric("Sensor uptime", 50, "Higher is better", 95, 100, "%", 99.5));
        hB_rel.addMetric(new Metric("Data loss rate", 50, "Lower is better", 0, 10, "%", 0.5));
        healthB.addDimension(hB_rel);

        QualityDimension hB_sec = new QualityDimension("Security", 25);
        hB_sec.addMetric(new Metric("HIPAA compliance", 50, "Higher is better", 0, 100, "%", 97));
        hB_sec.addMetric(new Metric("Intrusion attempts blocked", 50, "Higher is better", 0, 100, "%", 99));
        healthB.addDimension(hB_sec);
        healthScenarios.add(healthB);

        scenarioMap.put("Health", healthScenarios);

        // ========== EDUCATION MODE ==========
        List<Scenario> educationScenarios = new ArrayList<>();

        // Education - Scenario C (from spec)
        Scenario eduC = new Scenario("Scenario C — Team Alpha");
        QualityDimension eC_usability = new QualityDimension("Usability", 25);
        eC_usability.addMetric(new Metric("SUS score", 50, "Higher is better", 0, 100, "points", 89));
        eC_usability.addMetric(new Metric("Onboarding time", 50, "Lower is better", 0, 60, "min", 5));
        eduC.addDimension(eC_usability);

        QualityDimension eC_perf = new QualityDimension("Perf. Efficiency", 20);
        eC_perf.addMetric(new Metric("Video start time", 50, "Lower is better", 0, 15, "sec", 3));
        eC_perf.addMetric(new Metric("Concurrent exams", 50, "Higher is better", 0, 600, "users", 450));
        eduC.addDimension(eC_perf);

        QualityDimension eC_acc = new QualityDimension("Accessibility", 20);
        eC_acc.addMetric(new Metric("WCAG compliance", 50, "Higher is better", 0, 100, "%", 91));
        eC_acc.addMetric(new Metric("Screen reader score", 50, "Higher is better", 0, 100, "%", 78));
        eduC.addDimension(eC_acc);

        QualityDimension eC_rel = new QualityDimension("Reliability", 20);
        eC_rel.addMetric(new Metric("Uptime", 50, "Higher is better", 95, 100, "%", 99.2));
        eC_rel.addMetric(new Metric("MTTR", 50, "Lower is better", 0, 120, "min", 15));
        eduC.addDimension(eC_rel);

        QualityDimension eC_func = new QualityDimension("Func. Suitability", 15);
        eC_func.addMetric(new Metric("Feature completion", 50, "Higher is better", 0, 100, "%", 87));
        eC_func.addMetric(new Metric("Assignment submit rate", 50, "Higher is better", 0, 100, "%", 93));
        eduC.addDimension(eC_func);
        educationScenarios.add(eduC);

        // Education - Scenario D
        Scenario eduD = new Scenario("Scenario D — Team Beta");
        QualityDimension eD_usability = new QualityDimension("Usability", 25);
        eD_usability.addMetric(new Metric("SUS score", 50, "Higher is better", 0, 100, "points", 72));
        eD_usability.addMetric(new Metric("Onboarding time", 50, "Lower is better", 0, 60, "min", 25));
        eduD.addDimension(eD_usability);

        QualityDimension eD_perf = new QualityDimension("Perf. Efficiency", 20);
        eD_perf.addMetric(new Metric("Video start time", 50, "Lower is better", 0, 15, "sec", 7));
        eD_perf.addMetric(new Metric("Concurrent exams", 50, "Higher is better", 0, 600, "users", 310));
        eduD.addDimension(eD_perf);

        QualityDimension eD_acc = new QualityDimension("Accessibility", 20);
        eD_acc.addMetric(new Metric("WCAG compliance", 50, "Higher is better", 0, 100, "%", 65));
        eD_acc.addMetric(new Metric("Screen reader score", 50, "Higher is better", 0, 100, "%", 55));
        eduD.addDimension(eD_acc);

        QualityDimension eD_rel = new QualityDimension("Reliability", 20);
        eD_rel.addMetric(new Metric("Uptime", 50, "Higher is better", 95, 100, "%", 97));
        eD_rel.addMetric(new Metric("MTTR", 50, "Lower is better", 0, 120, "min", 45));
        eduD.addDimension(eD_rel);

        QualityDimension eD_func = new QualityDimension("Func. Suitability", 15);
        eD_func.addMetric(new Metric("Feature completion", 50, "Higher is better", 0, 100, "%", 74));
        eD_func.addMetric(new Metric("Assignment submit rate", 50, "Higher is better", 0, 100, "%", 81));
        eduD.addDimension(eD_func);
        educationScenarios.add(eduD);

        scenarioMap.put("Education", educationScenarios);

        // ========== CUSTOM MODE ==========
        List<Scenario> customScenarios = new ArrayList<>();
        // Custom mode uses a placeholder scenario; user defines from scratch
        Scenario customA = new Scenario("Custom Scenario 1");
        QualityDimension cA_dim1 = new QualityDimension("Quality", 50);
        cA_dim1.addMetric(new Metric("Custom Metric 1", 50, "Higher is better", 0, 100, "points", 70));
        cA_dim1.addMetric(new Metric("Custom Metric 2", 50, "Lower is better", 0, 100, "points", 30));
        customA.addDimension(cA_dim1);
        QualityDimension cA_dim2 = new QualityDimension("Performance", 50);
        cA_dim2.addMetric(new Metric("Response Time", 50, "Lower is better", 0, 10, "sec", 2));
        cA_dim2.addMetric(new Metric("Throughput", 50, "Higher is better", 0, 1000, "req/s", 800));
        customA.addDimension(cA_dim2);
        customScenarios.add(customA);

        Scenario customB = new Scenario("Custom Scenario 2");
        QualityDimension cB_dim1 = new QualityDimension("Usability", 60);
        cB_dim1.addMetric(new Metric("User Satisfaction", 50, "Higher is better", 0, 100, "%", 85));
        cB_dim1.addMetric(new Metric("Task Completion", 50, "Higher is better", 0, 100, "%", 90));
        customB.addDimension(cB_dim1);
        QualityDimension cB_dim2 = new QualityDimension("Reliability", 40);
        cB_dim2.addMetric(new Metric("Uptime", 50, "Higher is better", 95, 100, "%", 99));
        cB_dim2.addMetric(new Metric("Error Rate", 50, "Lower is better", 0, 10, "%", 1));
        customB.addDimension(cB_dim2);
        customScenarios.add(customB);

        scenarioMap.put("Custom", customScenarios);
    }

    public static List<Scenario> getScenariosForMode(String mode) {
        return scenarioMap.getOrDefault(mode, new ArrayList<>());
    }

    public static List<String> getModes() {
        List<String> modes = new ArrayList<>();
        modes.add("Health");
        modes.add("Education");
        modes.add("Custom");
        return modes;
    }
}
