package com.yotouch.config;

import com.yotouch.entity.EntityCollection;

/**
 * Created by yinwm on 5/10/15.
 */
public class ConfigManager {

    private String ytUrl = null;
    private String companyId = null;
    private String companyName = null;

    public ConfigManager(String yotouchUrl) {
        this.ytUrl = yotouchUrl;
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
        String comp = this.companyId;
        if (comp == null || comp.equals("")) {
            comp = this.companyName;
        }
        
        return new EntityCollection(collName, comp);
    }



}
