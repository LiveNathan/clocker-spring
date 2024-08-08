package dev.nathanlively.clocker_spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataRoot
{
//    private final ClockEvents clockEvents = new ClockEvents();
    private final Map<Long, ClockEvent> clockEvents = new HashMap<>();

    public DataRoot()
    {
        super();
    }

    public List<ClockEvent> allClockEvents() {
        return new ArrayList<>(this.clockEvents.values());
    }

}
