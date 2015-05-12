package com.yotouch.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class MetaEntity {
    
    private String uuid;
    private String name;
    private Map<String, MetaField> fieldMap;
    
    public MetaEntity(String uuid, String name) {
        this.uuid = uuid;
        this.name = name;
        this.fieldMap = new HashMap<String, MetaField>();
    }
    
    public List<MetaField> getFields() {
        return new ArrayList<MetaField>(this.fieldMap.values());
    }
    
    public void addField(MetaField field) {
        this.fieldMap.put(field.getName(), field);
        field.setMetaEntity(this);
    }

    public static MetaEntity fromJsonObject(JSONObject jsonObj) {
        String name = jsonObj.getString("name");
        String uuid = jsonObj.getString("uuid");
        MetaEntity me = new MetaEntity(uuid, name);
        
        
        JSONArray fields = jsonObj.getJSONArray("fields");
        for (int i = 0; i < fields.length(); i++) {
            JSONObject fieldObj = fields.getJSONObject(i);
            MetaField mf = MetaField.fromJsonObject(fieldObj);
            me.addField(mf);
        }
        
        return me; 
    }

    public String getName() {
        return this.name;
    }
    
    public String getUUID() {
        return this.uuid;
    }

}
