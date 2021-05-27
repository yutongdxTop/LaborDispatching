package com.yutongdxTop.LaborDispatching.domain;

import com.yutongdxTop.LaborDispatching.domain.pojo.Project;
import com.yutongdxTop.LaborDispatching.domain.pojo.ProjectExample;
import com.yutongdxTop.LaborDispatching.mapper.ProjectMapper;
import com.yutongdxTop.LaborDispatching.service.AuditService;
import com.yutongdxTop.LaborDispatching.util.GetTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Service
public class AuditDomain implements AuditService {

    @Autowired
    ProjectMapper projectMapper;

    @Override
    public List<Project> getAllProjects(String status) {
        List<Project> projects;

        if (status.equals("1")) {  //查询所有未接单项目
            ProjectExample projectExample = new ProjectExample();
            ProjectExample.Criteria criteria = projectExample.createCriteria();
            criteria.andStatusEqualTo("未接单");
            projects = projectMapper.selectByExample(projectExample);
        } else {  //查询当天发布的项目
            projects = new LinkedList<>();

            Date dateNow = new Date();  //目前时间
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateNow);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            Date dateBefore = calendar.getTime();  //每天零点

            ProjectExample allExample = new ProjectExample();
            ProjectExample.Criteria all = allExample.createCriteria();
            all.andIdIsNotNull();
            List<Project> allProjects = projectMapper.selectByExample(allExample);  //获得所有项目列表
            for (Project project : allProjects) {
                Date time = GetTime.dateTransform(project.getTime());
                if (time.after(dateBefore) && time.before(dateNow)) {  //将当日发布的项目加入列表
                    projects.add(project);
                }
            }
        }
        return projects;
    }

    @Override
    public List<Project> getProjectByTypeLike(String status, String type) {
        List<Project> projects;

        if (status.equals("1")) {  //查询所有未接单项目
            ProjectExample projectExample = new ProjectExample();
            ProjectExample.Criteria criteria = projectExample.createCriteria();
            criteria.andStatusEqualTo("未接单").andTypeLike("%" + type + "%");
            projects = projectMapper.selectByExample(projectExample);
        } else {  //查询当天发布的项目
            projects = new LinkedList<>();

            Date dateNow = new Date();  //目前时间
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateNow);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            Date dateBefore = calendar.getTime();  //每天零点

            ProjectExample allExample = new ProjectExample();
            ProjectExample.Criteria all = allExample.createCriteria();
            all.andTypeLike("%" + type + "%");
            List<Project> allProjects = projectMapper.selectByExample(allExample);  //获得所有项目列表
            for (Project project : allProjects) {
                Date time = GetTime.dateTransform(project.getTime());
                if (time.after(dateBefore) && time.before(dateNow)) {  //将当日发布的项目加入列表
                    projects.add(project);
                }
            }
        }
        return projects;
    }

}
