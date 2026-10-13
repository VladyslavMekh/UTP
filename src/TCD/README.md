# Thread Creation Delay (TCD)

### Overview

This project compares two ways of defining work for a Java thread:

* **ExtThread** - a class that extends Thread.
* **RunTask** - a class that implements Runnable, passed to a Thread.

For each implementation, the program records the time between creating the task
object and the first instruction in its **run ()** method. It runs measurements
for N = 10000, N = 25000, and N = 50000, after a warm-up phase of 5000
iterations for each N.

The program calculates:

* **Average** execution-start latency
* **P50 (median)**
* **P90**
* **P99**

The results are written to one Excel workbook, with a separate for each value of
N.

### Project structure

```text
TCD_Thread_Benchmark/
├── README.md
└── src/
    └── TCD/
        ├── Main.java
        ├── ExtThread.java
        ├── RunTask.java
        ├── ExcelPoiConverter.java
        └── Output/
            └── benchmark_result.xlsx   # created when the program runs
```

### Requirements

* A JDK installed.
* Apache POI libraries available on the project's classpath to write .xlsx
  files.
* An IDE such as InteliJ IDEA, or another Java build/run environment.

This example does not require Maven or Gradle if you add the Apache POI JAR
files manually to the project's classpath.

### Apache POI setup without Maven

Download the Apache POI binary distribution from the official Apache POI website
and add the required JARs to your IDE's project/module dependencies. The .xlsx
API uses XSSFWorkbook from poi-ooxml ; the distribution includes additional
required dependencies, so add the required JARs as documented for the version
you download. A missing dependency may cause a ClassNotFoundException or
NoClassDefFoundError.

### How to run

- **1.** Open the project in your IDE.
- **2.** Make sure all Java files are in package TCD and the Apache POI
  dependencies are configured.
- **3.** Run TCD.Main.
- **4.** After the measurements finish, open
  src/TCD/Output/benchmark_result.xsls.

The program creates these worksheets:

* N = 10000
* N = 25000
* N = 50000

Each worksheet contains one row for ExtThread and one for Runnable.
The output folder must exist before the workbook is saved. This project includes
the src/TCD/Output folder; depending on how files are transferred, an empty
folder may not be retained by version control.

### Metrics and interpretation

* **Average** is the arithmetic mean of all recoded values.
* **P50** is the median, calculated with the nearest-rank-style index used in
  this project.
* **P90** is the value at the 90th percentile.
* **P99** is the value at the 99th percentile.

All values are expressed in **nanoseconds (ns)**.

The measurements represent the interval from task-object construction to the
first timestamp recorded inside run (). They do **not** represent the full time
taken for a thread to finish executing. Results can vary due to JVM warm-up and
JIT compilation, operating-system scheduling, CPU load, and garbage collection.
For meaningful comparisons, run the experiment multiple times and interpret
small differences cautiously.

### Excel output

ExcelPoiConverter creates a workbook in memory, adds one worksheet for each N,
and writes the workbook once after all measurements are complete. If the program
is run again, the existing workbook at the output path is overwritten with a new
workbook.

### Source files

The Java source files are under src/TCD/ and all use the package declaration
package TCD;