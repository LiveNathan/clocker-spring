package dev.nathanlively.clocker_spring;

import java.util.ArrayList;
import java.util.List;

public class DataRoot
{
    private final List<ClockEvent> clockEvents = new ArrayList<>();

    public DataRoot()
    {
        super();
    }

    public List<ClockEvent> allClockEvents() {
        return new ArrayList<>(this.clockEvents);
    }

}
