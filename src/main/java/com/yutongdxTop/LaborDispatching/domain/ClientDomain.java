package com.yutongdxTop.LaborDispatching.domain;

import com.yutongdxTop.LaborDispatching.domain.pojo.Client;
import com.yutongdxTop.LaborDispatching.domain.pojo.ClientExample;
import com.yutongdxTop.LaborDispatching.domain.pojo.User;
import com.yutongdxTop.LaborDispatching.domain.pojo.UserExample;
import com.yutongdxTop.LaborDispatching.domain.vo.ClientVo;
import com.yutongdxTop.LaborDispatching.domain.vo.ClientVoExample;
import com.yutongdxTop.LaborDispatching.mapper.ClientMapper;
import com.yutongdxTop.LaborDispatching.mapper.ClientVoMapper;
import com.yutongdxTop.LaborDispatching.mapper.UserMapper;
import com.yutongdxTop.LaborDispatching.service.ClientVoService;
import com.yutongdxTop.LaborDispatching.util.GetRandom;
import com.yutongdxTop.LaborDispatching.util.GetTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ClientDomain implements ClientVoService {

    @Autowired
    ClientVoMapper clientVoMapper;
    @Autowired
    ClientMapper clientMapper;
    @Autowired
    UserMapper userMapper;

    @Override
    public List<ClientVo> getAllClientVos() {
        ClientVoExample clientVoExample = new ClientVoExample();
        ClientVoExample.Criteria criteria = clientVoExample.createCriteria();
        criteria.andClientIdIsNotNull();
        return clientVoMapper.selectByExample(clientVoExample);
    }

    @Override
    public ClientVo getClientVoByUserName(String userName) {
        ClientVoExample clientVoExample = new ClientVoExample();
        ClientVoExample.Criteria criteria = clientVoExample.createCriteria();
        criteria.andUserNameEqualTo(userName);
        List<ClientVo> clientVos = clientVoMapper.selectByExample(clientVoExample);
        if (clientVos.size() != 0) {
            return clientVos.get(0);
        } else {
            return null;
        }
    }

    @Override
    public ClientVo getClientVoByTypeLike(String type) {
        ClientVoExample clientVoExample = new ClientVoExample();
        ClientVoExample.Criteria criteria = clientVoExample.createCriteria();
        criteria.andTypeLike("%" + type + "%");
        List<ClientVo> clientVos = clientVoMapper.selectByExample(clientVoExample);
        if (clientVos.size() != 0) {
            return clientVos.get(0);
        } else {
            return null;
        }
    }

    @Override
    public String deleteClientVo(String userName) {
        try{
            User user = userMapper.selectByPrimaryKey(userName);
            int i = clientMapper.deleteByPrimaryKey(user.getClientId());
            i =  i + userMapper.deleteByPrimaryKey(userName);
            if (i==0){
                return "删除失败，用户不存在";
            }else{
                return "删除成功";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "删除失败";
        }
    }

    @Override
    public String updateClientVo(ClientVo clientVo) {
        try {
            int i;

            System.out.println(clientVo.getUserName());
            User user = userMapper.selectByPrimaryKey(clientVo.getUserName());  //找出表单用户名对应的用户

            ClientVoExample clientVoExample = new ClientVoExample();  //找出数据库中原客户主体
            ClientVoExample.Criteria clientId = clientVoExample.createCriteria();
            clientId.andClientIdEqualTo(clientVo.getClientId());
            List<ClientVo> clientVos = clientVoMapper.selectByExample(clientVoExample);

            //如果表单原客户用户名和表单的用户名不同，但用户名在数据库中存在，则为修改的用户名已被注册
            if ( user != null && !Objects.equals(clientVos.get(0).getUserName(), clientVo.getUserName())) {
                return "修改失败，用户名已存在!";
            } else if (user == null) {  //如果用户名不存在，则修改用户名
                UserExample userExample = new UserExample();      //删除旧账号
                UserExample.Criteria criteria = userExample.createCriteria();
                criteria.andClientIdEqualTo(clientVo.getClientId());
                List<User> users = userMapper.selectByExample(userExample);
                userMapper.deleteByPrimaryKey(users.get(0).getName());

                user = new User();   //用新的用户名注册账号
                user.setName(clientVo.getUserName());
                user.setClientId(clientVo.getClientId());
                user.setPassword(clientVo.getPassword());
                user.setStaffId(null);

                i = userMapper.insert(user);
            } else {   //如果用户名存在且与原客户用户名相同，则用户名未修改，可直接更新
                user.setName(clientVo.getUserName());
                user.setClientId(clientVo.getClientId());
                user.setPassword(clientVo.getPassword());
                user.setStaffId(null);

                i = userMapper.updateByPrimaryKey(user);
            }

            Client client = new Client();  //更新Client
            client.setId(clientVo.getClientId());
            client.setName(clientVo.getName());
            client.setTelephone(clientVo.getTelephone());
            client.setAddress(clientVo.getAddress());
            client.setType(clientVo.getType());

            i = i + clientMapper.updateByPrimaryKey(client);
            System.out.println("updateClientVo返回：" + i);
            if (i == 0){
                return "修改失败，信息没更改!";
            }else {
                return "修改成功!";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "修改失败，用户名已存在!";
        }
    }

    @Override
    public String addClientVo(ClientVo clientVo) {
        try {
            int i = 0;
            User user;
            Client client;

            ClientExample clientExample = new ClientExample();
            ClientExample.Criteria clientName = clientExample.createCriteria();
            clientName.andNameEqualTo(clientVo.getName());
            List<Client> clients = clientMapper.selectByExample(clientExample);

            user = userMapper.selectByPrimaryKey(clientVo.getUserName());

            if (clients.isEmpty() && user == null) {    //结果为空说明不存在客户主体和相同用户名，可以添加
                int clientId = Integer.parseInt(GetTime.getTime() + GetRandom.getRandom(0, 99));
                client = new Client();
                client.setId(clientId);
                client.setName(clientVo.getName());
                client.setTelephone(clientVo.getTelephone());
                client.setAddress(clientVo.getAddress());
                client.setType(clientVo.getType());

                user = new User();
                user.setName(clientVo.getUserName());
                user.setPassword(clientVo.getPassword());
                user.setClientId(clientId);
                user.setStaffId(null);

                i = clientMapper.insert(client) + userMapper.insert(user);
            }

            System.out.println("addClientVo返回：" + i);
            if (i == 2) {
                return "添加失败,客户主体或用户名已存在";
            } else {
                return "添加成功";
            }
        } catch (Exception e) {
            return "添加失败";
        }
    }

}
