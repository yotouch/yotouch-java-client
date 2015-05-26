package com.yotouch.workflow;

import java.util.Map;

import com.yotouch.Constants;
import com.yotouch.YotouchException;
import com.yotouch.entity.Entity;

public class State {
    
    private Workflow wf;
    private Entity stateEntity;
    private Map<String, Action> actions;
    private Map<String, Action> inActions;
    private Map<String, Action> outActions;
    

    public State(Workflow wf, Entity stateEntity) {
        this.wf = wf;
        this.stateEntity = stateEntity;
    }

    public String getName() {
        return this.stateEntity.getStringValue("name");
    }
    
    public boolean isStart() {
        return Constants.WF_STATE_TYPE_START.equals(this.stateEntity.getStringValue("type"));
    }
    
    public void addInAction(Action action) {
        this.actions.put(action.getName(), action);
        this.inActions.put(action.getName(), action); 
    }

    public void addOutAction(Action action) {
        this.actions.put(action.getName(), action);
        this.outActions.put(action.getName(), action);        
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.getName() == null) ? 0 : this.getName().hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        State other = (State) obj;
        if (this.getName() == null) {
            if (other.getName() != null)
                return false;
        } else if (!this.getName().equals(other.getName()))
            return false;
        return true;
    }

}



class AnyState extends State {

    public AnyState(Workflow wf) {
        super(wf, null);
    }
    
    @Override
    public String getName() {
        return Constants.WF_STATE_ANY;
    }
    
    @Override
    public boolean isStart() {
        return false;
    }
    
    @Override
    public void addOutAction(Action action) {
        throw new YotouchException("Any state cannot add out action");
    }
    
}

class SelfState extends State {

    public SelfState(Workflow wf) {
        super(wf, null);
    }
    
    @Override
    public String getName() {
        return Constants.WF_STATE_SELF;
    }
    
}


