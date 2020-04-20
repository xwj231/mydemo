package com.example.demotest.decorate;

/**
 * @author xuj231
 * @description 具体的装饰角色1
 * @date 2020/1/7 16:48
 */
public class ConcreteDecorator1 extends Decorator {
    public ConcreteDecorator1(Component component) {
        super(component);
    }

    @Override
    public void dosomething() {
        super.dosomething();
        this.doAnotherThing();
    }
    
    private void doAnotherThing() {
        System.out.println("功能B");
    }
}
