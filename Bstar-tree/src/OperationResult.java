public class OperationResult {

    private final long operations;
    private final long timeNs;

    public OperationResult(long operations, long timeNs) {
        this.operations = operations;
        this.timeNs = timeNs;
    }

    public long getOperations() {
        return operations;
    }

    public long getTimeNs() {
        return timeNs;
    }
}