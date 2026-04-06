package model;

import java.util.ArrayList;
import java.util.List;

public class QualityDimension {
    private String name;
    private int coefficient;
    private List<Metric> metrics;

    public QualityDimension(String name, int coefficient) {
        this.name = name;
        this.coefficient = coefficient;
        this.metrics = new ArrayList<>();
    }

    public void addMetric(Metric metric) {
        metrics.add(metric);
    }

    public String getName() { return name; }
    public int getCoefficient() { return coefficient; }
    public List<Metric> getMetrics() { return metrics; }

    public double calculateDimensionScore() {
        double sumWeightedScores = 0.0;
        double sumCoefficients = 0.0;
        for (Metric m : metrics) {
            sumWeightedScores += m.calculateScore() * m.getCoefficient();
            sumCoefficients += m.getCoefficient();
        }
        if (sumCoefficients == 0) return 0;
        return sumWeightedScores / sumCoefficients;
    }
}
