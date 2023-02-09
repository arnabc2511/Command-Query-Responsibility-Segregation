package com.techbank.cqrs.core.producers;

import com.techbank.cqrs.core.events.BaseEvents;

public interface EventProducer {
    void produce(String topic, BaseEvents events);
}
