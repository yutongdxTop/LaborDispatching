package com.yutongdxTop.LaborDispatching.mapper;

import com.yutongdxTop.LaborDispatching.domain.vo.FreeTimeVo;
import com.yutongdxTop.LaborDispatching.domain.vo.FreeTimeVoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FreeTimeVoMapper {
    long countByExample(FreeTimeVoExample example);

    int deleteByExample(FreeTimeVoExample example);

    int insert(FreeTimeVo record);

    int insertSelective(FreeTimeVo record);

    List<FreeTimeVo> selectByExample(FreeTimeVoExample example);

    int updateByExampleSelective(@Param("record") FreeTimeVo record, @Param("example") FreeTimeVoExample example);

    int updateByExample(@Param("record") FreeTimeVo record, @Param("example") FreeTimeVoExample example);
}