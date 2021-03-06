package com.yutongdxTop.LaborDispatching.domain;

import com.yutongdxTop.LaborDispatching.domain.pojo.Contact;
import com.yutongdxTop.LaborDispatching.domain.pojo.FreeTime;
import com.yutongdxTop.LaborDispatching.domain.vo.ContactVo;
import com.yutongdxTop.LaborDispatching.domain.vo.ContactVoExample;
import com.yutongdxTop.LaborDispatching.domain.vo.FreeTimeVo;
import com.yutongdxTop.LaborDispatching.domain.vo.FreeTimeVoExample;
import com.yutongdxTop.LaborDispatching.mapper.ContactMapper;
import com.yutongdxTop.LaborDispatching.mapper.ContactVoMapper;
import com.yutongdxTop.LaborDispatching.mapper.FreeTimeMapper;
import com.yutongdxTop.LaborDispatching.mapper.FreeTimeVoMapper;
import com.yutongdxTop.LaborDispatching.service.AdministratorService;
import com.yutongdxTop.LaborDispatching.util.GetRandom;
import com.yutongdxTop.LaborDispatching.util.GetTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdministratorDomain implements AdministratorService {

    @Autowired
    FreeTimeMapper freeTimeMapper;
    @Autowired
    ContactMapper contactMapper;
    @Autowired
    FreeTimeVoMapper freeTimeVoMapper;
    @Autowired
    ContactVoMapper contactVoMapper;

    @Override
    public List<FreeTimeVo> getAllFreeTimeVos() {
        FreeTimeVoExample freeTimeVoExample = new FreeTimeVoExample();
        FreeTimeVoExample.Criteria criteria = freeTimeVoExample.createCriteria();
        criteria.andIdIsNotNull();
        return freeTimeVoMapper.selectByExample(freeTimeVoExample);
    }

    @Override
    public List<FreeTimeVo> getFreeTimeVoByNameLike(String name) {
        FreeTimeVoExample freeTimeVoExample = new FreeTimeVoExample();
        FreeTimeVoExample.Criteria criteria = freeTimeVoExample.createCriteria();
        criteria.andNameLike("%" + name + "%");
        return freeTimeVoMapper.selectByExample(freeTimeVoExample);
    }

    @Override
    public String updateFreeTimeVo(FreeTimeVo freeTimeVo) {
        FreeTime freeTime = new FreeTime();
        freeTime.setId(freeTimeVo.getId());
        freeTime.setStaffId(freeTimeVo.getStaffId());
        freeTime.setTimeBegin(freeTimeVo.getFreeTimeBegin());
        freeTime.setTimeEnd(freeTimeVo.getFreeTimeEnd());

        int i = freeTimeMapper.updateByPrimaryKey(freeTime);
        System.out.println("updateFreeTimeVo??????:"+i);
        if (i==0){
            return "??????????????????????????????????????????";
        }else{
            return "????????????";
        }
    }

    @Override
    public String deleteFreeTimeVo(String freeTimeId) {
        try{
            int i = freeTimeMapper.deleteByPrimaryKey(freeTimeId);
            if (i==0){
                return "????????????";
            }else{
                return "????????????";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "????????????";
        }
    }

    @Override
    public String addFreeTimeVo(FreeTimeVo freeTimeVo) {
        try{
            FreeTime check = freeTimeMapper.selectByPrimaryKey(freeTimeVo.getId());
            if (check != null) {//????????????????????????????????????
                check.setTimeBegin(freeTimeVo.getFreeTimeBegin());
                check.setTimeEnd(freeTimeVo.getFreeTimeEnd());

                int i = freeTimeMapper.updateByPrimaryKeySelective(check);
                System.out.println("updateFreeTimeVo?????????" + i);
                if (i == 0) {
                    return "??????????????????????????????????????????";
                } else {
                    return "????????????";
                }
            } else {  //??????????????????????????????
                FreeTime freeTime = new FreeTime();
                String staffId = freeTimeVo.getStaffId();
                freeTime.setId(staffId.substring(staffId.length()-2) + GetTime.getNowTimeString("yyMMddHHmm") + GetRandom.getRandom(0, 99));
                freeTime.setStaffId(freeTimeVo.getStaffId());
                freeTime.setTimeBegin(freeTimeVo.getFreeTimeBegin());
                freeTime.setTimeEnd(freeTimeVo.getFreeTimeEnd());

                int i = freeTimeMapper.insert(freeTime);
                System.out.println("addFreeTimeVo?????????" + i);
                if (i == 0) {
                    return "????????????";
                } else {
                    return "????????????";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "???????????? ??????????????? ?????????ID";
        }
    }

    @Override
    public List<ContactVo> getAllContactVos() {
        ContactVoExample contactVoExample = new ContactVoExample();
        ContactVoExample.Criteria criteria = contactVoExample.createCriteria();
        criteria.andIdIsNotNull();
        return contactVoMapper.selectByExample(contactVoExample);
    }

    @Override
    public List<ContactVo> getContactVoByNameLike(String name) {
        ContactVoExample contactVoExample = new ContactVoExample();
        ContactVoExample.Criteria criteria = contactVoExample.createCriteria();
        criteria.andNameLike("%" + name + "%");
        return contactVoMapper.selectByExample(contactVoExample);
    }

    @Override
    public String updateContactVo(ContactVo contactVo) {
        Contact contact = new Contact();
        contact.setId(contactVo.getId());
        contact.setStaffId(contactVo.getStaffId());
        contact.setContactDetails(contactVo.getContactDetails());
        contact.setContactValue(contactVo.getContactValue());

        int i = contactMapper.updateByPrimaryKeySelective(contact);
        System.out.println("updateContactVo??????:"+i);
        if (i==0){
            return "???????????????????????????????????????";
        }else{
            return "????????????";
        }
    }

    @Override
    public String deleteContactVo(String contactId) {
        try{
            int i = contactMapper.deleteByPrimaryKey(contactId);
            if (i==0){
                return "????????????";
            }else{
                return "????????????";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "????????????";
        }
    }

    @Override
    public String addContactVo(ContactVo contactVo) {
        try{
            Contact check = contactMapper.selectByPrimaryKey(contactVo.getId());
            if (check != null) {//?????????????????????????????????
                check.setContactDetails(contactVo.getContactDetails());
                check.setContactValue(contactVo.getContactValue());

                System.out.println(check.getId());
                int i = contactMapper.updateByPrimaryKeySelective(check);
                System.out.println("updateContactVo?????????" + i);
                if (i == 0) {
                    return "???????????????????????????????????????";
                } else {
                    return "????????????";
                }
            } else {  //???????????????????????????
                Contact contact = new Contact();
                String staffId = contactVo.getStaffId();
                contact.setId(staffId.substring(staffId.length()-2) + GetTime.getNowTimeString("yyMMddHHmm") + GetRandom.getRandom(0, 99));
                contact.setStaffId(contactVo.getStaffId());
                contact.setContactDetails(contactVo.getContactDetails());
                contact.setContactValue(contactVo.getContactValue());

                int i = contactMapper.insert(contact);
                System.out.println("addContactVo?????????" + i);
                if (i == 0) {
                    return "????????????";
                } else {
                    return "????????????";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "???????????? ?????????????????? ?????????ID";
        }
    }
}
