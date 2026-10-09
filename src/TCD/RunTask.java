package TCD;

public class RunTask implements Runnable {
    private long objCreationTime;
    private long firstInstruction;

    public RunTask() {
        objCreationTime = System.nanoTime();
    }

    @Override
    public void run() {
        firstInstruction = System.nanoTime();
    }

    public long getTime() {
        return firstInstruction - objCreationTime;
    }
}
