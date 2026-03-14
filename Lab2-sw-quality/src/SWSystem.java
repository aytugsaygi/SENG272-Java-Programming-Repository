import java.util.ArrayList;

public class SWSystem {

    private String name;
    private String category;
    private String version;

    private ArrayList<QualityDimension> dimensions;

    public SWSystem(String name, String category, String version) {
        this.name = name;
        this.category = category;
        this.version = version;
        dimensions = new ArrayList<>();
    }

    public void addDimension(QualityDimension d) {
        dimensions.add(d);
    }

    public ArrayList<QualityDimension> getDimensions() {
        return dimensions;
    }

    public double calculateOverallScore() {

        double total = 0;
        double weights = 0;

        for(QualityDimension d : dimensions) {

            total += d.calculateDimensionScore() * d.getWeight();
            weights += d.getWeight();
        }

        double score = total / weights;
        score = Math.round(score * 10) / 10.0;
        return score;
    }

    public QualityDimension findWeakestDimension() {

        QualityDimension weakest = null;
        double min = Double.MAX_VALUE;

        for(QualityDimension d : dimensions) {
            double score = d.calculateDimensionScore();
            if(score < min) {
                min = score;
                weakest = d;
            }
        }
        return weakest;
    }

    public void printReport() {

        System.out.println("========================================");
        System.out.println("SOFTWARE QUALITY EVALUATION REPORT (ISO/IEC 25010)");
        System.out.println("System: " + name + " v" + version + " (" + category + ")");
        System.out.println("========================================");

        for(QualityDimension d : dimensions) {
            System.out.println("\n--- " + d.getName() + " [" + d.getIsoCode() + "] (Weight: " + (int)d.getWeight() + ") ---");

            for(Criterion c : d.getCriteria()) {
                double score = c.calculateScore();
                System.out.println(c.getMetricName() + ": "
                        + c.getMeasuredValue() + " " + c.getUnit()
                        + " -> Score: " + score
                        + " (" + capitalize(c.getDirection()) + " is better)");
            }
            double dimScore = d.calculateDimensionScore();
            System.out.println(">> Dimension Score: " + dimScore
                    + "/5 (" + d.getQualityLabel(dimScore) + ")");
        }

        double overall = calculateOverallScore();

        System.out.println("\n========================================");
        System.out.println("OVERALL QUALITY SCORE: " + overall + "/5");
        System.out.println("========================================");

        QualityDimension weakest = findWeakestDimension();
        double weakestScore = weakest.calculateDimensionScore();
        double gap = Math.round((5 - weakestScore) * 10) / 10.0;

        System.out.println("\n========================================");
        System.out.println("GAP ANALYSIS (ISO/IEC 25010)");
        System.out.println("========================================");
        System.out.println("Weakest Characteristic : " + weakest.getName() + " [" + weakest.getIsoCode() + "]");
        System.out.println("Score: " + weakestScore + "/5  |  Gap: " + gap);
        System.out.println("Level: " + weakest.getQualityLabel(weakestScore));
        System.out.println(">> This characteristic requires the most improvement.");
        System.out.println("========================================");
    }

    private String capitalize(String text) {
        return text.substring(0,1).toUpperCase() + text.substring(1).toLowerCase();
    }
}