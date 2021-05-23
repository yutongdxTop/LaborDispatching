package com.yutongdxTop.LaborDispatching.mapper;

import com.yutongdxTop.LaborDispatching.domain.vo.ClientVo;
import com.yutongdxTop.LaborDispatching.domain.vo.ClientVoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientVoMapper {
    long countByExample(ClientVoExample example);

    int deleteByExample(ClientVoExample example);

    int insert(ClientVo record);

    int insertSelective(ClientVo record);

    List<ClientVo> selectByExample(ClientVoExample example);

    int updateByExampleSelective(@Param("record") ClientVo record, @Param("example") ClientVoExample example);

    int updateByExample(@Param("record") ClientVo record, @Param("example") ClientVoExample example);
}