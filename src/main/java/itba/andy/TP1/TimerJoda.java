package itba.andy.TP1;

import org.joda.time.Instant;
import org.joda.time.Period;

public class TimerJoda {
    private final Instant startTime;
    private Instant endTime;
    private Period elapsedTime;

    public TimerJoda(long startTimeMillis) {
        startTime = new Instant(startTimeMillis);
    }

    public TimerJoda() {
        this(System.currentTimeMillis());
    }

    public void stop(long stopTimeMillis) {
        endTime = new Instant(stopTimeMillis);

        if (endTime.isBefore(startTime)) {
            throw new IllegalStateException("El tiempo de parada no puede ser anterior al tiempo de inicio.");
        }
        new Period(startTime, endTime);
    }

    public void stop() {
        this.stop(System.currentTimeMillis());
    }

    @Override
    public String toString() {
        if (endTime == null) {
            return "El temporizador no ha sido detenido.";
        }
        elapsedTime = new Period(startTime, endTime);
        return String.format("Tiempo transcurrido: %d hrs, %d mins, %d secs, %d ms",
                elapsedTime.getHours(), elapsedTime.getMinutes(), elapsedTime.getSeconds(), elapsedTime.getMillis());
    }
}
