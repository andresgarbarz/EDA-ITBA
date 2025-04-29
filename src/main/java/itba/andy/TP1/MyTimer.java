package itba.andy.TP1;

public class MyTimer {
    private final long startTime;
    private long endTime;

    public MyTimer(long startTime) {
        this.startTime = startTime;
    }

    public MyTimer() {
        this(System.currentTimeMillis());
    }

    public void stop(long endTime) {
        this.endTime = endTime;
    }

    public void stop() {
        this.stop(System.currentTimeMillis());
    }

    private String formatElapsedTime(long millis) {
        long ms = millis % 1000;
        long seconds = (millis / 1000) % 60;
        long minutes = (millis / (1000 * 60)) % 60;
        long hours = millis / (1000 * 60 * 60);

        return String.format("Tiempo transcurrido: %d hrs, %d mins, %d secs, %d ms", hours, minutes, seconds, ms);
    }

    @Override
    public String toString() {
        return formatElapsedTime(endTime - startTime);
    }
}
