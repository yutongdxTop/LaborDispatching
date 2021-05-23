package com.yutongdxTop.LaborDispatching.mapper;

import com.yutongdxTop.LaborDispatching.domain.vo.ContactVo;
import com.yutongdxTop.LaborDispatching.domain.vo.ContactVoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactVoMapper {
    long countByExample(ContactVoExample example);

    int deleteByExample(ContactVoExample example);

    int insert(ContactVo record);

    int insertSelective(ContactVo record);

    List<ContactVo> selectByExample(ContactVoExample example);

    int updateByExampleSelective(@Param("record") ContactVo record, @Param("example") ContactVoExample example);

    int updateByExample(@Param("record") ContactVo record, @Param("example") ContactVoExample example);
}