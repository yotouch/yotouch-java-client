package com.yotouch.example;

import com.yotouch.config.ConfigManager;
import com.yotouch.entity.Entity;
import com.yotouch.entity.EntityCollection;

/**
 * Created by yinwm on 5/10/15.
 */
public class Main {

    public static void main(String[] args) {

        ConfigManager cfgMgr = new ConfigManager("http://wedding.taomovie.cn/p/api/v1");

        String companyName = "wedding";
        cfgMgr.setCompanyName(companyName);

        EntityCollection staffColl = cfgMgr.getEntityCollection("staff");
        Entity staff = staffColl.get("admin");
        System.out.println("Entity staff " + staff);

    }

}
