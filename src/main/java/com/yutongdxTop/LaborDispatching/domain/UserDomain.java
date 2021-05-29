package com.yutongdxTop.LaborDispatching.domain;

import com.yutongdxTop.LaborDispatching.domain.pojo.*;
import com.yutongdxTop.LaborDispatching.domain.vo.ClientVo;
import com.yutongdxTop.LaborDispatching.domain.vo.StaffVo;
import com.yutongdxTop.LaborDispatching.domain.vo.StaffVoExample;
import com.yutongdxTop.LaborDispatching.mapper.*;
import com.yutongdxTop.LaborDispatching.service.UserService;
import com.yutongdxTop.LaborDispatching.util.GetRandom;
import com.yutongdxTop.LaborDispatching.util.GetTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserDomain implements UserService {
    @Autowired
    StaffVoMapper staffVoMapper;
    @Autowired
    StaffMapper staffMapper;
    @Autowired
    ClientVoMapper clientVoMapper;
    @Autowired
    ClientMapper clientMapper;
    @Autowired
    UserMapper userMapper;

    @Override
    public StaffVo login(String userName) {
        StaffVo staffVo;
        User user = userMapper.selectByPrimaryKey(userName);
        if (Objects.equals(user, null)) {  //找不到用户
            staffVo = null;
        } else if (!Objects.equals(user.getStaffId(), null)) {  //用户为员工
            StaffVoExample staffVoExample = new StaffVoExample();
            StaffVoExample.Criteria criteria = staffVoExample.createCriteria();
            criteria.andUserNameEqualTo(userName);
            staffVo = staffVoMapper.selectByExample(staffVoExample).get(0);
        } else {  //若用户为客户，则借用员工个体返回信息
            staffVo = new StaffVo();
            staffVo.setUserName(user.getName());
            staffVo.setPassword(user.getPassword());
            staffVo.setStaffId(user.getClientId());
            staffVo.setIdentity("客户");
        }
        return staffVo;
    }

    @Override
    public String staffRegister(StaffVo staffVo) {
        try {
            int i = 0;
            User user;
            Staff staff;

            StaffExample staffExample = new StaffExample();
            StaffExample.Criteria staffIdnumber = staffExample.createCriteria();
            staffIdnumber.andIdNumberEqualTo(staffVo.getIdNumber());
            List<Staff> staffs = staffMapper.selectByExample(staffExample);

            user = userMapper.selectByPrimaryKey(staffVo.getUserName());

            if (staffs.isEmpty() && user == null) {    //结果为空说明不存在员工主体和相同用户名，可以添加
                String staffId = GetTime.getNowTimeString("yyMMddHHmmss") + GetRandom.getRandom(0, 99);
                staff = new Staff();
                staff.setId(staffId);
                staff.setName(staffVo.getName());
                staff.setIdNumber(staffVo.getIdNumber());
                staff.setSex(staffVo.getSex());
                staff.setIdentity(staffVo.getIdentity());
                staff.setType(staffVo.getType());

                user = new User();
                user.setName(staffVo.getUserName());
                user.setPassword(staffVo.getPassword());
                user.setStaffId(staffId);
                user.setClientId(null);

                i = staffMapper.insert(staff) + userMapper.insert(user);
            }

            System.out.println("staffRegister返回：" + i);
            if (i != 2) {
                return "添加失败,员工主体（身份证号码）或用户名已存在";
            } else {
                return "添加成功";
            }
        } catch (Exception e) {
            return "添加失败";
        }
    }

    @Override
    public String clientRegister(ClientVo clientVo) {
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
                String clientId ="c" + GetTime.getNowTimeString("yyMMddHHmmss") + GetRandom.getRandom(0, 99);
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

            System.out.println("clientRegister返回：" + i);
            if (i != 2) {
                return "添加失败,客户主体（客户名称）或用户名已存在";
            } else {
                return "添加成功";
            }
        } catch (Exception e) {
            return "添加失败";
        }
    }
}
