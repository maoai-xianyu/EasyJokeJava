package com.mao.easyjokejava.model;

import org.litepal.crud.LitePalSupport;

/**
 * @author zhangkun
 * @time 2020-04-20 17:10
 * @Description
 */
public class Person extends LitePalSupport {

    private String name;
    private int age;
    private boolean flag;

    // 默认构造
    public Person() {
    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", flag=" + flag +
                '}';
    }
}
