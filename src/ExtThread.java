public class ExtThread extends Thread {
    private long objCreationTime;
    private long firstInstruction;

    public ExtThread() {
        objCreationTime = System.nanoTime();
        firstInstruction = objCreationTime;
    }

    public long getTime(long objCreationTime, long firstInstruction) {
        return objCreationTime - firstInstruction;
    }
}
