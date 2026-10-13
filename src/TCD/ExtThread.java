package TCD;

public class ExtThread extends Thread {
    private long objCreationTime;
    private long firstInstruction;

    public ExtThread() {
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
