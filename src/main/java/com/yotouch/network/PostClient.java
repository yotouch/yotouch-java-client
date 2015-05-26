package com.yotouch.network;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import com.yotouch.entity.Entity;
import com.yotouch.entity.MetaEntity;
import com.yotouch.workflow.WorkflowManager;

public class PostClient {
    
    private String ytUrl;
    private WorkflowManager wfMgr;

    public PostClient(WorkflowManager wfMgr, String ytUrl) {
        this.wfMgr = wfMgr;
        this.ytUrl = ytUrl;
    }
    
    public JSONObject doPost(String uri, String postBody) {
        
        String fullUrl = this.ytUrl + uri;
        
        CloseableHttpClient httpClient = HttpClients.createDefault();

        try {
            HttpPost post = new HttpPost(fullUrl);
            
            StringEntity input = new StringEntity(postBody, "UTF-8");
            input.setContentEncoding("utf8");
            input.setContentType("application/json");
            post.setEntity(input);
            
            System.out.println("postbody " + postBody);


            CloseableHttpResponse httpResp = httpClient.execute(post);
            HttpEntity httpEntity = httpResp.getEntity();
            String body = EntityUtils.toString(httpEntity, "utf8");
            
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
    
    public Entity doPostEntity(String uri, Entity entity) {
        JSONObject jsonObj = this.doPost(uri, entity.asJSON());
        JSONObject resultObj = jsonObj.getJSONObject("result");

        MetaEntity metaEntity = MetaEntity.fromJsonObject(resultObj.getJSONObject("meta_entity"));
        Entity savedEntity = Entity.fromJsonObject(this.wfMgr, metaEntity, resultObj.getJSONObject("entity"));
        
        return savedEntity;        
    }

}
