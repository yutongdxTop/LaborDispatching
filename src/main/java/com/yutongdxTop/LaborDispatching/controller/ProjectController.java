package com.yutongdxTop.LaborDispatching.controller;

import com.yutongdxTop.LaborDispatching.domain.pojo.Project;
import com.yutongdxTop.LaborDispatching.service.ProjectService;
import com.yutongdxTop.LaborDispatching.util.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/LaborDispatching/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @RequestMapping(value = "/getAllProjects")
    @ResponseBody
    public JSONResult getAllProjects(@RequestParam(value = "id", defaultValue = "-1") String clientId, @RequestParam(value = "type",  defaultValue = "id") String type) {
        List<Project> projects;
        if (type.equals("id")) {
            projects = projectService.getAllProjects(clientId);  //获取所有项目信息
        } else {
            projects = projectService.getProjectByTypeLike(clientId, type);  //配合前端搜索功能，根据项目分类搜索项目
        }
        if (projects.isEmpty()) {
            return JSONResult.errorMsg("还没有项目信息");
        } else {
            int count = projects.size();
            return JSONResult.ok(count, projects);
        }
    }

    @RequestMapping("/deleteProject")
    @ResponseBody
    public JSONResult deleteProject(@RequestParam(value = "id", required = true, defaultValue = "-1") String id) {
        String msg = projectService.deleteProject(id);

        if (msg.contains("成功")) {
            return JSONResult.ok(msg);
        } else {
            return JSONResult.errorMsg(msg);
        }
    }

    @RequestMapping("/updateProject")
    @ResponseBody
    public JSONResult updateProject(@ModelAttribute Project project) {
        String msg = projectService.updateProject(project);
        if (msg != null) {
            return JSONResult.ok(msg);
        } else {
            return JSONResult.errorMsg("控制层出现问题");
        }
    }

    @RequestMapping(value = "/addProject")
    @ResponseBody
    public JSONResult addProject(@ModelAttribute Project project) {
        System.out.println(project);
        String msg = projectService.addProject(project);
        if (msg.contains("成功")) {
            return JSONResult.ok(msg);
        } else {
            return JSONResult.errorMsg(msg);
        }
    }
}
