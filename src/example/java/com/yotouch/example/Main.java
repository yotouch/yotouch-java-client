package com.yotouch.example;

import java.util.Date;
import java.util.List;

import com.yotouch.config.ConfigManager;
import com.yotouch.entity.Entity;
import com.yotouch.entity.EntityCollection;
import com.yotouch.workflow.Action;
import com.yotouch.workflow.State;
import com.yotouch.workflow.Workflow;

/**
 * Created by yinwm on 5/10/15.
 */
public class Main {

    public static void main(String[] args) {

        String companyName = "meiliaoshi";
        ConfigManager cfgMgr = new ConfigManager("http://meiliaoshi.yotouch.com/api/v1", companyName);


        EntityCollection staffColl = cfgMgr.getEntityCollection("staff");
        Entity staff = staffColl.get("admin");
        System.out.println("Entity staff " + staff);

        System.out.println("fullname " + staff.getStringValue("fullname") + " " + staff.getValue("created_at"));
        
        staff.setValue("fullname", "管理");
        Entity savedStaff = staffColl.save(staff);
        
        EntityCollection orderColl = cfgMgr.getEntityCollection("order");
        List<Entity> orders = orderColl.find();
        System.out.println("orders " + orders);
        
        
        Entity order = orderColl.newOne();
        order.setValue("created_at", new Date());
        order = orderColl.save(order);
        System.out.println("new uuid " + order.getUUID());
        
        
        Workflow wf = cfgMgr.getWorkflowManager().getWorkflow("order");
        System.out.println("Workflow " + wf);
        
        State startState = wf.getStartState();
        System.out.println(startState.getName());
        System.out.println(startState.getOutActions());
        
        Action start = startState.getOutAction("start");
        order.setValue("address", "望京");
        start.act(order);
        
        System.out.println("After start order " + order.getValue("created_at"));
        
        order = orderColl.save(order);
        
        State s = order.getState();
        System.out.println("New saved state " + s.getName());
        System.out.println("New saved state actions " + s.getOutActions());
        
        
        order = orderColl.newOne();
        order.setValue("address", "爱到底");
        order = wf.prepare(order);
        start = order.getState().getOutAction("start");
        order = start.act(order);
        
        Action process = order.getState().getOutAction("process");
        order = process.act(order);
        order = orderColl.save(order);
        System.out.println("order id " + order.getUUID());
        System.out.println("order state " + order.getState());
        
        Entity newOrder = orderColl.get(order.getUUID());
        System.out.println("new order id " + newOrder.getUUID());
        System.out.println("new order state " + newOrder.getState());
        
        Action edit = newOrder.getState().getOutAction("edit");
        newOrder.setValue("address", "ad");
        newOrder = edit.act(newOrder);
        newOrder = orderColl.save(newOrder);
        
        
        EntityCollection cusColl = cfgMgr.getEntityCollection("customer");
        Entity customer = cusColl.newOne();
        customer.setValue("fullname", "张三");

        //这步是必须的，因为还没有 customer 的 uuid
        customer = cusColl.save(customer);
        
        newOrder.setValue("customer", customer);
        newOrder = orderColl.save(newOrder);
        
        newOrder = orderColl.get(newOrder.getUUID());
        
        System.out.println("Customer " + newOrder.getValue("customer"));
        
        
        
    }

}
