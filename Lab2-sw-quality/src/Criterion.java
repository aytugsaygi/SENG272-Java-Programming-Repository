public class Criterion {

    private String metricName;
    private double weight;
    private String direction;
    private double minValue;
    private double maxValue;
    private String unit;
    private double measuredValue;

    public Criterion(String metricName, double weight, String direction,
                     double minValue, double maxValue, String unit) {

        this.metricName = metricName;
        this.weight = weight;
        this.direction = direction;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.unit = unit;
    }

    public void setMeasuredValue(double value) {
        measuredValue = value;
    }

    public double getMeasuredValue() {
        return measuredValue;
    }

    public double getWeight() {
        return weight;
    }

    public String getMetricName() {
        return metricName;
    }

    public String getDirection() {
        return direction;
    }

    public String getUnit() {
        return unit;
    }

    public double calculateScore() {

        double score;

        if(direction.equalsIgnoreCase("higher")) {
            score = 1 + (measuredValue - minValue) / (maxValue - minValue) * 4;
        } else {
            score = 5 - (measuredValue - minValue) / (maxValue - minValue) * 4;
        }

        score = Math.max(1, Math.min(5, score));
        score = Math.round(score * 2) / 2.0;

        return score;
    }
}