package com.yotouch.entity;

public class MetaField {
    
    public static final FieldType TYPE_STRING = new FieldType("string");
    
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
