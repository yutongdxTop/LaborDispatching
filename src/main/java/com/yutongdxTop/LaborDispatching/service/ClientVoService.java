package com.yutongdxTop.LaborDispatching.service;

import com.yutongdxTop.LaborDispatching.domain.vo.ClientVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ClientVoService {  //客户信息管理服务

    List<ClientVo> getAllClientVos();

    ClientVo getClientVoByUserName(String userName);

    List<ClientVo> getClientVoByTypeLike(String type);

    String addClientVo(@Param("clientVo") ClientVo clientVo);

    String deleteClientVo(String clientId);

    String updateClientVo(ClientVo clientVo);

}
