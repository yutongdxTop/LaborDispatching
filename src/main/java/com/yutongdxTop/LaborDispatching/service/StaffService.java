package com.yutongdxTop.LaborDispatching.service;

import com.yutongdxTop.LaborDispatching.domain.pojo.Staff;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StaffService {  //全职员工个人信息管理服务

    List<Staff> getAllStaffs();

    List<Staff> getStaffByTypeLike(String type);

    String addStaff(@Param("staff") Staff staff);

    String deleteStaff(String id);

}
