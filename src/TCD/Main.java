package TCD;

import java.io.IOException;
import java.util.Arrays;

public class Main {

    static void main(String[] args) throws InterruptedException {
        final int WARMUP = 5000;
        final int N = 50000;

        for (int i = 0; i < WARMUP; i++) {
            measureExt();
            measureRun();
        }

        long[] extTimes = new long[N];
        long[] runTimes = new long[N];

        for (int i = 0; i < N; i++) {
            extTimes[i] = measureExt();
            runTimes[i] = measureRun();
        }

        String[] threadTypes = {"ExtThread", "Runnable"};
        long[] averageTimes = {(long) averageTime(extTimes), (long) averageTime(runTimes)};
        long[] percentile50 = {percentile(extTimes, 0.50), percentile(runTimes, 0.50)};
        long[] percentile90 = {percentile(extTimes, 0.90), percentile(runTimes, 0.90)};
        long[] percentile99 = {percentile(extTimes, 0.99), percentile(runTimes, 0.99)};

        try {
            ExcelExport.exportResults(
                    threadTypes,
                    averageTimes,
                    percentile50,
                    percentile90,
                    percentile99
            );
            System.out.println("Results exported successfully!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

    public static double averageTime(long[] data) {
        long sum = 0;
        for (long element : data) sum += element;
        return (double) sum/data.length;
    }

    public static long percentile(long[] data, double p) {
        long[] sorted = data.clone();
        Arrays.sort(sorted);
        int index = (int) Math.ceil(p * sorted.length) - 1;
        return sorted[index];
    }
}