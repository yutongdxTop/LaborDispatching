package com.yutongdxTop.LaborDispatching.controller;

import com.yutongdxTop.LaborDispatching.domain.pojo.Contact;
import com.yutongdxTop.LaborDispatching.domain.pojo.FreeTime;
import com.yutongdxTop.LaborDispatching.service.FreelancerService;
import com.yutongdxTop.LaborDispatching.util.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/LaborDispatching/freelancer")
public class FreelancerController {
    @Autowired
    private FreelancerService freelancerService;

    @RequestMapping(value = "/getAllFreeTimes")
    @ResponseBody
    public JSONResult getAllFreeTimes(@RequestParam(value = "id", defaultValue = "-1") String staffId) {
        List<FreeTime> freeTimes;
        freeTimes = freelancerService.getAllFreeTimes(staffId);  //获取员工所有时间表
        if (freeTimes.isEmpty()) {
            return JSONResult.errorMsg("还没有时间表");
        } else {
            int count = freeTimes.size();
            return JSONResult.ok(count, freeTimes);
        }
    }

    @RequestMapping("/deleteFreeTime")
    @ResponseBody
    public JSONResult deleteFreeTime(@RequestParam(value = "id", required = true, defaultValue = "-1") String id) {
        String msg = freelancerService.deleteFreeTime(id);

        if (msg.contains("成功")) {
            return JSONResult.ok(msg);
        } else {
            return JSONResult.errorMsg(msg);
        }
    }

    @RequestMapping("/updateFreeTime")
    @ResponseBody
    public JSONResult updateFreeTime(@ModelAttribute FreeTime freeTime) {
        String msg = freelancerService.updateFreeTime(freeTime);
        if (msg != null) {
            return JSONResult.ok(msg);
        } else {
            return JSONResult.errorMsg("控制层出现问题");
        }
    }

    @RequestMapping(value = "/addFreeTime")
    @ResponseBody
    public JSONResult addFreeTime(@ModelAttribute FreeTime freeTime) {
        System.out.println(freeTime);
        String msg = freelancerService.addFreeTime(freeTime);
        if (msg.contains("成功")) {
            return JSONResult.ok(msg);
        } else {
            return JSONResult.errorMsg(msg);
        }
    }

    @RequestMapping(value = "/getAllContacts")
    @ResponseBody
    public JSONResult getAllContacts(@RequestParam(value = "id", defaultValue = "-1") String staffId) {
        List<Contact> contacts;
        contacts = freelancerService.getAllContacts(staffId);  //获取员工所有联系方式
        if (contacts.isEmpty()) {
            return JSONResult.errorMsg("还没有联系方式");
        } else {
            int count = contacts.size();
            return JSONResult.ok(count, contacts);
        }
    }

    @RequestMapping("/deleteContact")
    @ResponseBody
    public JSONResult deleteContact(@RequestParam(value = "id", required = true, defaultValue = "-1") String id) {
        String msg = freelancerService.deleteContact(id);

        if (msg.contains("成功")) {
            return JSONResult.ok(msg);
        } else {
            return JSONResult.errorMsg(msg);
        }
    }

    @RequestMapping("/updateContact")
    @ResponseBody
    public JSONResult updateContact(@ModelAttribute Contact contact) {
        String msg = freelancerService.updateContact(contact);
        if (msg != null) {
            return JSONResult.ok(msg);
        } else {
            return JSONResult.errorMsg("控制层出现问题");
        }
    }

    @RequestMapping(value = "/addContact")
    @ResponseBody
    public JSONResult addContact(@ModelAttribute Contact contact) {
        System.out.println(contact);
        String msg = freelancerService.addContact(contact);
        if (msg.contains("成功")) {
            return JSONResult.ok(msg);
        } else {
            return JSONResult.errorMsg(msg);
        }
    }
}
