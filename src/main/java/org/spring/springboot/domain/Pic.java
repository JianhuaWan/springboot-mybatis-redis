package org.spring.springboot.domain;

import java.io.Serializable;

public class Pic implements Serializable {
    private String definfo;
    private String defphone;
    private String defpic;

    public String getDefinfo() {
        return definfo;
    }

    public void setDefinfo(String definfo) {
        this.definfo = definfo;
    }

    public String getDefphone() {
        return defphone;
    }

    public void setDefphone(String defphone) {
        this.defphone = defphone;
    }

    public String getDefpic() {
        return defpic;
    }

    public void setDefpic(String defpic) {
        this.defpic = defpic;
    }
}