package com.yotouch.entity;

import com.yotouch.config.ConfigManager;
import com.yotouch.network.GetClient;
import com.yotouch.network.PostClient;

public class EntityCollection {
    
    private String entityName;
    private String companyId;
    private ConfigManager cfgMgr;
    
    public EntityCollection(ConfigManager cfgMgr, String collName) {
        this(cfgMgr, collName, null);
    }

    public EntityCollection(ConfigManager cfgMgr, String entityName, String comp) {
        this.cfgMgr = cfgMgr;
        this.entityName = entityName;
        this.companyId = comp;
    }

    public Entity get(String idOrName) {
        GetClient gClient = cfgMgr.getGetClient();
        
        String uri = "/entity/get/" + this.entityName + "/" + this.companyId + "/" + idOrName;
        Entity entity = gClient.doGetEntity(uri);
        
        return entity;
    }

    public Entity save(Entity entity) {
        
        PostClient pClient = cfgMgr.getPostClient();
        
        String uri = "/entity/save/" + this.entityName + "/" + this.companyId;
        return pClient.doPostEntity(uri, entity);
    }

}
