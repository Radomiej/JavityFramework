/*
 * Copyright (c) 2018 di support GmbH
 */

package pl.radomiej.javity.components.ui;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import pl.radomiej.javity.JComponent;
import pl.radomiej.javity.components.BoundaryComponent;
import pl.radomiej.javity.components.assets.BootstrapAssets;
import pl.radomiej.javity.hardware.JTexture;

import java.awt.*;
import java.awt.image.BufferedImage;

public class JLabel extends JComponent implements BoundaryComponent {

    private String text;

    public JLabel(String text, String fontFamily, int fontSize) {
        this(text, fontFamily, fontSize, BootstrapAssets.colors.TEXT_DARK);
    }

    public JLabel(String text, String fontFamily, int fontSize, Color textColor) {
        this.text = text;
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
        float scaleWidth = 1 * getTransform().getScale().x; //TODO fix text size
        float scaleHeight = 1 * getTransform().getScale().y; //TODO fix text size

        com.badlogic.gdx.math.Rectangle imageBound = new Rectangle(position.x - scaleWidth / 2, position.y - scaleHeight / 2, scaleWidth, scaleHeight);
        return imageBound;
    }
}
