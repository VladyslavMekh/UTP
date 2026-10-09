package TCD;

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

        System.out.println("Average time for extTimes: " + averageTime(extTimes));
        System.out.println("Average time for runTimes: " + averageTime(runTimes) + "\n");

        System.out.println("For extTimes P50 = " + percentile(extTimes, 0.50));
        System.out.println("For runTimes P50 = " + percentile(runTimes, 0.50) + " ns. \n");

        System.out.println("For extTimes P90 = " + percentile(extTimes, 0.90));
        System.out.println("For runTimes P90 = " + percentile(runTimes, 0.90) + " ns. \n");

        System.out.println("For extTimes P99 = " + percentile(extTimes, 0.99));
        System.out.println("For runTimes P99 = " + percentile(runTimes, 0.99) + " ns. \n");
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