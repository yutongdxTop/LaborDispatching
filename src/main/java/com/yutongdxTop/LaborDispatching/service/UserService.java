package com.yutongdxTop.LaborDispatching.service;

import com.yutongdxTop.LaborDispatching.domain.vo.ClientVo;
import com.yutongdxTop.LaborDispatching.domain.vo.StaffVo;
import org.apache.ibatis.annotations.Param;

public interface UserService {  //用户登录注册服务

    StaffVo login(String userName);

    String staffRegister(@Param("staffVo") StaffVo staffVo);

    String clientRegister(@Param("clientVo") ClientVo clientVo);

}
