package com.yutongdxTop.LaborDispatching.domain;

import com.yutongdxTop.LaborDispatching.domain.pojo.Contact;
import com.yutongdxTop.LaborDispatching.domain.pojo.ContactExample;
import com.yutongdxTop.LaborDispatching.domain.pojo.FreeTime;
import com.yutongdxTop.LaborDispatching.domain.pojo.FreeTimeExample;
import com.yutongdxTop.LaborDispatching.mapper.ContactMapper;
import com.yutongdxTop.LaborDispatching.mapper.FreeTimeMapper;
import com.yutongdxTop.LaborDispatching.service.FreelancerService;
import com.yutongdxTop.LaborDispatching.util.GetRandom;
import com.yutongdxTop.LaborDispatching.util.GetTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FreelancerDomain implements FreelancerService {

    @Autowired
    FreeTimeMapper freeTimeMapper;
    @Autowired
    ContactMapper contactMapper;

    @Override
    public List<FreeTime> getAllFreeTimes(String staffId) {
        FreeTimeExample freeTimeExample = new FreeTimeExample();
        FreeTimeExample.Criteria criteria = freeTimeExample.createCriteria();
        criteria.andStaffIdEqualTo(staffId);
        return freeTimeMapper.selectByExample(freeTimeExample);
    }

    @Override
    public String updateFreeTime(FreeTime freeTime) {
        int i = freeTimeMapper.updateByPrimaryKeySelective(freeTime);
        System.out.println("updateFreeTime返回:"+i);
        if (i==0){
            return "修改失败，时间表信息没有修改";
        }else{
            return "修改成功";
        }
    }

    @Override
    public String deleteFreeTime(String freeTimeId) {
        try{
            int i = freeTimeMapper.deleteByPrimaryKey(freeTimeId);
            if (i==0){
                return "删除失败";
            }else{
                return "删除成功";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "删除失败";
        }
    }

    @Override
    public String addFreeTime(FreeTime freeTime) {
        try{
            FreeTime check = freeTimeMapper.selectByPrimaryKey(freeTime.getId());
            if (check != null) {//时间表信息已存在则为更新
                System.out.println(freeTime.getId());
                int i = freeTimeMapper.updateByPrimaryKeySelective(freeTime);
                System.out.println("updateFreeTime返回：" + i);
                if (i == 0) {
                    return "更新失败，时间表信息没有修改";
                } else {
                    return "更新成功";
                }
            } else {  //否则为添加时间表信息
                String staffId = freeTime.getStaffId();
                freeTime.setId(staffId.substring(staffId.length()-2) + GetTime.getNowTimeString("yyMMddHHmm") + GetRandom.getRandom(0, 99));
                int i = freeTimeMapper.insert(freeTime);
                System.out.println("addFreeTime返回：" + i);
                if (i == 0) {
                    return "添加失败";
                } else {
                    return "添加成功";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "添加失败 时间表重复 不能重ID";
        }
    }

    @Override
    public List<Contact> getAllContacts(String staffId) {
        ContactExample contactExample = new ContactExample();
        ContactExample.Criteria criteria = contactExample.createCriteria();
        criteria.andStaffIdEqualTo(staffId);
        return contactMapper.selectByExample(contactExample);
    }

    @Override
    public String updateContact(Contact contact) {
        int i = contactMapper.updateByPrimaryKeySelective(contact);
        System.out.println("updateContact返回:"+i);
        if (i==0){
            return "修改失败，联系方式没有修改";
        }else{
            return "修改成功";
        }
    }

    @Override
    public String deleteContact(String contactId) {
        try{
            int i = contactMapper.deleteByPrimaryKey(contactId);
            if (i==0){
                return "删除失败";
            }else{
                return "删除成功";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "删除失败";
        }
    }

    @Override
    public String addContact(Contact contact) {
        try{
            Contact check = contactMapper.selectByPrimaryKey(contact.getId());
            if (check != null) {//时间表信息已存在则为更新
                System.out.println(contact.getId());
                int i = contactMapper.updateByPrimaryKeySelective(contact);
                System.out.println("updateContact返回：" + i);
                if (i == 0) {
                    return "更新失败，联系方式没有修改";
                } else {
                    return "更新成功";
                }
            } else {  //否则为添加时间表信息
                String staffId = contact.getStaffId();
                contact.setId(staffId.substring(staffId.length()-2) + GetTime.getNowTimeString("yyMMddHHmm") + GetRandom.getRandom(0, 99));
                int i = contactMapper.insert(contact);
                System.out.println("addContact返回：" + i);
                if (i == 0) {
                    return "添加失败";
                } else {
                    return "添加成功";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "添加失败 联系方式重复 不能重ID";
        }
    }
}
