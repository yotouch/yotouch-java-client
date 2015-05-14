package com.yotouch.config;

import com.yotouch.network.PostClient;
import com.yotouch.network.GetClient;

import com.yotouch.entity.Entity;
import com.yotouch.entity.EntityCollection;

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

    public ConfigManager(String yotouchUrl) {
        //this.ytUrl = yotouchUrl;
        this.getClient = new GetClient(yotouchUrl);
        this.postClient = new PostClient(yotouchUrl);
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return this.companyName;
    }
    
    public EntityCollection getEntityCollection(String collName) {
        if (this.companyId == null || this.companyId.equals("")) {
            String uri = "/company/get/" + this.companyName;
            this.companyEntity = this.getClient.doGetEntity(uri);
            this.companyId = this.companyEntity.getUUID();
        }
        
        return new EntityCollection(this, collName, this.getCompanyId());
    }

    public GetClient getGetClient() {
        return this.getClient;
    }

    public PostClient getPostClient() {
        return this.postClient;
    }

}
