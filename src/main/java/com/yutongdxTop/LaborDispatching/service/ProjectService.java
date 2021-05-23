package com.yutongdxTop.LaborDispatching.service;

import com.yutongdxTop.LaborDispatching.domain.pojo.Project;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProjectService {

    List<Project> getAllProjects(int clientId);

    List<Project> getProjectByTypeLike(int clientId, String type);

    String addProject(@Param("project") Project project);

    String deleteProject(String id);

    String updateProject(Project project);

}