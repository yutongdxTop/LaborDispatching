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

@Controller
@RequestMapping("/LaborDispatching/staffVo")
public class StaffVoController {
    @Autowired
    private StaffVoService staffVoService;

    @RequestMapping(value = "/getAllStaffVos")
    @ResponseBody
    public JSONResult getAllStaffVos() {
        List<StaffVo> staffVos = staffVoService.getAllStaffVos();
        if (staffVos.isEmpty()) {
            return JSONResult.errorMsg("还没有客户信息");
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
}
