package com.yutongdxTop.LaborDispatching.controller;

import com.yutongdxTop.LaborDispatching.domain.vo.ContactVo;
import com.yutongdxTop.LaborDispatching.domain.vo.FreeTimeVo;
import com.yutongdxTop.LaborDispatching.service.AdministratorService;
import com.yutongdxTop.LaborDispatching.util.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/LaborDispatching/administrator")
public class AdministratorController {

    @Autowired
    private AdministratorService administratorService;

    @RequestMapping(value = "/getAllFreeTimeVos")
    public JSONResult getAllFreeTimes(@RequestParam(value = "name",  defaultValue = "all") String name) {
        List<FreeTimeVo> freeTimeVos;
        if (Objects.equals(name, "all")) {
            freeTimeVos = administratorService.getAllFreeTimeVos();
        } else {
            freeTimeVos = administratorService.getFreeTimeVoByNameLike(name);
        }
        if (freeTimeVos.isEmpty()) {
            return JSONResult.errorMsg("还没有时间表");
        } else {
            int count = freeTimeVos.size();
            return JSONResult.ok(count, freeTimeVos);
        }
    }

    @RequestMapping("/deleteFreeTimeVo")
    public JSONResult deleteFreeTime(@RequestParam(value = "id", required = true, defaultValue = "-1") String id) {
        String msg = administratorService.deleteFreeTimeVo(id);

        if (msg.contains("成功")) {
            return JSONResult.ok(msg);
        } else {
            return JSONResult.errorMsg(msg);
        }
    }

    @RequestMapping("/updateFreeTimeVo")
    public JSONResult updateFreeTime(@ModelAttribute FreeTimeVo freeTimeVo) {
        String msg = administratorService.updateFreeTimeVo(freeTimeVo);
        if (msg != null) {
            return JSONResult.ok(msg);
        } else {
            return JSONResult.errorMsg("控制层出现问题");
        }
    }

    @RequestMapping(value = "/addFreeTimeVo")
    public JSONResult addFreeTime(@ModelAttribute FreeTimeVo freeTimeVo) {
        String msg = administratorService.addFreeTimeVo(freeTimeVo);
        if (msg.contains("成功")) {
            return JSONResult.ok(msg);
        } else {
            return JSONResult.errorMsg(msg);
        }
    }

    @RequestMapping(value = "/getAllContactVos")
    public JSONResult getAllContactVos(@RequestParam(value = "name",  defaultValue = "all") String name) {
        List<ContactVo> contactVos;
        if (Objects.equals(name, "all")) {
            contactVos = administratorService.getAllContactVos();  //获取所有员工联系方式
        } else {
            contactVos = administratorService.getContactVoByNameLike(name);
        }
        if (contactVos.isEmpty()) {
            return JSONResult.errorMsg("还没有联系方式");
        } else {
            int count = contactVos.size();
            return JSONResult.ok(count, contactVos);
        }
    }

    @RequestMapping("/deleteContactVo")
    public JSONResult deleteContactVo(@RequestParam(value = "id", required = true, defaultValue = "-1") String id) {
        String msg = administratorService.deleteContactVo(id);

        if (msg.contains("成功")) {
            return JSONResult.ok(msg);
        } else {
            return JSONResult.errorMsg(msg);
        }
    }

    @RequestMapping("/updateContactVo")
    public JSONResult updateContactVo(@ModelAttribute ContactVo contactVo) {
        String msg = administratorService.updateContactVo(contactVo);
        if (msg != null) {
            return JSONResult.ok(msg);
        } else {
            return JSONResult.errorMsg("控制层出现问题");
        }
    }

    @RequestMapping(value = "/addContactVo")
    public JSONResult addContactVo(@ModelAttribute ContactVo contactVo) {
        String msg = administratorService.addContactVo(contactVo);
        if (msg.contains("成功")) {
            return JSONResult.ok(msg);
        } else {
            return JSONResult.errorMsg(msg);
        }
    }

}
