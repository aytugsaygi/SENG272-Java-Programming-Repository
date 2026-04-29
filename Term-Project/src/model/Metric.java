package model;

public class Metric {
    private String name;
    private int coefficient;
    private String direction;
    private double rangeMin;
    private double rangeMax;
    private String unit;
    private double value;

    public Metric(String name, int coefficient, String direction, double rangeMin, double rangeMax, String unit,
            double value) {
        this.name = name;
        this.coefficient = coefficient;
        this.direction = direction;
        this.rangeMin = rangeMin;
        this.rangeMax = rangeMax;
        this.unit = unit;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public int getCoefficient() {
        return coefficient;
    }

    public String getDirection() {
        return direction;
    }

    public double getRangeMin() {
        return rangeMin;
    }

    public double getRangeMax() {
        return rangeMax;
    }

    public String getUnit() {
        return unit;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double calculateScore() {
        double min = rangeMin;
        double max = rangeMax;
        double score;
        if (direction.equalsIgnoreCase("Higher is better") || direction.contains("Higher")) {
            score = 1.0 + (value - min) / (max - min) * 4.0;
        } else {
            score = 5.0 - (value - min) / (max - min) * 4.0;
        }
        score = Math.max(1.0, Math.min(5.0, score));
        score = Math.round(score * 2.0) / 2.0;
        return score;
    }

    @Override
    public String toString() {
        return name;
    }
}
