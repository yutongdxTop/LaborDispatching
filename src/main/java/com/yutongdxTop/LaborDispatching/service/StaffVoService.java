package com.yutongdxTop.LaborDispatching.service;

import com.yutongdxTop.LaborDispatching.domain.vo.StaffVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StaffVoService {  //自由职业者个人信息管理服务

    List<StaffVo> getAllFreelancers();

    StaffVo getStaffVoByUserName(String userName);

    List<StaffVo> getStaffVoByTypeLike(String type);

    String addStaffVo(@Param("staffVo") StaffVo staffVo);

    String deleteStaffVo(String staffId);

    String updateStaffVo(StaffVo staffVo);

}
