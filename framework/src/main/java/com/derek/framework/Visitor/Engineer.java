package com.derek.framework.Visitor;

import java.util.Random;

public class Engineer extends Staff {

    public Engineer(String name) {
        super(name);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visite(this);
    }

    public int getCodeLines(){
        return new Random().nextInt(10*1000);
    }
}