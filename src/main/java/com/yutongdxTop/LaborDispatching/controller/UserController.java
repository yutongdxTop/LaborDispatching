package com.yutongdxTop.LaborDispatching.controller;

import com.yutongdxTop.LaborDispatching.domain.vo.ClientVo;
import com.yutongdxTop.LaborDispatching.domain.vo.StaffVo;
import com.yutongdxTop.LaborDispatching.service.UserService;
import com.yutongdxTop.LaborDispatching.util.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/LaborDispatching/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/login")
    @ResponseBody
    public JSONResult login(@RequestParam(value = "name", required = true, defaultValue = "-1") String userName) {
        StaffVo staffVo = userService.login(userName);
        System.out.println("login:" + userName);
        if (staffVo == null) {
            return JSONResult.errorMsg("找不到这个用户");
        } else {
            return JSONResult.ok(staffVo);
        }
    }

    @RequestMapping("/staffRegister")
    @ResponseBody
    public JSONResult staffRegister(@ModelAttribute StaffVo staffVo) {
        System.out.println(staffVo.getStaffId());
        String msg = userService.staffRegister(staffVo);
        if (msg!=null){
            return JSONResult.ok(msg);
        }else{
            return JSONResult.errorMsg("控制层出现问题");
        }
    }

    @RequestMapping("/clientRegister")
    @ResponseBody
    public JSONResult clientRegister(@ModelAttribute ClientVo clientVo) {
        System.out.println(clientVo.getName());
        String msg = userService.clientRegister(clientVo);
        if (msg!=null){
            return JSONResult.ok(msg);
        }else{
            return JSONResult.errorMsg("控制层出现问题");
        }
    }

}
