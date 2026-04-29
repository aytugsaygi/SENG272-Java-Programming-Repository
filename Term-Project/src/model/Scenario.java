package model;

import java.util.ArrayList;
import java.util.List;

public class Scenario {
    private String name;
    private List<QualityDimension> dimensions;

    public Scenario(String name) {
        this.name = name;
        this.dimensions = new ArrayList<>();
    }

    public void addDimension(QualityDimension dimension) {
        dimensions.add(dimension);
    }

    public String getName() {
        return name;
    }

    public List<QualityDimension> getDimensions() {
        return dimensions;
    }

    @Override
    public String toString() {
        return name;
    }
}
