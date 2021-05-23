package com.yutongdxTop.LaborDispatching.mapper;

import com.yutongdxTop.LaborDispatching.domain.pojo.Client;
import com.yutongdxTop.LaborDispatching.domain.pojo.ClientExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientMapper {
    long countByExample(ClientExample example);

    int deleteByExample(ClientExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Client record);

    int insertSelective(Client record);

    List<Client> selectByExample(ClientExample example);

    Client selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Client record, @Param("example") ClientExample example);

    int updateByExample(@Param("record") Client record, @Param("example") ClientExample example);

    int updateByPrimaryKeySelective(Client record);

    int updateByPrimaryKey(Client record);
}