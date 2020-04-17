package org.spring.springboot.controller;

import com.alibaba.fastjson.JSONObject;
import org.spring.springboot.domain.Bsbdj;
import org.spring.springboot.domain.Izuiyou;
import org.spring.springboot.domain.Pic;
import org.spring.springboot.service.IzuiyouService;
import org.spring.springboot.service.PicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by bysocket on 07/02/2017.
 * show variables like '%time_zone%';
 * set global time_zone='+8:00';
 */
@RestController
public class PicController {

    @Autowired
    private PicService picService;

    @ResponseBody
    @RequestMapping(value = "/api/addpic", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String insertData(@RequestBody JSONObject jsonParam) {
        Pic pic = (Pic) JSONObject.toJavaObject(jsonParam, Pic.class);
        try {
            Long result = picService.saveCity(pic);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
