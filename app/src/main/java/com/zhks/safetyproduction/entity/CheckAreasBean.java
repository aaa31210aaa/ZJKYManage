package com.zhks.safetyproduction.entity;

import java.util.List;

public class CheckAreasBean {

    private String id;
    private String pid;
    private String name;
    private String isParent;
    private String open;
    private List<?> checkAreas;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIsParent() {
        return isParent;
    }

    public void setIsParent(String isParent) {
        this.isParent = isParent;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public List<?> getCheckAreas() {
        return checkAreas;
    }

    public void setCheckAreas(List<?> checkAreas) {
        this.checkAreas = checkAreas;
    }
}
