package com.yutongdxTop.LaborDispatching.service;

import com.yutongdxTop.LaborDispatching.domain.vo.StaffVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StaffVoService {

    List<StaffVo> getAllStaffVos();

    StaffVo getStaffVoByUserName(String userName);

    StaffVo getStaffVoByTypeLike(String type);

    String addStaffVo(@Param("staffVo") StaffVo staffVo);

    String deleteStaffVo(String userName);

    String updateStaffVo(StaffVo staffVo);

}