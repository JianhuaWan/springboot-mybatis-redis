package org.spring.springboot.Utils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.spring.springboot.domain.Bsbdj;
import org.spring.springboot.domain.Izuiyou;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.*;

public class Utils {

    /**
     * 向目的URL发送post请求
     *
     * @param url    目的url
     * @param params 发送的参数
     * @return ResultVO
     */

    public static List<Izuiyou> sendPostRequest(String url, MultiValueMap<String, Object> params) {
        RestTemplate client = new RestTemplate();
        client.getMessageConverters().add(new WxMappingJackson2HttpMessageConverter());
        HttpHeaders headers = new HttpHeaders();
        HttpMethod method = HttpMethod.POST;
//        headers.add("sign", "e45fd7a22437605f1fb12f209d89ccda");
        // 以表单的方式提交
        headers.setContentType(MediaType.APPLICATION_JSON);
        //将请求头部和参数合成一个请求
//        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(params, headers);
        HttpEntity<String> requestEntity = new HttpEntity<>("{\"filter\":\"all\",\"auto\":0,\"tab\":\"推荐\",\"direction\":\"down\",\"c_types\":[1,3,2,8,7,9,11,21,20,14],\"sdk_ver\":{\"tt\":\"1.9.6.4.2\",\"tx\":\"4.19.574\",\"tt_aid\":\"\",\"tx_aid\":\"\"},\"ad_wakeup\":2,\"h_ua\":\"Mozilla\\/5.0 (Linux; Android 9; Note9 Build\\/PKQ1.181203.001; wv) AppleWebKit\\/537.36 (KHTML, like Gecko) Version\\/4.0 Chrome\\/70.0.3538.111 Mobile Safari\\/537.36\",\"h_av\":\"4.8.8\",\"h_dt\":0,\"h_os\":28,\"h_app\":\"zuiyou\",\"h_model\":\"Note9\",\"h_did\":\"f659308b25eaba46\",\"h_nt\":1,\"h_m\":169744524,\"h_ch\":\"meizu\",\"h_ts\":1559532163267,\"token\":\"T4K0NMwSz-8zksCVOZw_223AJT2_fCtGs6IKo4_jwACzMVO5tznCdyuBPaM1l-VcLbh_b\",\"android_id\":\"f659308b25eaba46\"}", headers);
        //执行HTTP请求，将返回的结构使用ResultVO类格式化
        ResponseEntity<String> response = client.exchange(url, method, requestEntity, String.class);
        Map res = new HashMap();
        return iteraJson(response.getBody(), res);
    }

    public static Bsbdj sendGetRequest() {
        RestTemplate client = new RestTemplate();
        client.getMessageConverters().add(new WxMappingJackson2HttpMessageConverter());
        HttpHeaders headers = new HttpHeaders();
        HttpMethod method = HttpMethod.GET;
        // 以表单的方式提交
        headers.setContentType(MediaType.APPLICATION_JSON);
        //将请求头部和参数合成一个请求
        System.out.println(System.currentTimeMillis());
        String geturl = "http://d.api.budejie.com/v2/topic/list/1/0-0/budejie-android-8.1.0/0-25.json?jdk=1&market=taobao&uid=&ver=8.1.0&appname=budejie&t=" + System.currentTimeMillis() + "&client=android&from=android&udid=86630904162342&device=Note9";
        HttpEntity<String> requestEntity = new HttpEntity<>(geturl, headers);
        //执行HTTP请求，将返回的结构使用ResultVO类格式化
        ResponseEntity<Bsbdj> response = client.exchange(geturl, method, requestEntity, Bsbdj.class);
        return response.getBody();
    }

    private static List<Izuiyou> iteraJson(String str, Map res) {
        List<Izuiyou> izuiyous = new ArrayList<>();
        JSONObject fromObject = JSONObject.fromObject(str);
        JSONObject fromdata = fromObject.getJSONObject("data");
        JSONArray fromArray = JSONArray.fromObject(fromdata.getString("list"));
        for (int i = 0; i < fromArray.size(); i++) {
            Izuiyou izuiyou = new Izuiyou();
            JSONObject obj = fromArray.getJSONObject(i);
            if (obj.has("app_name")) {
                if (obj.getString("app_name").equals("zuiyou")) {
                    String content = "";
                    if (obj.has("content")) {
                        content = obj.getString("content");
                    }
                    String id = "";
                    if (obj.has("_id")) {
                        id = obj.getString("_id");
                    } else if (obj.has("id")) {
                        id = obj.getString("id");
                    }
                    JSONObject member = null;
                    if (obj.has("member")) {
                        member = obj.getJSONObject("member");
                    }
                    String name = null;
                    if (member != null && member.has("name")) {
                        name = member.getString("name");
                    }
                    JSONObject avatar_urls = null;
                    if (member.has("avatar_urls")) {
                        avatar_urls = member.getJSONObject("avatar_urls");
                    }
                    JSONObject origin = null;
                    if (avatar_urls != null && avatar_urls.has("origin")) {
                        origin = avatar_urls.getJSONObject("origin");
                    }
                    JSONArray url = null;
                    if (origin != null && origin.has("urls")) {
                        url = JSONArray.fromObject(origin.getString("urls"));
                    }
                    String headurl = null;
                    if (url != null && url.size() != 0) {
                        headurl = url.getString(0);
                    }
                    String videourl = null;//视频地址
                    List<String> listurls = null;//图片列表
                    if (obj.has("imgs")) {
                        JSONArray imgs = JSONArray.fromObject(obj.getString("imgs"));
                        for (int j = 0; j < imgs.size(); j++) {
                            //有可能是多张图片
                            listurls = new ArrayList<>();
                            JSONObject img = imgs.getJSONObject(j);
                            JSONObject urls = img.getJSONObject("urls");
                            if (urls.has("origin")) {
                                JSONObject urls_origin = urls.getJSONObject("origin");
                                JSONArray urlstemp = JSONArray.fromObject(urls_origin.getString("urls"));
                                String urlover = urlstemp.getString(0);
                                listurls.add(urlover);
                            } else if (urls.has("540")) {
                                JSONObject urls_origin = urls.getJSONObject("540");
                                JSONArray urlstemp = JSONArray.fromObject(urls_origin.getString("urls"));
                                String urlover = urlstemp.getString(0);
                                listurls.add(urlover);
                            }

                        }
                    }
                    if (obj.has("videos")) {
                        JSONObject video = obj.getJSONObject("videos");
                        Iterator videokey = video.keys();
                        String keyvideo = videokey.next().toString();
                        JSONObject valuevideo = video.getJSONObject(keyvideo);
                        JSONArray qualities = JSONArray.fromObject(valuevideo.getString("qualities"));
                        JSONObject qualities_0 = qualities.getJSONObject(0);
                        JSONArray over = JSONArray.fromObject(qualities_0.getString("urls"));
                        JSONObject over_1 = over.getJSONObject(0);
                        String over_urls = over_1.getString("url");
                        videourl = over_urls;
                    }
                    izuiyou.setId(id);
                    izuiyou.setName(name);
                    izuiyou.setAvatar_urls(headurl);
                    izuiyou.setContent(content);
                    if (listurls.size() == 1) {
                        izuiyou.setImgs(listurls.get(0));
                    }
                    izuiyou.setUrls(videourl);
                    izuiyous.add(izuiyou);
                }
            }
        }
        return izuiyous;
    }

}
