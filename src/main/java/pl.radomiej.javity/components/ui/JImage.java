/*
 * Copyright (c) 2018 di support GmbH
 */

package pl.radomiej.javity.components.ui;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.sun.prism.Texture;
import pl.radomiej.javity.JComponent;
import pl.radomiej.javity.components.BoundaryComponent;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class JImage extends JComponent implements BoundaryComponent {

    protected Color color = Color.white;

    protected JImage(){

    }

    public JImage(String imagePath) {
    }



    @Override
    public void update() {
    }

    @Override
    public void draw() {
    }

    @Override
    public Rectangle getBoundary() {
        Vector2 position = getTransform().getPosition();
        float scaleWidth = 1 * getTransform().getScale().x;
        float scaleHeight = 1 * getTransform().getScale().y;

        Rectangle imageBound = new Rectangle(position.x - scaleWidth / 2, position.y - scaleHeight / 2, scaleWidth, scaleHeight);
        return imageBound;
    }

    public void setColor(Color newColor) {
        color = newColor;
    }
}
