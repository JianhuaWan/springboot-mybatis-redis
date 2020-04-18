package org.spring.springboot.controller;

import com.alibaba.fastjson.JSONObject;
import org.spring.springboot.domain.Bsbdj;
import org.spring.springboot.domain.Izuiyou;
import org.spring.springboot.domain.Pic;
import org.spring.springboot.service.IzuiyouService;
import org.spring.springboot.service.PicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
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

    @RequestMapping(value = "/api/addfile", method = RequestMethod.POST, produces = "image/png")
    @ResponseBody
    public String handleFileUpload(HttpServletRequest request) {
        MultipartHttpServletRequest params = ((MultipartHttpServletRequest) request);
        List<MultipartFile> files = ((MultipartHttpServletRequest) request)
                .getFiles("file");
        MultipartFile file = null;
        BufferedOutputStream stream = null;
        for (int i = 0; i < files.size(); ++i) {
            file = files.get(i);
            if (!file.isEmpty()) {
                try {
                    byte[] bytes = file.getBytes();
                    stream = new BufferedOutputStream(new FileOutputStream(
                            new File("C://photo//" + file.getOriginalFilename())));
                    stream.write(bytes);
                    stream.close();
                } catch (Exception e) {
                    stream = null;
                    return "You failed to upload " + i + " => "
                            + e.getMessage();
                }
            } else {
                return "You failed to upload " + i
                        + " because the file was empty.";
            }
        }
        return "upload successful";
    }

}
