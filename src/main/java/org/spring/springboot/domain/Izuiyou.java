package org.spring.springboot.domain;

import java.io.Serializable;
import java.util.List;

public class Izuiyou implements Serializable {
    private String id;
    private String name;
    private String avatar_urls;
    private String content;
    private String imgs;
    private String urls;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar_urls() {
        return avatar_urls;
    }

    public void setAvatar_urls(String avatar_urls) {
        this.avatar_urls = avatar_urls;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImgs() {
        return imgs;
    }

    public void setImgs(String imgs) {
        this.imgs = imgs;
    }

    public String getUrls() {
        return urls;
    }

    public void setUrls(String urls) {
        this.urls = urls;
    }
}