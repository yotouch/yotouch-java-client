package com.yotouch.entity;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.yotouch.config.ConfigManager;
import com.yotouch.network.GetClient;

public class MetaEntityManager {
    
    private ConfigManager cfgMgr;
    
    private Map<String, MetaEntity> metaEntityMap;

    public MetaEntityManager(ConfigManager configManager) {
        this.cfgMgr = configManager;
        this.metaEntityMap = new HashMap<String, MetaEntity>();
    }
    
    public MetaEntity getMetaEntity(String name) {
        
        if (this.metaEntityMap.containsKey(name)) {
            return this.metaEntityMap.get(name);
        }
        
        
        GetClient gClient = cfgMgr.getGetClient();
        String uri = "/meta_entity/get/" + name + "/" + this.cfgMgr.getCompanyId();
        
        JSONObject jsonObj = gClient.doGet(uri);
        JSONObject resultObj = jsonObj.getJSONObject("result");
        MetaEntity metaEntity = MetaEntity.fromJsonObject(resultObj.getJSONObject("meta_entity"));
        
        this.metaEntityMap.put(name, metaEntity);
        return metaEntity;
    }
    
}
