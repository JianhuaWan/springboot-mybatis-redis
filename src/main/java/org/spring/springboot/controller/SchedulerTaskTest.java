package org.spring.springboot.controller;

import org.spring.springboot.domain.Izuiyou;
import org.spring.springboot.service.IzuiyouService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component  // 实例化对象到Spring容器
public class SchedulerTaskTest {
    private static int count = 0;
    @Autowired
    private IzuiyouService cityService;

    @Scheduled(initialDelay = 1000, fixedRate = 5000)   //第一次延迟1秒后执行，之后按fixedRate的规则每5秒执行一次
    public void initialDelayProcess() {
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(sdf.format(new Date()) + " initialDelayProcess任务执行次数：" + (count++));
        List<Izuiyou> izuiyou = cityService.getUserInfoFromAuthority();
        cityService.saveIzuiyou(izuiyou);
    }
}
