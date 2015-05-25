package com.yotouch.config;

import com.yotouch.network.PostClient;
import com.yotouch.network.GetClient;
import com.yotouch.entity.Entity;
import com.yotouch.entity.EntityCollection;
import com.yotouch.entity.MetaEntity;
import com.yotouch.entity.MetaEntityManager;

/**
 * Created by yinwm on 5/10/15.
 */
public class ConfigManager {

    //private String ytUrl;
    private String companyId;
    private String companyName;
    private Entity companyEntity;
    private GetClient getClient;
    private PostClient postClient;
    private MetaEntityManager metaEntityManager;

    public ConfigManager(String yotouchUrl, String company) {
        //this.ytUrl = yotouchUrl;
        this.getClient = new GetClient(yotouchUrl);
        this.postClient = new PostClient(yotouchUrl);
        
        String uri = "/company/get/" + company;
        this.companyEntity = this.getClient.doGetEntity(uri);
        this.companyId = this.companyEntity.getUUID();
        
        this.metaEntityManager = new MetaEntityManager(this);
    }

    public String getCompanyId() {
        return companyId;
    }

    public String getCompanyName() {
        return this.companyName;
    }
    
    public EntityCollection getEntityCollection(String collName) {
        MetaEntity me = this.metaEntityManager.getMetaEntity(collName);
        return new EntityCollection(this, me);
    }

    public GetClient getGetClient() {
        return this.getClient;
    }

    public PostClient getPostClient() {
        return this.postClient;
    }

}
