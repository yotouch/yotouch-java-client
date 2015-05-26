package com.yotouch.workflow;

import com.yotouch.entity.Entity;

public class Action {
    
    private Workflow wf;
    private Entity actionEntity;
    private State fromState;
    private State toState;

    public Action(Workflow wf, Entity actionEntity) {
        this.wf = wf;
        this.actionEntity = actionEntity;
        
        String from = actionEntity.getStringValue("from");
        this.fromState = wf.getState(from);
        this.fromState.addOutAction(this);
        
        String to = actionEntity.getStringValue("to");
        this.toState = wf.getState(to);
        this.toState.addInAction(this);
        
        wf.addAction(this);
        
    }

    public String getName() {
        return this.actionEntity.getStringValue("name");
    }

    public State getFrom() {
        return this.fromState;
    }
    
    public State getTo() {
        return this.toState;
    }
    
    

}
