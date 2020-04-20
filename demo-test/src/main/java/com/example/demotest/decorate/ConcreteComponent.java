package com.example.demotest.decorate;

/**
 * @author xuj231
 * @description 具体构建角色
 * @date 2020/1/7 16:00
 */
public class ConcreteComponent implements Component {
    @Override
    public void dosomething() {
        System.out.println("功能A");
    }
}
