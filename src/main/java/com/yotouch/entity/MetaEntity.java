package com.yotouch.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

}
