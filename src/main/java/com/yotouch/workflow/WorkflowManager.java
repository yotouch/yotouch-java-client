package com.yotouch.workflow;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.yotouch.config.ConfigManager;
import com.yotouch.entity.Entity;
import com.yotouch.entity.MetaEntity;
import com.yotouch.network.GetClient;

public class WorkflowManager {
    
    private ConfigManager cfgMgr;
    
    private Map<String, Workflow> wfMap;
    
    public WorkflowManager(ConfigManager cfgMgr) {
        this.cfgMgr = cfgMgr;
        this.wfMap = new HashMap<String, Workflow>();
    }
    
    public Workflow getWorkflow(String name) {
        if (this.wfMap.containsKey(name)) {
            return this.wfMap.get(name);
        } 
        
        GetClient gClient = this.cfgMgr.getGetClient();
        
        String uri = "/workflow/get/" + name;
        JSONObject obj = gClient.doGet(uri);
        System.out.println(obj);
        
        JSONObject wfObj = obj.getJSONObject("result").getJSONObject("workflow");
        MetaEntity wfMe  = MetaEntity.fromJsonObject(wfObj.getJSONObject("meta_entity"));
        Entity entity    = Entity.fromJsonObject(wfMe, wfObj.getJSONObject("entity"));
        
        Workflow wf = new Workflow(this, entity);
        this.addWorkflow(wf);
        
        return this.wfMap.get(name);
    }
    
    public void addWorkflow(Workflow wf) {
        this.wfMap.put(wf.getName(), wf);
    }

}
