package com.yotouch.workflow;

import com.yotouch.Constants;
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
        //System.out.println("from state name " + from);
        this.fromState = this.wf.getState(from);
        this.fromState.addOutAction(this);
        
        String to = actionEntity.getStringValue("to");
        //System.out.println("to state name " + to);
        this.toState = this.wf.getState(to);
        this.toState.addInAction(this);
        
        this.wf.addAction(this);
        
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

    public static Action create(Workflow wf, Entity a) {
        return new Action(wf, a);
    }

    public Entity act(Entity entity) {
        State s = this.getTo();
        if (!Constants.WF_STATE_SELF.equals(s.getName())) {
            entity.setState(s);
        }
        
        return entity;
    }
    
    

}
