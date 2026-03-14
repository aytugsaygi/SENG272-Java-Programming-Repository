# Software Quality Evaluation System (ISO/IEC 25010)

A Java framework that evaluates software systems against the ISO/IEC 25010 quality model. You define your system's criteria, feed in measured values, and get a scored report with gap analysis.

## Project Structure

```
├── Criterion.java         # A single measurable metric (e.g. Response Time)
├── QualityDimension.java  # A group of criteria (e.g. Reliability)
├── SWSystem.java          # A software system with its dimensions and report logic
├── SWSystemData.java      # Pre-configured systems (ShopSphere, HealthApp)
└── Main.java              # Sets measured values and triggers the report
```

## How It Works

- Each `Criterion` has a `minValue`, `maxValue`, and a direction (`"higher"` or `"lower"`). When you call `setMeasuredValue()`, it normalizes the value into a 1–5 score.
- Each `QualityDimension` averages its criteria scores using weights.
- `SWSystem` averages its dimension scores and finds the weakest one for gap analysis.

## Running

```bash
javac *.java
java Main
```

No external dependencies. Java 8+ is enough.

---

## ISO/IEC 25010 & 25023 Reference

### Implemented Characteristics

| ISO Code | Characteristic | Criterion | Measure (ISO 25023) | Direction | Range | Unit |
|----------|---------------|-----------|---------------------|-----------|-------|------|
| QC.FS | Functional Suitability | Functional Completeness Ratio | FCR = (implemented / specified) × 100 | Higher ↑ | 50–100 | % |
| QC.FS | Functional Suitability | Functional Correctness Ratio | FCoR = (correct / total implemented) × 100 | Higher ↑ | 50–100 | % |
| QC.RE | Reliability | Availability Ratio | AR = (uptime / total time) × 100 | Higher ↑ | 95–100 | % |
| QC.RE | Reliability | Defect Density | DD = defects / KLOC | Lower ↓ | 0–20 | defect/KLOC |
| QC.PE | Performance Efficiency | Response Time | End-to-end latency | Lower ↓ | 100–500 | ms |
| QC.PE | Performance Efficiency | CPU Utilization Ratio | CUR = (used CPU / total CPU) × 100 | Lower ↓ | 10–100 | % |
| QC.MA | Maintainability | Test Coverage Ratio | TCR = (covered lines / total lines) × 100 | Higher ↑ | 30–100 | % |
| QC.MA | Maintainability | Cyclomatic Complexity | CC = edges − nodes + 2 × components | Lower ↓ | 0–20 | score |

### Characteristic Definitions (ISO/IEC 25010:2011)

| Code | Characteristic | Definition |
|------|---------------|------------|
| QC.FS | Functional Suitability | Degree to which the product provides functions that meet stated and implied needs. |
| QC.RE | Reliability | Degree to which a system performs specified functions under defined conditions for a defined period of time. |
| QC.PE | Performance Efficiency | Performance relative to the amount of resources used under stated conditions. |
| QC.MA | Maintainability | Degree of effectiveness and efficiency with which a product can be modified. |

### Score Labels

| Score | Label |
|-------|-------|
| 4.5 – 5.0 | Excellent Quality |
| 3.5 – 4.4 | Good Quality |
| 2.5 – 3.4 | Needs Improvement |
| 1.0 – 2.4 | Poor Quality |

---

## Scoring Formulas

**Higher is better:**
```
score = 1 + ((measuredValue - min) / (max - min)) × 4
```

**Lower is better:**
```
score = 5 - ((measuredValue - min) / (max - min)) × 4
```

Clamped to [1, 5], rounded to nearest 0.5. Dimension and overall scores use weighted averages.

---

## Adding a New System

```java
// In SWSystemData.java
private static SWSystem createMySystem() {
    SWSystem s = new SWSystem("MyApp", "Desktop", "2.0.0");

    QualityDimension security = new QualityDimension("Security", "QC.SE", 30);
    security.addCriterion(new Criterion("Vulnerability Density", 50, "lower", 0, 10, "vuln/KLOC"));
    s.addDimension(security);

    return s;
}
```

The `direction` parameter accepts `"higher"` or `"lower"` (case-insensitive).