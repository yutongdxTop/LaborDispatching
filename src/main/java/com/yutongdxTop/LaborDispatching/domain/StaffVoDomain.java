package com.yutongdxTop.LaborDispatching.domain;

import com.yutongdxTop.LaborDispatching.domain.pojo.Staff;
import com.yutongdxTop.LaborDispatching.domain.pojo.StaffExample;
import com.yutongdxTop.LaborDispatching.domain.pojo.User;
import com.yutongdxTop.LaborDispatching.domain.pojo.UserExample;
import com.yutongdxTop.LaborDispatching.domain.vo.StaffVo;
import com.yutongdxTop.LaborDispatching.domain.vo.StaffVoExample;
import com.yutongdxTop.LaborDispatching.mapper.StaffMapper;
import com.yutongdxTop.LaborDispatching.mapper.StaffVoMapper;
import com.yutongdxTop.LaborDispatching.mapper.UserMapper;
import com.yutongdxTop.LaborDispatching.service.StaffVoService;
import com.yutongdxTop.LaborDispatching.util.GetRandom;
import com.yutongdxTop.LaborDispatching.util.GetTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class StaffVoDomain implements StaffVoService {

    @Autowired
    StaffVoMapper staffVoMapper;
    @Autowired
    StaffMapper staffMapper;
    @Autowired
    UserMapper userMapper;

    @Override
    public List<StaffVo> getAllStaffVos() {
        StaffVoExample staffVoExample = new StaffVoExample();
        StaffVoExample.Criteria criteria = staffVoExample.createCriteria();
        criteria.andStaffIdIsNotNull().andIdentityEqualTo("自由职业者");
        return staffVoMapper.selectByExample(staffVoExample);
    }

    @Override
    public StaffVo getStaffVoByUserName(String userName) {
        StaffVoExample staffVoExample = new StaffVoExample();
        StaffVoExample.Criteria criteria = staffVoExample.createCriteria();
        criteria.andUserNameEqualTo(userName);
        List<StaffVo> staffVos = staffVoMapper.selectByExample(staffVoExample);
        if (staffVos.size() != 0) {
            return staffVos.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<StaffVo> getStaffVoByTypeLike(String type) {
        StaffVoExample staffVoExample = new StaffVoExample();
        StaffVoExample.Criteria criteria = staffVoExample.createCriteria();
        criteria.andTypeLike("%" + type + "%").andIdentityEqualTo("自由职业者");
        return staffVoMapper.selectByExample(staffVoExample);
    }

    @Override
    public String deleteStaffVo(String staffId) {
        try{
            int i = staffMapper.deleteByPrimaryKey(staffId);
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
    public String updateStaffVo(StaffVo staffVo) {
        try {
            int i;

            System.out.println(staffVo.getUserName());
            User user = userMapper.selectByPrimaryKey(staffVo.getUserName());  //找出表单用户名对应的用户

            StaffVoExample staffVoExample = new StaffVoExample();  //找出数据库中原员工主体
            StaffVoExample.Criteria staffId = staffVoExample.createCriteria();
            staffId.andStaffIdEqualTo(staffVo.getStaffId());
            List<StaffVo> staffVos = staffVoMapper.selectByExample(staffVoExample);

            //如果表单原员工用户名和表单的用户名不同，但用户名在数据库中存在，则为修改的用户名已被注册
            if ( user != null && !Objects.equals(staffVos.get(0).getUserName(), staffVo.getUserName())) {
                return "修改失败，用户名已存在!";
            } else if (user == null) {  //如果用户名不存在，则修改用户名
                UserExample userExample = new UserExample();      //删除旧账号
                UserExample.Criteria criteria = userExample.createCriteria();
                criteria.andStaffIdEqualTo(staffVo.getStaffId());
                List<User> users = userMapper.selectByExample(userExample);
                userMapper.deleteByPrimaryKey(users.get(0).getName());

                user = new User();   //用新的用户名注册账号
                user.setName(staffVo.getUserName());
                user.setStaffId(staffVo.getStaffId());
                user.setPassword(staffVo.getPassword());
                user.setClientId(null);

                i = userMapper.insert(user);
            } else {   //如果用户名存在且与原员工用户名相同，则用户名未修改，可直接更新
                user.setName(staffVo.getUserName());
                user.setStaffId(staffVo.getStaffId());
                user.setPassword(staffVo.getPassword());
                user.setClientId(null);

                i = userMapper.updateByPrimaryKey(user);
            }

            Staff staff = new Staff();  //更新Staff
            staff.setId(staffVo.getStaffId());
            staff.setName(staffVo.getName());
            staff.setIdNumber(staffVo.getIdNumber());
            staff.setSex(staffVo.getSex());
            staff.setIdentity(staffVo.getIdentity());
            staff.setType(staffVo.getType());

            i = i + staffMapper.updateByPrimaryKey(staff);
            System.out.println("updateStaffVo返回：" + i);
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
    public String addStaffVo(StaffVo staffVo) {
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
                String staffId = GetTime.getDateTime() + GetRandom.getRandom(0, 99);
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

            System.out.println("addStaffVo返回：" + i);
            if (i != 2) {
                return "添加失败,员工主体（身份证号码）或用户名已存在";
            } else {
                return "添加成功";
            }
        } catch (Exception e) {
            return "添加失败";
        }
    }

}
