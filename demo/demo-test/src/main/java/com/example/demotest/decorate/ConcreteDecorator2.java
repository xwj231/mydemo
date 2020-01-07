package com.example.demotest.decorate;

/**
 * @author xuj231
 * @description 具体的装饰角色2
 * @date 2020/1/7 16:50
 */
public class ConcreteDecorator2 extends Decorator {

    public ConcreteDecorator2(Component component) {
        super(component);
    }

    @Override
    public void dosomething() {
        super.dosomething();
        this.doAnotherThing();
    }
    
    private void doAnotherThing() {
        System.out.println("功能C");
    }
}
