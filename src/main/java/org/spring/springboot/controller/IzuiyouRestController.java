package org.spring.springboot.controller;

import org.spring.springboot.domain.Bsbdj;
import org.spring.springboot.domain.City;
import org.spring.springboot.domain.Izuiyou;
import org.spring.springboot.service.IzuiyouService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by bysocket on 07/02/2017.
 * show variables like '%time_zone%';
 * set global time_zone='+8:00';
 */
@RestController
public class IzuiyouRestController {

    @Autowired
    private IzuiyouService cityService;

    @RequestMapping(value = "/api/insertdata", method = RequestMethod.GET)
    public List<Izuiyou> insertData() {
        List<Izuiyou> izuiyou = cityService.getUserInfoFromAuthority();
        try {
            cityService.saveIzuiyou(izuiyou);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return izuiyou;
    }

    @RequestMapping(value = "/api/getdata", method = RequestMethod.GET)
    public List<Bsbdj.ListBean> getData() {
        Bsbdj izuiyou = cityService.getBdjFromAuthority();
        cityService.saveBdj(izuiyou);
        return izuiyou.getList();
    }

    @RequestMapping(value = "/api/getdata/{index}/{pageSize}", method = RequestMethod.GET)
    public List<Izuiyou> getData(@PathVariable("index") int index, @PathVariable("pageSize") int pageSize) {
        List<Izuiyou> izuiyous = cityService.queryIzuiyou(index, pageSize);
        return izuiyous;
    }

    @RequestMapping(value = "/api/getranddata", method = RequestMethod.GET)
    public List<Izuiyou> getrandData() {
        List<Izuiyou> izuiyous = cityService.queryIzuiyourand();
        return izuiyous;
    }

}
