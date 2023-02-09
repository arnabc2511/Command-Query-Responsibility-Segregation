package com.techbank.cqrs.core.domain;

import com.techbank.cqrs.core.events.BaseEvents;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AggregateRoot {

    protected String id;

    private int version = -1;

    private final List<BaseEvents> changes = new ArrayList<>();

    private final Logger logger = Logger.getLogger(AggregateRoot.class.getName());

    public List<BaseEvents> getUncommittedChanges(){
        return this.changes;
    }
    public String getId() {
        return this.id;
    }
    public void markChangesAsCommitted(){
        this.changes.clear();
    }
    public int getVersion() {
        return this.version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
    protected void applyChange(BaseEvents event,Boolean isNewEvent){
        try {
            var method = getClass().getDeclaredMethod("apply",event.getClass());
            method.setAccessible(true);
            method.invoke(this,event);

        } catch (NoSuchMethodException e){
            logger.log(Level.WARNING, MessageFormat.format("The apply method was not found in the aggregate for {0}",event.getClass().getName()));
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error applying event to aggregate", e);
        }finally {
            if (isNewEvent){
                changes.add(event);
            }
        }

    }
    public void raiseEvent(BaseEvents event){
        applyChange(event,true);
    }

    public void replayEvents(Iterable<BaseEvents> events){
        events.forEach(event -> applyChange(event,false));
    }

}
