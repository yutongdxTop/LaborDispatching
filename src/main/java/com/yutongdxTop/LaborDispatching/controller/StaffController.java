package com.yutongdxTop.LaborDispatching.controller;

import com.yutongdxTop.LaborDispatching.domain.pojo.Staff;
import com.yutongdxTop.LaborDispatching.service.StaffService;
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
@RequestMapping("/LaborDispatching/staff")
public class StaffController {

    @Autowired
    private StaffService staffService;

    @RequestMapping(value = "/getAllStaffs")
    @ResponseBody
    public JSONResult getAllStaffs(@RequestParam(value = "type",  defaultValue = "all") String type) {
        List<Staff> staffs;
        if (Objects.equals(type, "all")) {
            staffs = staffService.getAllStaffs();
        } else {
            staffs = staffService.getStaffByTypeLike(type);
        }
        if (staffs.isEmpty()) {
            return JSONResult.errorMsg("还没有全职员工信息");
        } else {
            int count = staffs.size();
            return JSONResult.ok(count, staffs);
        }
    }

    @RequestMapping("/deleteStaff")
    @ResponseBody
    public JSONResult deleteStaff(@RequestParam(value = "id", required = true, defaultValue = "-1") String id) {
        String msg = staffService.deleteStaff(id);
        if (msg.contains("成功")) {
            return JSONResult.ok(msg);
        } else {
            return JSONResult.errorMsg(msg);
        }
    }

    @RequestMapping("/addStaff")
    @ResponseBody
    public JSONResult addStaff(@ModelAttribute Staff staff) {
        System.out.println(staff.getId());
        String msg = staffService.addStaff(staff);
        if (msg!=null){
            return JSONResult.ok(msg);
        }else{
            return JSONResult.errorMsg("控制层出现问题");
        }
    }

}
