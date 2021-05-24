package com.yutongdxTop.LaborDispatching.service;

import com.yutongdxTop.LaborDispatching.domain.pojo.Contact;
import com.yutongdxTop.LaborDispatching.domain.pojo.FreeTime;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FreelancerService {

    List<FreeTime> getAllFreeTimes(String staffId);

    String addFreeTime(@Param("freeTime") FreeTime freeTime);

    String deleteFreeTime(String id);

    String updateFreeTime(FreeTime freeTime);

    List<Contact> getAllContacts(String staffId);

    String addContact(@Param("contact") Contact contact);

    String deleteContact(String id);

    String updateContact(Contact contact);

}
