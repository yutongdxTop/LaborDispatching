package com.yutongdxTop.LaborDispatching.controller;

import com.yutongdxTop.LaborDispatching.domain.vo.StaffVo;
import com.yutongdxTop.LaborDispatching.service.StaffVoService;
import com.yutongdxTop.LaborDispatching.util.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/LaborDispatching/staffVo")
public class StaffVoController {
    @Autowired
    private StaffVoService staffVoService;

    @RequestMapping(value = "/getAllStaffVos")
    @ResponseBody
    public JSONResult getAllStaffVos(@RequestParam(value = "type",  defaultValue = "all") String type) {
        List<StaffVo> staffVos;
        if (Objects.equals(type, "all")) {
            staffVos = staffVoService.getAllStaffVos();
        } else {
            staffVos = staffVoService.getStaffVoByTypeLike(type);
        }
        if (staffVos.isEmpty()) {
            return JSONResult.errorMsg("还没有自由职业者信息");
        } else {
            int count = staffVos.size();
            return JSONResult.ok(count, staffVos);
        }
    }

    @RequestMapping("/getStaffVoByUserName")
    @ResponseBody
    public JSONResult getStaffVoByUserName(@RequestParam(value = "userName", required = true, defaultValue = "-1") String userName) {
        StaffVo staffVo = staffVoService.getStaffVoByUserName(userName);
        System.out.println("getStaffVo:" + userName);
        if (staffVo == null) {
            return JSONResult.errorMsg("找不到这个用户");
        } else {
            return JSONResult.ok(staffVo);
        }
    }

    @RequestMapping("/updateStaffVo")
    @ResponseBody
    public JSONResult updateStaffVo(@ModelAttribute StaffVo staffVo) {
        System.out.println(staffVo.getStaffId());
        String msg = staffVoService.updateStaffVo(staffVo);
        if (msg!=null){
            return JSONResult.ok(msg);
        }else{
            return JSONResult.errorMsg("控制层出现问题");
        }
    }

    @RequestMapping("/deleteFreelancer")
    @ResponseBody
    public JSONResult deleteFreelancer(@RequestParam(value = "staffId", required = true, defaultValue = "-1") String staffId) {
        String msg = staffVoService.deleteStaffVo(staffId);
        if (msg.contains("成功")) {
            return JSONResult.ok(msg);
        } else {
            return JSONResult.errorMsg(msg);
        }
    }

    @RequestMapping("/addStaffVo")
    @ResponseBody
    public JSONResult addStaffVo(@ModelAttribute StaffVo staffVo) {
        System.out.println(staffVo.getStaffId());
        String msg = staffVoService.addStaffVo(staffVo);
        if (msg!=null){
            return JSONResult.ok(msg);
        }else{
            return JSONResult.errorMsg("控制层出现问题");
        }
    }
}
