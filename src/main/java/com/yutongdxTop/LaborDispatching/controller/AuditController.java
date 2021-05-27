package com.yutongdxTop.LaborDispatching.controller;

import com.yutongdxTop.LaborDispatching.domain.pojo.Project;
import com.yutongdxTop.LaborDispatching.service.AuditService;
import com.yutongdxTop.LaborDispatching.util.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/LaborDispatching/audit")
public class AuditController {

    @Autowired
    private AuditService auditService;

    @RequestMapping(value = "/getAllProjects")
    public JSONResult getAllProjects(@RequestParam(value = "status", defaultValue = "1") String status, @RequestParam(value = "type",  defaultValue = "all") String type) {
        List<Project> projects;
        if (type.equals("all")) {
            projects = auditService.getAllProjects(status);  //获取所有项目信息
        } else {
            projects = auditService.getProjectByTypeLike(status, type);  //配合前端搜索功能，根据项目分类搜索项目
        }
        if (projects.isEmpty()) {
            return JSONResult.errorMsg("还没有项目信息");
        } else {
            int count = projects.size();
            return JSONResult.ok(count, projects);
        }
    }

}
