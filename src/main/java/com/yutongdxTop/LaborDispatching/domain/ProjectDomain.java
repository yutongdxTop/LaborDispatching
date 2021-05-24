package com.yutongdxTop.LaborDispatching.domain;

import com.yutongdxTop.LaborDispatching.domain.pojo.Project;
import com.yutongdxTop.LaborDispatching.domain.pojo.ProjectExample;
import com.yutongdxTop.LaborDispatching.mapper.ProjectMapper;
import com.yutongdxTop.LaborDispatching.service.ProjectService;

import com.yutongdxTop.LaborDispatching.util.GetRandom;
import com.yutongdxTop.LaborDispatching.util.GetTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ProjectDomain implements ProjectService {

    @Autowired
    ProjectMapper projectMapper;

    @Override
    public List<Project> getAllProjects(String clientId) {
        ProjectExample projectExample = new ProjectExample();
        ProjectExample.Criteria criteria = projectExample.createCriteria();
        if (clientId.equals("-1")) {  //管理员查询所有项目
            criteria.andIdIsNotNull();
        } else {  //客户查询自己发布的项目
            criteria.andClientIdEqualTo(clientId);
        }
        return projectMapper.selectByExample(projectExample);
    }

    @Override
    public List<Project> getProjectByTypeLike(String clientId, String type) {
        ProjectExample projectExample = new ProjectExample();
        ProjectExample.Criteria criteria = projectExample.createCriteria();
        if (clientId.equals("-1")) {  //管理员查询所有项目
            criteria.andIdIsNotNull().andTypeLike("%" + type + "%");
        } else {  //客户查询自己发布的项目
            criteria.andClientIdEqualTo(clientId).andTypeLike("%" + type + "%");
        }
        return projectMapper.selectByExample(projectExample);
    }

    @Override
    public String updateProject(Project project) {
        int i = projectMapper.updateByPrimaryKeySelective(project);
        System.out.println("updateProject返回:"+i);
        if (i==0){
            return "修改失败，项目信息没有修改";
        }else{
            return "修改成功";
        }
    }

    @Override
    public String deleteProject(String projectId) {
        try{
            int i = 0;
            Project project = projectMapper.selectByPrimaryKey(projectId);
            if (Objects.equals(project.getStatus(), "未接单")) {
                i = projectMapper.deleteByPrimaryKey(projectId);
            }
            if (i==0){
                return "删除失败，项目已接单";
            }else{
                return "删除成功";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "删除失败";
        }
    }

    @Override
    public String addProject(Project project) {
        try{
            Project check = projectMapper.selectByPrimaryKey(project.getId());
            if (check != null) {//项目信息已存在则为更新
                System.out.println(project.getId());
                int i = projectMapper.updateByPrimaryKeySelective(project);
                System.out.println("updateProject返回：" + i);
                if (i == 0) {
                    return "更新失败，项目信息没有修改";
                } else {
                    return "更新成功";
                }
            } else {  //否则为添加项目信息
                project.setId("p" + GetTime.getDateTime() + GetRandom.getRandom(0, 99));
                project.setTime(GetTime.getDate());
                int i = projectMapper.insert(project);
                System.out.println("addProject返回：" + i);
                if (i == 0) {
                    return "添加失败";
                } else {
                    return "添加成功";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "添加失败 项目重复 不能重ID";
        }

    }

}
