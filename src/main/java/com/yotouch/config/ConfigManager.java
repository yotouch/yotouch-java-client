package com.yotouch.config;

import com.yotouch.network.PostClient;
import com.yotouch.network.GetClient;
import com.yotouch.workflow.Workflow;
import com.yotouch.workflow.WorkflowManager;
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
    private WorkflowManager wfMgr;

    public ConfigManager(String yotouchUrl, String company) {
        //this.ytUrl = yotouchUrl;
        GetClient gc = new GetClient(yotouchUrl, null);
        
        
        String uri = "/company/get/" + company;
        this.companyEntity = gc.doGetEntity(uri);
        this.companyId = this.companyEntity.getUUID();
        
        this.getClient  = new GetClient(yotouchUrl, this.companyId);
        this.postClient = new PostClient(yotouchUrl);
        this.metaEntityManager = new MetaEntityManager(this);
        this.wfMgr = new WorkflowManager(this);
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

    public Workflow getWorkflow(String name) {
        return this.wfMgr.getWorkflow(name);
    }

}
