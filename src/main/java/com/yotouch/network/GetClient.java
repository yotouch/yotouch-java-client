package com.yotouch.network;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.yotouch.entity.Entity;
import com.yotouch.entity.MetaEntity;
import com.yotouch.workflow.WorkflowManager;

public class GetClient {
    
    private String ytUrl;
    private String companyId;
    private WorkflowManager wfMgr;
    
    public GetClient(WorkflowManager wfMgr, String ytUrl, String companyId) {
        this.wfMgr = wfMgr;
        this.ytUrl = ytUrl;
        this.companyId = companyId;
    }
    
    public JSONObject doGet(String uri) {
        String fullUrl = this.ytUrl + uri;
        
        //System.out.println("Do get " + fullUrl);
        
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            HttpGet httpGet = new HttpGet(fullUrl);
            CloseableHttpResponse httpResp = httpClient.execute(httpGet);
            HttpEntity httpEntity = httpResp.getEntity();
            String body = EntityUtils.toString(httpEntity, "utf8");
            //System.out.println("response " + body);
            
            JSONObject obj = new JSONObject(body);
            return obj;
            
        } catch (IOException e) {
            
            e.printStackTrace();
            return null;
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public Entity doGetEntity(String uri) {
        System.out.println("GEt entity " + uri);
        JSONObject jsonObj = this.doGet(uri);
        JSONObject resultObj = jsonObj.getJSONObject("result");

        MetaEntity metaEntity = MetaEntity.fromJsonObject(resultObj.getJSONObject("meta_entity"));
        Entity entity = Entity.fromJsonObject(wfMgr, metaEntity, resultObj.getJSONObject("entity"));
        
        return entity;        
    }

    public List<Entity> doGetEntityList(String uri) {
        JSONObject jsonObj = this.doGet(uri);
        JSONObject resultObj = jsonObj.getJSONObject("result");

        MetaEntity metaEntity = MetaEntity.fromJsonObject(resultObj.getJSONObject("meta_entity"));
        
        JSONArray entityArray = resultObj.getJSONArray("entities");
        List<Entity> l = Entity.fromJsonArray(this.wfMgr, metaEntity, entityArray);
        return l;
    }

    public Entity doGetEntity(String entityName, String idOrName) {
        String uri = "/entity/get/" + entityName + "/" + this.companyId + "/" + idOrName;
        Entity entity = this.doGetEntity(uri);
        return entity;
    }
    
}
