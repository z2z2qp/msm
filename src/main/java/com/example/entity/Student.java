package com.example.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.will.framework.entity.IEntity;

import java.util.Date;

/**
 * Created by Will on 2016/8/25 9:21.
 */
public class Student implements IEntity {

    private Integer stuid;
    private Integer romid;
    private String name;
    private Integer age;
    private Date born;

    public Student() {
    }

    public Student(Integer stuid, String name, Integer age) {
        this.stuid = stuid;
        this.name = name;
        this.age = age;
    }

    public Student(Integer stuid, Integer romid, String name, Integer age, Date born) {
        this.stuid = stuid;
        this.romid = romid;
        this.name = name;
        this.age = age;
        this.born = born;
    }

    public Integer getStuid() {
        return stuid;
    }

    public void setStuid(Integer stuid) {
        this.stuid = stuid;
    }

    public Integer getRomid() {
        return romid;
    }

    public void setRomid(Integer romid) {
        this.romid = romid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JSONField(serialize = false)//不输出
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @JSONField(format = "yyyy-MM-dd")//日期格式化
    public Date getBorn() {
        return born;
    }

    public void setBorn(Date born) {
        this.born = born;
    }
}
