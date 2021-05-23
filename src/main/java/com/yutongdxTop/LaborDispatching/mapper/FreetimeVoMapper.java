package com.yutongdxTop.LaborDispatching.mapper;

import com.yutongdxTop.LaborDispatching.domain.vo.FreetimeVo;
import com.yutongdxTop.LaborDispatching.domain.vo.FreetimeVoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FreetimeVoMapper {
    long countByExample(FreetimeVoExample example);

    int deleteByExample(FreetimeVoExample example);

    int insert(FreetimeVo record);

    int insertSelective(FreetimeVo record);

    List<FreetimeVo> selectByExample(FreetimeVoExample example);

    int updateByExampleSelective(@Param("record") FreetimeVo record, @Param("example") FreetimeVoExample example);

    int updateByExample(@Param("record") FreetimeVo record, @Param("example") FreetimeVoExample example);
}