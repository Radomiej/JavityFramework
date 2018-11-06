/*
 * Copyright (c) 2018 di support GmbH
 */

package pl.radomiej.javity.components.ui;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import pl.radomiej.javity.JComponent;
import pl.radomiej.javity.components.BoundaryComponent;
import pl.radomiej.javity.geom.Cake;

import java.awt.*;

public class JRoundRectangle extends JComponent implements BoundaryComponent {

    private Cake[] cakes;
    private float radius;
    private float width, height;
    private Color color;


    public JRoundRectangle(float width, float height, float radius, Color color) {
        cakes = new Cake[4];
        cakes[0] = new Cake(radius, 270f, 360, 100);
        cakes[1] = new Cake(radius, 180f, 270, 100);
        cakes[2] = new Cake(radius, 90f, 180f, 100);
        cakes[3] = new Cake(radius, 0f, 90f, 100);


        this.radius = radius;
        this.width = width;
        this.height = height;
        this.color = new Color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
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
        Rectangle[] simpleRectangle = computeSimpleRectangle();

        for (Rectangle rec : simpleRectangle) {
//            GLShapeDrawer.drawRectangle(rec, color);
        }

        Rectangle[] cakeRectangle = computeCakeRectangle();
        int cakeIndex = 0;
        for (Rectangle rec : cakeRectangle) {
            Cake cake = cakes[cakeIndex++];
            Vector2 position = new Vector2(rec.x + radius, rec.y + radius);
//            GLShapeDrawer.drawCake(JEngine.Instance.getOpenGL().getGL2(), cake, position, color);
        }

    }

    private Rectangle[] computeCakeRectangle() {
        float top = -height + 2 * radius;

        Rectangle[] results = new Rectangle[4];
        results[0] = new Rectangle(0, -2 * radius, radius, radius); //Bottom Left
        results[1] = new Rectangle(width - 2 * radius, -2 * radius, radius, radius); //Bottom Right
        results[2] = new Rectangle(width - 2 * radius, top - 2 * radius, radius, radius); //Top Right
        results[3] = new Rectangle(0, top - 2 * radius, radius, radius); //Top left
        for (Rectangle rec : results) {
            rec.x += getTransform().getPosition().x - width / 2;
            rec.y -= getTransform().getPosition().y - height / 2;
        }
        return results;
    }

    private Rectangle[] computeSimpleRectangle() {
        float top = -height + 2 * radius;

        Rectangle[] results = new Rectangle[5];
        results[0] = new Rectangle(radius, 0, width - 2 * radius, height - radius); //Center
        results[1] = new Rectangle(radius, 0, width - 2 * radius, radius); //Bottom
        results[4] = new Rectangle(radius, top - radius, width - 2 * radius, radius); //Top
        results[2] = new Rectangle(0, -radius, radius, height - 2 * radius); //Left
        results[3] = new Rectangle(width - radius, -radius, radius, height - 2 * radius); //Right

        for (Rectangle rec : results) {
            rec.x += getTransform().getPosition().x - width / 2;
            rec.y -= getTransform().getPosition().y - height / 2;
        }
        return results;
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
