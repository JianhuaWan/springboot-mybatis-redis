package org.spring.springboot.domain;

import java.io.Serializable;

/**
 * Created By wanjianhua
 * on 2020/7/1
 */
public class CheckStatusResult implements Serializable {
    private boolean ischeck;

    public boolean isIscheck() {
        return ischeck;
    }

    public void setIscheck(boolean ischeck) {
        this.ischeck = ischeck;
    }
}
