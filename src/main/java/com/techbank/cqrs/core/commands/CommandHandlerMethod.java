package com.techbank.cqrs.core.commands;

import com.techbank.cqrs.core.events.BaseEvents;
@FunctionalInterface
public interface CommandHandlerMethod <T extends BaseCommand> {

    void handle(T command);
}
