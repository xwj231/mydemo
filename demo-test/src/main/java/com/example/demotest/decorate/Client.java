package com.example.demotest.decorate;

/**
 * @author xuj231
 * @description 运行装饰模式
 * @date 2020/1/7 16:51
 */
public class Client {
    public static void main(String[] args) {
        Component component = new ConcreteDecorator2(
                new ConcreteDecorator1(
                        new ConcreteComponent()));
        component.dosomething();
    }
}
