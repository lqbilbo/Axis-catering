package performance;

public class BailoutFuture {
    private double iterationsPerSecond;
    private long recordsAdded, recordsRemoved, nullCounter;

    public BailoutFuture(double iterationsPerSecond, long recordsAdded, long recordsRemoved, long nullCounter) {
        this.iterationsPerSecond = iterationsPerSecond;
        this.recordsAdded = recordsAdded;
        this.recordsRemoved = recordsRemoved;
        this.nullCounter = nullCounter;
    }

    public double getIterationsPerSecond() {
        return iterationsPerSecond;
    }

    public void setIterationsPerSecond(double iterationsPerSecond) {
        this.iterationsPerSecond = iterationsPerSecond;
    }

    public long getRecordsAdded() {
        return recordsAdded;
    }

    public void setRecordsAdded(long recordsAdded) {
        this.recordsAdded = recordsAdded;
    }

    public long getRecordsRemoved() {
        return recordsRemoved;
    }

    public void setRecordsRemoved(long recordsRemoved) {
        this.recordsRemoved = recordsRemoved;
    }

    public long getNullCounter() {
        return nullCounter;
    }

    public void setNullCounter(long nullCounter) {
        this.nullCounter = nullCounter;
    }
}
