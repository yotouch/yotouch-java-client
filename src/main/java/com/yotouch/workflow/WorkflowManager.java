package com.yotouch.workflow;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
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
        
        JSONObject wfObj = obj.getJSONObject("result").getJSONObject("workflow");
        MetaEntity wfMe  = MetaEntity.fromJsonObject(wfObj.getJSONObject("meta_entity"));
        Entity entity    = Entity.fromJsonObject(this.cfgMgr.getWorkflowManager(), wfMe, wfObj.getJSONObject("entity"));
        Workflow wf = new Workflow(entity);
        
        uri = "/workflow/get_states/" + name;
        obj = gClient.doGet(uri);
        
        JSONObject stateObj = obj.getJSONObject("result").getJSONObject("states");
        MetaEntity stateMe  = MetaEntity.fromJsonObject(stateObj.getJSONObject("meta_entity"));
        JSONArray entityArray = stateObj.getJSONArray("entities");
        List<Entity> l = Entity.fromJsonArray(this.cfgMgr.getWorkflowManager(), stateMe, entityArray);
        
        for (Entity e: l) {
            wf.addState(State.create(wf, e));
        }
        
        uri = "/workflow/get_actions/" + name;
        obj = gClient.doGet(uri);
        
        JSONObject actionObj = obj.getJSONObject("result").getJSONObject("actions");
        MetaEntity actionMe  = MetaEntity.fromJsonObject(actionObj.getJSONObject("meta_entity"));
        entityArray = actionObj.getJSONArray("entities");
        List<Entity> actions = Entity.fromJsonArray(this.cfgMgr.getWorkflowManager(), actionMe, entityArray);
        
        for (Entity a: actions) {
            wf.addAction(Action.create(wf, a));
        }
        
        this.addWorkflow(wf);
        
        return this.wfMap.get(name);
    }
    
    public void addWorkflow(Workflow wf) {
        this.wfMap.put(wf.getName(), wf);
    }

}
