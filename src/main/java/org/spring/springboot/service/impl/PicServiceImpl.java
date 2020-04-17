package org.spring.springboot.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spring.springboot.Utils.Utils;
import org.spring.springboot.dao.BdjDao;
import org.spring.springboot.dao.IzuiyouDao;
import org.spring.springboot.dao.PicDao;
import org.spring.springboot.domain.*;
import org.spring.springboot.service.IzuiyouService;
import org.spring.springboot.service.PicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;

/**
 * 业务逻辑实现类
 * <p>
 * Created by bysocket on 07/02/2017.
 */
@Service
public class PicServiceImpl implements PicService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PicServiceImpl.class);

    @Autowired
    private PicDao izuiyouDao;


    @Autowired
    private RedisTemplate redisTemplate;


    @Override
    public List<Izuiyou> findALLCity() {
        return null;
    }

    @Override
    public Izuiyou findCityById(Long id) {
        return null;
    }

    @Override
    public Long saveCity(Pic city) {
        return izuiyouDao.saveCity(city);
    }

    @Override
    public void saveBdj(Bsbdj bsbdj) {

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
        Params paramsbean = new Params();
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
    public List<Izuiyou> queryIzuiyou(int index, int pageSize) {
        return null;
    }

    @Override
    public List<Izuiyou> queryIzuiyourand() {
        return null;
    }


}
