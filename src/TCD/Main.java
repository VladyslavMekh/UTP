package TCD;

import java.util.Arrays;

public class Main {

    static void main(String[] args) throws InterruptedException {
        final int WARMUP = 5000;
        final int[] N = {10000, 25000, 50000};

        ExcelPoiConverter converter = new ExcelPoiConverter();

        for (int i = 0; i < N.length; i++) {

            for (int j = 0; j < WARMUP; j++) {
                measureExt();
                measureRun();
            }

            long[] extTimes = new long[N[i]];
            long[] runTimes = new long[N[i]];

            for (int j = 0; j < N[i]; j++) {
                extTimes[j] = measureExt();
                runTimes[j] = measureRun();
            }

            String sheetName = "N = " + N[i];
            String[] threadTypes = {"ExtThread", "Runnable"};
            long[] averageTimes = {averageTime(extTimes), averageTime(runTimes)};
            long[] percentile50 = {percentile(extTimes, 0.50), percentile(runTimes, 0.50)};
            long[] percentile90 = {percentile(extTimes, 0.90), percentile(runTimes, 0.90)};
            long[] percentile99 = {percentile(extTimes, 0.99), percentile(runTimes, 0.99)};

            converter.createSheets(
                    sheetName,
                    threadTypes,
                    averageTimes,
                    percentile50,
                    percentile90,
                    percentile99
            );
            System.out.println("Results exported successfully!");
        }
        converter.saveWorkbook();
    }

    public static long measureExt() throws InterruptedException {
        ExtThread extThread = new ExtThread();
        extThread.start();
        extThread.join();
        return extThread.getTime();
    }

    public static long measureRun() throws InterruptedException {
        RunTask runTask = new RunTask();
        Thread thread = new Thread(runTask);
        thread.start();
        thread.join();
        return runTask.getTime();
    }

    public static long averageTime(long[] data) {
        long sum = 0;
        for (long element : data) {
            sum += element;
        }
        return sum/data.length;
    }

    public static long percentile(long[] data, double p) {
        long[] sorted = data.clone();
        Arrays.sort(sorted);
        int index = (int) Math.ceil(p * sorted.length) - 1;
        return sorted[index];
    }
}