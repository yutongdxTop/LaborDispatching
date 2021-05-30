package com.yutongdxTop.LaborDispatching.domain;

import com.yutongdxTop.LaborDispatching.domain.pojo.Contact;
import com.yutongdxTop.LaborDispatching.domain.pojo.FreeTime;
import com.yutongdxTop.LaborDispatching.domain.pojo.Staff;
import com.yutongdxTop.LaborDispatching.domain.pojo.StaffExample;
import com.yutongdxTop.LaborDispatching.mapper.ContactMapper;
import com.yutongdxTop.LaborDispatching.mapper.FreeTimeMapper;
import com.yutongdxTop.LaborDispatching.mapper.StaffMapper;
import com.yutongdxTop.LaborDispatching.service.StaffService;
import com.yutongdxTop.LaborDispatching.util.GetRandom;
import com.yutongdxTop.LaborDispatching.util.GetTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StaffDomain implements StaffService {

    @Autowired
    StaffMapper staffMapper;
    @Autowired
    FreeTimeMapper freeTimeMapper;
    @Autowired
    ContactMapper contactMapper;

    @Override
    public List<Staff> getAllStaffs() {
        StaffExample staffExample = new StaffExample();
        StaffExample.Criteria criteria = staffExample.createCriteria();
        criteria.andIdentityEqualTo("全职员工");
        return staffMapper.selectByExample(staffExample);
    }

    @Override
    public List<Staff> getStaffByTypeLike(String type) {
        StaffExample staffExample = new StaffExample();
        StaffExample.Criteria criteria = staffExample.createCriteria();
        criteria.andTypeLike("%" + type + "%").andIdentityEqualTo("全职员工");
        return staffMapper.selectByExample(staffExample);
    }

    @Override
    public String addStaff(Staff staff) {
        try{
            Staff check = staffMapper.selectByPrimaryKey(staff.getId());
            if (check != null) {//员工信息已存在则为更新
                System.out.println(staff.getId());
                int i = staffMapper.updateByPrimaryKeySelective(staff);
                System.out.println("updateStaff返回：" + i);
                if (i == 0) {
                    return "更新失败，，员工信息没有修改";
                } else {
                    return "更新成功";
                }
            } else {  //否则为添加员工信息
                StaffExample staffExample = new StaffExample();
                StaffExample.Criteria staffIdnumber = staffExample.createCriteria();
                staffIdnumber.andIdNumberEqualTo(staff.getIdNumber());
                List<Staff> staffs = staffMapper.selectByExample(staffExample);

                if (!staffs.isEmpty()) {  //结果不为空说明存在员工主体，不可以添加
                    return "添加失败,员工主体（身份证号码）已存在";
                }
                staff.setId(GetTime.getNowTimeString("yyMMddHHmmss") + GetRandom.getRandom(0, 99));
                int i = staffMapper.insert(staff);

                FreeTime freeTime = new FreeTime();  //为全职员工生成一个时间表
                String staffId = staff.getId();
                freeTime.setId(staffId.substring(staffId.length()-2) + GetTime.getNowTimeString("yyMMddHHmm") + GetRandom.getRandom(0, 99));
                freeTime.setStaffId(staffId);
                freeTime.setTimeBegin(GetTime.getNowTimeString("yyyy/MM/dd HH:mm:ss"));
                freeTime.setTimeEnd("∞");
                i = i + freeTimeMapper.insert(freeTime);

                Contact contact = new Contact();  //为全职员工生成一个联系方式
                contact.setId(staffId.substring(staffId.length()-2) + GetTime.getNowTimeString("yyMMddHHmm") + GetRandom.getRandom(0, 99));
                contact.setStaffId(staffId);
                contact.setContactDetails("待填写");
                contact.setContactValue("待填写");
                i = i + contactMapper.insert(contact);

                System.out.println("addStaff返回：" + i);
                if (i == 0) {
                    return "添加失败";
                } else {
                    return "添加成功";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "添加失败 员工重复 不能重ID";
        }
    }

    @Override
    public String deleteStaff(String id) {
        try{
            int i = staffMapper.deleteByPrimaryKey(id);
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

}
