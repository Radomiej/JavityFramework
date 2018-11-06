/*
 * Copyright (c) 2018 di support GmbH
 */

package pl.radomiej.javity.components.ui;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import pl.radomiej.javity.JComponent;
import pl.radomiej.javity.components.BoundaryComponent;

import java.awt.*;

public class JRectangle extends JComponent implements BoundaryComponent {

    private float width, height;
    private Color color;
    private Rectangle rectangle;


    public JRectangle(float width, float height, Color color) {
        this.width = width;
        this.height = height;
        this.color = new Color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
        rectangle = new Rectangle(0, 0, width, height);
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    @Override
    public void update() {
    }

    @Override
    public void draw() {
        rectangle.x = getTransform().getPosition().x - rectangle.width / 2;
        rectangle.y = -getTransform().getPosition().y + rectangle.height / 2;

//        GLShapeDrawer.drawRectangle(rectangle, color);
    }


    @Override
    public Rectangle getBoundary() {
        Vector2 position = getTransform().getPosition();
        float scaleWidth = width;
        float scaleHeight = height;

        Rectangle roundBound = new Rectangle(position.x - scaleWidth / 2, position.y - scaleHeight / 2, scaleWidth, scaleHeight);
        return roundBound;
    }
}
