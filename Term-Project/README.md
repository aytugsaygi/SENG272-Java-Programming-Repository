# ISO 15939 Measurement Process Simulator
### Software Project II — Term Project

| | |
|---|---|
| **Student Name** | Aytuğ SAYGI |
| **Student ID** | 202317034 |

---

## Project Description

A Java Swing desktop application that simulates the 5-step ISO/IEC 15939 software measurement process:

| Step | Name | Description |
|------|------|-------------|
| 1 | Profile | Enter user and session information |
| 2 | Define | Select quality type, mode, and scenario |
| 3 | Plan | View dimensions and metrics for scenario |
| 4 | Collect | View raw data and calculated scores (1–5) |
| 5 | Analyse | Weighted averages, radar chart, gap analysis |

---

## Requirements

- Java SE 17 or higher
- No external libraries required (standard Java SE library only)

---

## Compilation

Navigate to the project root directory and run:

```bash
javac -d bin src/Main.java src/controller/*.java src/model/*.java src/view/*.java
```

---

## How to Run

```bash
java -cp bin Main
```

---

## Screenshot

You can see `screenshots` in the project root directory.
