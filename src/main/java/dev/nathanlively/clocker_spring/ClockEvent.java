package dev.nathanlively.clocker_spring;

import java.time.LocalDateTime;
import java.util.Objects;

public final class ClockEvent {
    private final LocalDateTime time;
    private final ClockEventType type;

    public ClockEvent(LocalDateTime time, ClockEventType type) {
        if (time == null) {
            throw new IllegalArgumentException("Event time is empty");
        }
        if (type == null) {
            throw new IllegalArgumentException("Event type is empty");
        }
        this.time = time;
        this.type = type;
    }

    public LocalDateTime time() {
        return time;
    }

    public ClockEventType type() {
        return type;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (ClockEvent) obj;
        return Objects.equals(this.time, that.time) &&
                Objects.equals(this.type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(time, type);
    }

    @Override
    public String toString() {
        return "ClockEvent[" +
                "time=" + time + ", " +
                "type=" + type + ']';
    }

}
