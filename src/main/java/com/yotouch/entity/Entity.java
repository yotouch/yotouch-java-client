package com.yotouch.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.yotouch.Constants;
import com.yotouch.workflow.State;
import com.yotouch.workflow.Workflow;
import com.yotouch.workflow.WorkflowManager;

public class Entity {
    
    private static final SimpleDateFormat dtFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd");
    
    private MetaEntity metaEntity;
    private Map<String, Object> values;
    private State state;
    
    public Entity(MetaEntity metaEntity) {
        this.metaEntity = metaEntity;
        this.values = new HashMap<String, Object>();
        this.state = null;
    }
    
    public MetaEntity getMetaEntity() {
        return this.metaEntity;
    }
    
    public void setValue(String name, Object v) {
        // Don't check if the name in fields
        this.values.put(name, v);
    }
    
    public boolean isSetValue(String name) {
        return this.values.containsKey(name);
    }
    
    public String getId() {
        return this.getStringValue("_id");
    }
    
    public String getUUID() {
        return this.getId();
    }
    
    public Object getValue(String name) {
        return this.values.get(name);
    }
    
    public String getStringValue(String name) {
        return (String) this.getValue(name);
    }
    
    public static List<Entity> fromJsonArray(WorkflowManager wfMgr, MetaEntity me, JSONArray entityArray) {
        List<Entity> l = new ArrayList<>();
        for (int i = 0; i < entityArray.length(); i++) {
            Entity entity = Entity.fromJsonObject(wfMgr, me, entityArray.getJSONObject(i));
            l.add(entity);
        }
        
        return l;
    }

    public static Entity fromJsonObject(WorkflowManager wfMgr, MetaEntity me, JSONObject jsonObject) {
        Entity entity = new Entity(me);
        
        for (MetaField mf : me.getFields()) {
            String fname = mf.getName();
            Object value = null;
            if (jsonObject.has(fname)) {
                value = jsonObject.get(fname);
            }
            
            if (value == null || JSONObject.NULL.equals(value)) {
                continue;
            }
            
            FieldType ft = mf.getType();
            if (MetaField.TYPE_SINGLE_REF.equals(ft)) {
                
            } else if (MetaField.TYPE_DATE.equals(ft)) {
                try {
                    value = dFormat.parse((String) value);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } else if (MetaField.TYPE_DATETIME.equals(ft)) {
                try {
                    value = dtFormat.parse((String) value);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            
            entity.setValue(fname, value);
        }
        
        if (jsonObject.has(Constants.FIELD_NAME_STATE)
                && jsonObject.get(Constants.FIELD_NAME_STATE) != null
                && !JSONObject.NULL.equals(jsonObject.get(Constants.FIELD_NAME_STATE))) {
            Workflow wf = wfMgr.getWorkflow(jsonObject.getString(Constants.FIELD_NAME_WORKFLOW));
            entity.setState(wf.getState(jsonObject.getString(Constants.FIELD_NAME_STATE)));
        }
        
        return entity;
    }
    
    @Override
    public String toString() {
        return "[Entity] " + this.getMetaEntity().getName() + "=>" + this.getUUID();
    }

    public String asJSON() {
        JSONObject valueJson = new JSONObject();
        for (MetaField mf : this.getMetaEntity().getFields()) {
            String fname = mf.getName();
            if (this.isSetValue(fname)) {
                Object v = this.getValue(fname);
                
                if (v == null) {
                    continue;
                }
                
                FieldType ft = mf.getType();
                
                if (MetaField.TYPE_SINGLE_REF.equals(ft)) {
                    v = ((Entity)v).getUUID();
                } else if (MetaField.TYPE_DATETIME.equals(ft)) {
                    v = dtFormat.format(v);
                } else if (mf.getType().equals(Constants.FIELD_TYPE_DATE)) {
                    v = dFormat.format(v);
                }
                
                valueJson.put(fname, v);
            }
        }
        
        if (this.state != null) {
            valueJson.put(Constants.FIELD_NAME_WORKFLOW, this.state.getWorkflow().getName());
            valueJson.put(Constants.FIELD_NAME_STATE, this.state.getName());
        }
        
        return valueJson.toString();
    }

    public void setState(State s) {
        this.state = s;        
    }

    public State getState() {
        return this.state;
    }

}
