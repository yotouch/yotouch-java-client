package com.yotouch.workflow;

import java.util.HashMap;
import java.util.Map;

import com.yotouch.entity.Entity;

public class Workflow {
    
    private Map<String, State> states;
    private Map<String, Action> actions;
    private Map<String, Action> anyActions;
    
    private Entity wfEntity;
    private State startState;

    public Workflow(Entity wfEntity) {
        this.wfEntity = wfEntity;
        
        this.states     = new HashMap<String, State>();
        this.actions    = new HashMap<>();
        this.anyActions = new HashMap<>();
        
        this.addState(new AnyState(this));
        this.addState(new SelfState(this));
    }
    
    public String getName() {
        return this.wfEntity.getStringValue("name");
    }
    
    public void addState(State state) {
        this.states.put(state.getName(), state);
        
        if (state.isStart()) {
            this.startState = state;
        }
        
    }

    public State getState(String name) {
        return this.states.get(name);
    }

    public void addAction(Action action) {
        this.actions.put(action.getName(), action);
        
        if (action.getFrom().equals(new AnyState(this))) {
            this.anyActions.put(action.getName(), action);
        }
    }

    public State getStartState() {
        return this.startState;
    }

    public Map<String, Action> getAnyActions() {
        return this.anyActions;
    }

    public Entity prepare(Entity e) {
        e.setState(getStartState());
        return e;
    }

    @Override
    public String toString() {
        return "Workflow [name=" + getName() + "]";
    }
}
