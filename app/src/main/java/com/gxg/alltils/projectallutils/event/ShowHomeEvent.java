package com.gxg.alltils.projectallutils.event;

/**
 * 作者：Administrator on 2017/11/23 10:29
 * 邮箱：android_gaoxuge@163.com
 */
public class ShowHomeEvent {
    public static final String NAME = "showhome";
    public String name;
    public int type;

    public ShowHomeEvent(String name, int type) {
        this.name = name;
        this.type = type;
    }
}
