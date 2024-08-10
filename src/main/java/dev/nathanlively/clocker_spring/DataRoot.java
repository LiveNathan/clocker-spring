package dev.nathanlively.clocker_spring;

import java.util.ArrayList;
import java.util.List;

public class DataRoot
{
    private List<ClockEvent> clockEvents = new ArrayList<>();

//    public DataRoot()
//    {
//        super();
//    }

    public List<ClockEvent> getClockEvents() {
        return clockEvents;
    }

    public void setClockEvents(List<ClockEvent> clockEvents) {
        this.clockEvents = clockEvents;
    }
}
