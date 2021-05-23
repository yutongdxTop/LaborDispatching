package com.yutongdxTop.LaborDispatching.service;

import com.yutongdxTop.LaborDispatching.domain.vo.ClientVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ClientVoService {

    List<ClientVo> getAllClientVos();

    ClientVo getClientVoByUserName(String userName);

    ClientVo getClientVoByTypeLike(String type);

    String addClientVo(@Param("clientVo") ClientVo clientVo);

    String deleteClientVo(String userName);

    String updateClientVo(ClientVo clientVo);

}
