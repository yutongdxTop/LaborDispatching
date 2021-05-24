package com.yutongdxTop.LaborDispatching.mapper;

import com.yutongdxTop.LaborDispatching.domain.pojo.FreeTime;
import com.yutongdxTop.LaborDispatching.domain.pojo.FreeTimeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FreeTimeMapper {
    long countByExample(FreeTimeExample example);

    int deleteByExample(FreeTimeExample example);

    int deleteByPrimaryKey(String id);

    int insert(FreeTime record);

    int insertSelective(FreeTime record);

    List<FreeTime> selectByExample(FreeTimeExample example);

    FreeTime selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") FreeTime record, @Param("example") FreeTimeExample example);

    int updateByExample(@Param("record") FreeTime record, @Param("example") FreeTimeExample example);

    int updateByPrimaryKeySelective(FreeTime record);

    int updateByPrimaryKey(FreeTime record);
}