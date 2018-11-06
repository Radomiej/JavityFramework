/*
 * Copyright (c) 2018 di support GmbH
 */

package pl.radomiej.javity.components.ui;


import pl.radomiej.javity.JComponent;


public class JShape extends JComponent {

    private float lenght;

    public JShape(float lenght) {
        this.lenght = lenght;


    }

    @Override
    public void update() {
    }

    @Override
    public void draw() {
        float scaleWidth = lenght * getTransform().getScale().x;
        float scaleHeight = lenght * getTransform().getScale().y;

    }
}
