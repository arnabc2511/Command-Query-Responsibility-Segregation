package com.techbank.cqrs.core.infrastructure;

import com.techbank.cqrs.core.events.BaseEvents;

import java.util.List;

public interface EventStore {
    void saveEvents(String aggrgateId, Iterable<BaseEvents> events,int expectedVersion);
    List<BaseEvents> getEvents(String aggrgateId);
}
