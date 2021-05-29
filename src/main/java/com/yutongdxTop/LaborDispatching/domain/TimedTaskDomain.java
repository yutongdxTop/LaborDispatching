package com.yutongdxTop.LaborDispatching.domain;

import com.yutongdxTop.LaborDispatching.domain.pojo.FreeTime;
import com.yutongdxTop.LaborDispatching.domain.pojo.FreeTimeExample;
import com.yutongdxTop.LaborDispatching.mapper.FreeTimeMapper;
import com.yutongdxTop.LaborDispatching.util.GetTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Component
public class TimedTaskDomain {  //定时任务域

    @Autowired
    FreeTimeMapper freeTimeMapper;

    @Scheduled(cron = "0 0 0 ? * *")
    public void updateFreeTime() {   //每天零点更新空闲时间

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date dateZero = calendar.getTime();  //每天零点

        FreeTimeExample freeTimeExample = new FreeTimeExample();
        FreeTimeExample.Criteria criteria = freeTimeExample.createCriteria();
        criteria.andIdIsNotNull();
        List<FreeTime> allFreeTime = freeTimeMapper.selectByExample(freeTimeExample); //获取所有时间表

        try {
            for (FreeTime freeTime : allFreeTime) {
                Date timeEnd = null;
                if (!Objects.equals(freeTime.getTimeEnd(), "∞")) {
                    timeEnd = GetTime.dateTransform(freeTime.getTimeEnd());
                }
                if (timeEnd != null && timeEnd.before(dateZero)) {  //如果空闲结束时间在今天之前，则删除该时间表
                    freeTimeMapper.deleteByPrimaryKey(freeTime.getId());
                } else {
                    Date timeBegin = GetTime.dateTransform(freeTime.getTimeBegin());
                    if (timeBegin.before(dateZero)) {    //如果空闲开始时间在今天之前，则设置为今天开始空闲时间
                        freeTime.setTimeBegin(GetTime.stringTransform(dateZero));
                        freeTimeMapper.updateByPrimaryKey(freeTime);
                    }
                }
            }
            System.out.println("任务执行时间：" + LocalDateTime.now());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
