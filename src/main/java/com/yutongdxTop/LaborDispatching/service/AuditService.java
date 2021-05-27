package com.yutongdxTop.LaborDispatching.service;

import com.yutongdxTop.LaborDispatching.domain.pojo.Project;

import java.util.List;

public interface AuditService {  //审计信息服务（当天项目和未接单项目）

    List<Project> getAllProjects(String status);

    List<Project> getProjectByTypeLike(String status, String type);

}
