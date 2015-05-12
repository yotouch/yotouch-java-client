package com.yotouch.entity;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

public class Entity {
    
    private MetaEntity metaEntity;
    private Map<String, Object> values;
    
    public Entity(MetaEntity metaEntity) {
        this.metaEntity = metaEntity;
        this.values = new HashMap<String, Object>();
    }
    
    public MetaEntity getMetaEntity() {
        return this.metaEntity;
    }
    
    public void setValue(String name, Object v) {
        // Don't check if the name in fields
        this.values.put(name, v);
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

    public static Entity fromJsonObject(MetaEntity me, JSONObject jsonObject) {
        Entity entity = new Entity(me);
        
        for (MetaField mf : me.getFields()) {
            String fname = mf.getName();
            Object value = null;
            if (jsonObject.has(fname)) {
                value = jsonObject.get(fname);
            }
            entity.setValue(fname, value);
        }
        
        return entity;
    }
    
    @Override
    public String toString() {
        return "[Entity] " + this.getMetaEntity().getName() + "=>" + this.getUUID();
    }

}
