package com.yotouch.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yotouch.config.ConfigManager;
import com.yotouch.network.GetClient;
import com.yotouch.network.PostClient;

public class EntityCollection {
    
    private MetaEntity metaEntity;
    private ConfigManager cfgMgr;
    
    public EntityCollection(ConfigManager cfgMgr, MetaEntity metaEntity) {
        this.cfgMgr = cfgMgr;
        this.metaEntity = metaEntity;
    }

    public Entity get(String idOrName) {
        GetClient gClient = cfgMgr.getGetClient();
        Entity entity = gClient.doGetEntity(this.metaEntity.getName(), idOrName);
        return entity;
    }

    public Entity save(Entity entity) {
        PostClient pClient = cfgMgr.getPostClient();
        String uri = "/entity/save/" + this.metaEntity.getName() + "/" + this.cfgMgr.getCompanyId();
        return pClient.doPostEntity(uri, entity);
    }

    public List<Entity> find() {
        GetClient gClient = cfgMgr.getGetClient();
        String uri = "/entity/find/" + this.metaEntity.getName() + "/" + this.cfgMgr.getCompanyId();
        List<Entity> entityList = gClient.doGetEntityList(uri);
        return entityList;
    }
    
    
    public Entity newOne() {
        return this.newOne(new HashMap<String, Object>());
    }
    
    public Entity newOne(Map<String, Object> d) {
        Entity e = new Entity(this.metaEntity);
        
        for (MetaField mf : this.metaEntity.getFields()) {
            String fname = mf.getName();
            Object value = null;
            if (d.containsKey(fname)) {
                value = d.get(fname);
            }
            e.setValue(fname, value);
        }
        
        return e;
    }

}
