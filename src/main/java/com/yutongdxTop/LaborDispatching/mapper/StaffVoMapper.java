package com.yutongdxTop.LaborDispatching.mapper;

import com.yutongdxTop.LaborDispatching.domain.vo.StaffVo;
import com.yutongdxTop.LaborDispatching.domain.vo.StaffVoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StaffVoMapper {
    long countByExample(StaffVoExample example);

    int deleteByExample(StaffVoExample example);

    int insert(StaffVo record);

    int insertSelective(StaffVo record);

    List<StaffVo> selectByExample(StaffVoExample example);

    int updateByExampleSelective(@Param("record") StaffVo record, @Param("example") StaffVoExample example);

    int updateByExample(@Param("record") StaffVo record, @Param("example") StaffVoExample example);
}