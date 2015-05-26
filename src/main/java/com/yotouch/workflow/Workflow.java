package com.yotouch.workflow;

import java.util.Map;

import com.yotouch.entity.Entity;

public class Workflow {
    
    private WorkflowManager wfMgr;
    private Map<String, State> states;
    private Map<String, Action> actions;
    private Entity wfEntity;

    public Workflow(WorkflowManager wfMgr, Entity wfEntity) {
        this.wfMgr = wfMgr;
        this.wfEntity = wfEntity;
    }
    
    public String getName() {
        return this.wfEntity.getStringValue("name");
    }
    
    public void setState(State state) {
        this.states.put(state.getName(), state);
    }

    public State getState(String from) {
        return this.states.get(from);
    }

    public void addAction(Action action) {
        this.actions.put(action.getName(), action);
        
        if (action.getFrom().equals(new AnyState(this))) {
        }
    }
    
    

}
