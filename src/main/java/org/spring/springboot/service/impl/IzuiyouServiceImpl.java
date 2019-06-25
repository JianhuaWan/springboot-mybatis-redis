package org.spring.springboot.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spring.springboot.Utils.Utils;
import org.spring.springboot.dao.BdjDao;
import org.spring.springboot.dao.IzuiyouDao;
import org.spring.springboot.domain.*;
import org.spring.springboot.service.IzuiyouService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * 业务逻辑实现类
 * <p>
 * Created by bysocket on 07/02/2017.
 */
@Service
public class IzuiyouServiceImpl implements IzuiyouService {

    private static final Logger LOGGER = LoggerFactory.getLogger(IzuiyouServiceImpl.class);

    @Autowired
    private IzuiyouDao izuiyouDao;

    @Autowired
    private BdjDao bdjDao;

    @Autowired
    private RedisTemplate redisTemplate;

    public Izuiyou findCityById(Long id) {
        // 从缓存中获取城市信息
        String key = "zuiyou_" + id;
        ValueOperations<String, Izuiyou> operations = redisTemplate.opsForValue();

        // 缓存存在
        boolean hasKey = redisTemplate.hasKey(key);
        if (hasKey) {
            Izuiyou listBean = operations.get(key);

            LOGGER.info("CityServiceImpl.findCityById() : 从缓存中获取了城市 >> " + listBean.toString());
            return listBean;
        }

        // 从 DB 中获取城市信息
        Izuiyou listBean = izuiyouDao.findById(id);

        // 插入缓存
        operations.set(key, listBean);
        LOGGER.info("CityServiceImpl.findCityById() : 城市插入缓存 >> " + listBean.toString());

        return listBean;
    }


    public List<Izuiyou> findALLCity() {
        // 从缓存中获取城市信息
        String key = "city_";
        ValueOperations<String, List<Izuiyou>> operations = redisTemplate.opsForValue();

        // 缓存存在
        boolean hasKey = redisTemplate.hasKey(key);
        if (hasKey) {
            List<Izuiyou> city = operations.get(key);

            LOGGER.info("CityServiceImpl.findCityById() : 从缓存中获取了城市 >> " + city.toString());
            return city;
        }

        // 从 DB 中获取城市信息
        List<Izuiyou> city = izuiyouDao.findAllCity();

        // 插入缓存
        operations.set(key, city);
        LOGGER.info("CityServiceImpl.findCityById() : 城市插入缓存 >> " + city.toString());

        return city;
    }

    @Override
    public void saveIzuiyou(List<Izuiyou> izuiyous) {
        for (int i = 0; i < izuiyous.size(); i++) {
            izuiyouDao.saveCity(izuiyous.get(i));
        }
    }

    @Override
    public void saveBdj(Bsbdj bsbdj) {
        for (int i = 0; i < bsbdj.getList().size(); i++) {
            BsbdjChild bsbdjChild = new BsbdjChild();
            bsbdjChild.setId(bsbdj.getList().get(i).getId());
            bsbdjChild.setContent(bsbdj.getList().get(i).getText());
            bsbdjChild.setHeadurl(bsbdj.getList().get(i).getU().getHeader().get(0));
            bsbdjChild.setName(bsbdj.getList().get(i).getU().getName());
            if (bsbdj.getList().get(i).getType().equals("gif")) {
                bsbdjChild.setImgurl(bsbdj.getList().get(i).getGif().getImages().get(0));
            } else if (bsbdj.getList().get(i).getType().equals("video")) {
                bsbdjChild.setVideourl(bsbdj.getList().get(i).getVideo().getVideo().get(0));
            } else if (bsbdj.getList().get(i).getType().equals("image")) {
                bsbdjChild.setImgurl(bsbdj.getList().get(i).getImage().getBig().get(0));
            }
            if (bsbdj.getList().get(i).getTop_comments() != null && bsbdj.getList().get(i).getTop_comments().size() != 0) {
                bsbdjChild.setReply(bsbdj.getList().get(i).getTop_comments().get(0).getContent());
            }

            bdjDao.saveCity(bsbdjChild);
        }
    }


    @Override
    public Long deleteCity(Long id) {

        Long ret = izuiyouDao.deleteCity(id);

        // 缓存存在，删除缓存
        String key = "city_" + id;
        boolean hasKey = redisTemplate.hasKey(key);
        if (hasKey) {
            redisTemplate.delete(key);

            LOGGER.info("CityServiceImpl.deleteCity() : 从缓存中删除城市 ID >> " + id);
        }
        return ret;
    }

    @Override
    public List<Izuiyou> getUserInfoFromAuthority() {
        String authorizeUrl = "http://api.izuiyou.com/index/recommend?sign=e45fd7a22437605f1fb12f209d89ccda";
        org.spring.springboot.domain.Params paramsbean = new org.spring.springboot.domain.Params();
        List<Integer> integers = new ArrayList<>();
        integers.add(1);
        integers.add(3);
        integers.add(2);
        integers.add(8);
        integers.add(7);
        integers.add(9);
        integers.add(11);
        integers.add(21);
        integers.add(20);
        integers.add(14);
        paramsbean.setC_types(integers);
        Params.SdkVerBean sdkVerBean = new Params.SdkVerBean();
        sdkVerBean.setTt("1.9.6.4.2");
        sdkVerBean.setTt_aid("");
        sdkVerBean.setTx("4.19.574");
        sdkVerBean.setTx_aid("");
        paramsbean.setSdk_ver(sdkVerBean);
        paramsbean.setH_ts(Long.parseLong("1559532163267"));
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("filter", "all");
        params.add("auto", 0);
        params.add("tab", "推荐");
        params.add("direction", "down");
        params.add("c_types", paramsbean.getC_types());
        params.add("sdk_ver", paramsbean.getSdk_ver());
        params.add("ad_wakeup", 2);
        params.add("h_ua", "Mozilla/5.0 (Linux; Android 9; Note9 Build/PKQ1.181203.001; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/70.0.3538.111 Mobile Safari/537.36");
        params.add("h_av", "4.8.8");
        params.add("h_dt", 0);
        params.add("h_os", 28);
        params.add("h_app", "zuiyou");
        params.add("h_model", "Note9");
        params.add("h_did", "f659308b25eaba46");
        params.add("h_nt", 1);
        params.add("h_m", 169744524);
        params.add("h_ch", "meizu");
        params.add("h_ts", paramsbean.getH_ts());
        params.add("token", "T4K0NMwSz-8zksCVOZw_223AJT2_fCtGs6IKo4_jwACzMVO5tznCdyuBPaM1l-VcLbh_b");
        params.add("android_id", "f659308b25eaba46");


//        String jsonString = "{\"filter\":\"all\",\"auto\":0,\"tab\":\"推荐\",\"direction\":\"down\",\"c_types\":[1,3,2,8,7,9,11,21,20,14],\"sdk_ver\":{\"tt\":\"1.9.6.4.2\",\"tx\":\"4.19.574\",\"tt_aid\":\"\",\"tx_aid\":\"\"},\"ad_wakeup\":2,\"h_ua\":\"Mozilla\\/5.0 (Linux; Android 9; Note9 Build\\/PKQ1.181203.001; wv) AppleWebKit\\/537.36 (KHTML, like Gecko) Version\\/4.0 Chrome\\/70.0.3538.111 Mobile Safari\\/537.36\",\"h_av\":\"4.8.8\",\"h_dt\":0,\"h_os\":28,\"h_app\":\"zuiyou\",\"h_model\":\"Note9\",\"h_did\":\"f659308b25eaba46\",\"h_nt\":1,\"h_m\":169744524,\"h_ch\":\"meizu\",\"h_ts\":1559532163267,\"token\":\"T4K0NMwSz-8zksCVOZw_223AJT2_fCtGs6IKo4_jwACzMVO5tznCdyuBPaM1l-VcLbh_b\",\"android_id\":\"f659308b25eaba46\"}";
//        JSONObject object = JSONObject.parseObject(jsonString);
//        Iterator it = object.keySet().iterator();
//        StringBuilder sb = new StringBuilder("{");
//        while (it.hasNext()) {
//            String key = (String) it.next();
//            Object value = object.getString(key);
//            if (value == null) {
//                sb.append(JSON.toJSONString(key)).append(":").append(value).append(",");
//            } else {
//                List<String> list = Collections.singletonList(value.toString());
//                sb.append(JSON.toJSONString(key)).append(":").append(JSON.toJSONString(list)).append(",");
//            }
//        }
//        sb.append("}");
//        sb.deleteCharAt(sb.length()-2);
//        System.out.println(sb.toString());
//        LinkedMultiValueMap params = JSON.parseObject(sb.toString(), LinkedMultiValueMap.class);
//        System.out.println(params);


        //发送Post数据并返回数据
        List<Izuiyou> resultVo = Utils.sendPostRequest(authorizeUrl, params);
        return resultVo;
    }

    @Override
    public Bsbdj getBdjFromAuthority() {
        Bsbdj resultVo = Utils.sendGetRequest();
        return resultVo;
    }

    @Override
    public List<Izuiyou> queryIzuiyou(int index, int pagesize) {
        // 从缓存中获取城市信息
       /* String key = "data_" + index;
        ValueOperations<String, List<Izuiyou>> operations = redisTemplate.opsForValue();

        // 缓存存在
        boolean hasKey = redisTemplate.hasKey(key);
        if (hasKey) {
            List<Izuiyou> izuiyous = operations.get(key);

            LOGGER.info("CityServiceImpl.findCityById() : 从缓存中获取了数据 >> " + izuiyous.toString());
            return izuiyous;
        }*/

        // 从 DB 中获取城市信息
        List<Izuiyou> izuiyous = izuiyouDao.findData(index, pagesize);
        // 插入缓存
//        operations.set(key, izuiyous);
//        LOGGER.info("CityServiceImpl.findCityById() : 数据插入缓存 >> " + izuiyous.toString());
        return izuiyous;
    }

    @Override
    public List<Izuiyou> queryIzuiyourand() {
        // 从 DB 中获取城市信息
        List<Izuiyou> izuiyous = izuiyouDao.findDatarand();
        return izuiyous;
    }

}
