package com.yotouch.example;

import com.yotouch.config.ConfigManager;
import com.yotouch.entity.Entity;
import com.yotouch.entity.EntityCollection;

/**
 * Created by yinwm on 5/10/15.
 */
public class Main {

    public static void main(String[] args) {

        ConfigManager cfgMgr = new ConfigManager("http://meiliaoshi.yotouch.com/api/v1");

        String companyName = "meiliaoshi";
        cfgMgr.setCompanyName(companyName);

        EntityCollection staffColl = cfgMgr.getEntityCollection("staff");
        Entity staff = staffColl.get("admin");
        System.out.println("Entity staff " + staff);

        System.out.println("fullname " + staff.getStringValue("fullname"));
        
        staff.setValue("fullname", "管理");
        Entity savedStaff = staffColl.save(staff);

    }

}
