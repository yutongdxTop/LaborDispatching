package com.yutongdxTop.LaborDispatching.service;

import com.yutongdxTop.LaborDispatching.domain.vo.ContactVo;

import com.yutongdxTop.LaborDispatching.domain.vo.FreeTimeVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AdministratorService {  //管理员对所有员工时间表和联系方式管理服务

    List<FreeTimeVo> getAllFreeTimeVos();

    List<FreeTimeVo> getFreeTimeVoByNameLike(String name);

    String addFreeTimeVo(@Param("freeTimeVo") FreeTimeVo freeTimeVo);

    String deleteFreeTimeVo(String id);

    String updateFreeTimeVo(FreeTimeVo freeTime);

    List<ContactVo> getAllContactVos();

    List<ContactVo> getContactVoByNameLike(String name);

    String addContactVo(@Param("contactVo") ContactVo contactVo);

    String deleteContactVo(String id);

    String updateContactVo(ContactVo contact);

}
