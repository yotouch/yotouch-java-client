package com.yotouch.entity;

import org.json.JSONObject;

public class MetaField {
    
    public static final FieldType TYPE_STRING     = new FieldType("string");
    public static final FieldType TYPE_TEXT       = new FieldType("text");
    public static final FieldType TYPE_SINGLE_REF = new FieldType("single_ref");
    public static final FieldType TYPE_DATETIME   = new FieldType("datetime");
    public static final FieldType TYPE_DATE       = new FieldType("date");
    
    private String name;
    private FieldType type;

    private MetaEntity metaEntity;
    
    public MetaField(String name, FieldType type) {
        this.name = name;
        this.type = type;
    }
    
    public void setMetaEntity(MetaEntity metaEntity) {
        this.metaEntity = metaEntity;
    }

    public String getName() {
        return this.name;
    }
    
    public FieldType getType() {
        return this.type;
    }
    
    public MetaEntity getMetaEntity() {
        return this.metaEntity;
    }

    public static MetaField fromJsonObject(JSONObject fieldObj) {
        String name = fieldObj.getString("name");
        String type = fieldObj.getString("type");
        
        MetaField mf = new MetaField(name, new FieldType(type));
        return mf;
    }

}


class FieldType {
    
    String type;
    
    FieldType(String type) {
        this.type = type;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((type == null) ? 0 : type.hashCode());
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
        FieldType other = (FieldType) obj;
        if (type == null) {
            if (other.type != null)
                return false;
        } else if (!type.equals(other.type))
            return false;
        return true;
    }
    
}
