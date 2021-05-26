package com.yutongdxTop.LaborDispatching.service;

import com.yutongdxTop.LaborDispatching.domain.pojo.Project;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProjectService {  //项目信息管理服务

    List<Project> getAllProjects(String clientId);

    List<Project> getProjectByTypeLike(String clientId, String type);

    String addProject(@Param("project") Project project);

    String deleteProject(String id);

    String updateProject(Project project);

}
