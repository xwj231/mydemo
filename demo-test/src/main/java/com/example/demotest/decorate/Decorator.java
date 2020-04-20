package com.example.demotest.decorate;

/**
 * @author xuj231
 * @description 装饰角色
 * @date 2020/1/7 16:02
 */
public class Decorator implements Component {
    
    private Component component;
    
    public Decorator(Component component) {
        this.component = component;
    }
    @Override
    public void dosomething() {
        component.dosomething();
    }
}
