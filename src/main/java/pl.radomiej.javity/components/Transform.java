/*
 * Copyright (c) 2018 di support GmbH
 */

package pl.radomiej.javity.components;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import pl.radomiej.javity.JComponent;
import pl.radomiej.javity.JGameObject;

import java.util.UUID;

public class Transform extends JComponent {
    private Vector2 position = new Vector2();
    private Vector2 scale = new Vector2(1, 1);
    // private float rotation;
    private int orderZ = 0;
    private JGameObject parent;
    private Vector2 localPosition = new Vector2();
    private float localRotation = 0;
    private float absoluteRotation = 0;
    private Vector2 localScale = new Vector2(1, 1);
    private Vector2 absoluteScale = new Vector2(1, 1);
    private int localOrderZ = 0;
    private UUID uuid = UUID.randomUUID();

    public Transform() {
    }

    @Override
    public void awake() {

    }

    @Override
    public void start() {

    }

    @Override
    public void update() {

        // Scale
        if (parent != null) {
            Vector2 parentScale = parent.getTransform().getScale();
            absoluteScale.x = parentScale.x * localScale.x;
            absoluteScale.y = parentScale.y * localScale.y;
            scale.set(absoluteScale);

        } else {
            absoluteScale.set(scale);
        }

        if (parent != null) {

            Vector2 parentScale = parent.getTransform().getScale();
            Vector2 newPosition = localPosition.cpy();
            newPosition.x *= parentScale.x;
            newPosition.y *= parentScale.y;
            newPosition = newPosition.rotate(parent.getTransform().getRotation());
            newPosition.add(parent.getTransform().position);

            this.position.set(newPosition);
        }
    }

    public Vector2 getPosition() {
        return position.cpy();
    }

    public void setPosition(float x, float y) {
        Vector2 newPosition = new Vector2(x, y);
        setPosition(newPosition);
    }

    public void setPosition(Vector2 position) {
        this.position.set(position);
        if (parent != null)
            updateLocalPosition(parent);
    }

    public void setPosition(Vector3 position3d) {
        Vector2 newPosition = new Vector2(position3d.x, position3d.y);
        setPosition(newPosition);
    }

    private void updateLocalPosition(JGameObject parent) {
        if (parent.isDestroy()) {
            setParent(null);
            return;
        }

        Vector2 parentPosition = parent.getTransform().position;
        Vector2 thisPosition = this.position;
        localPosition.x = thisPosition.x - parentPosition.x;
        localPosition.y = thisPosition.y - parentPosition.y;
    }

    public Vector2 getScale() {
        if (parent == null) {
            return scale.cpy();
        } else {
            Vector2 parentScale = parent.getTransform().getScale();
            absoluteScale.x = parentScale.x * localScale.x;
            absoluteScale.y = parentScale.y * localScale.y;
            return absoluteScale.cpy();
        }
    }

    public void setScale(float scale) {
        setScale(scale, scale);
    }

    public void setScale(float scaleX, float scaleY) {
        this.scale.set(scaleX, scaleY);
    }

    public void setScale(Vector2 newScale) {
        this.scale.set(newScale);
    }

    public float getRotation() {
        float rotation = localRotation;
        if (parent != null) {
            rotation += parent.getTransform().getRotation();
        }
        return rotation;
    }

    public void setRotation(float rotation) {
        this.localRotation = rotation;
        if (parent != null) {
            this.localRotation -= parent.getTransform().getRotation();
        }
        // if (positionComponent != null)
        // positionComponent.setRotation(rotation);
    }

    public void setParent(JGameObject parent) {
        this.parent = parent;
        // localScale.set(getParentScale(parent));
        if (parent != null) {
            updateLocalPosition(parent);
        }
    }

    private Vector2 getParentScale(JGameObject parent) {
        return parent != null ? parent.getTransform().scale : new Vector2(1, 1);
    }

    public JGameObject getParent() {
        return parent;
    }

    public Vector2 getLocalPosition() {
        return localPosition;
    }

    public void setLocalPosition(Vector2 localPosition) {
        this.localPosition = localPosition;
    }

    public void setLocalScale(float scaleX, float scaleY) {
        localScale.set(scaleX, scaleY);
        if (parent == null) {
            setScale(scaleX, scaleY);
        }
    }

    public void setLocalScale(float scaleXY) {
        setLocalScale(scaleXY, scaleXY);
    }

    public void setLocalZ(int z) {
        orderZ = z;
    }

    public void setZ(int z) {
        orderZ = z;
        if (parent != null) {
            orderZ = z - parent.getTransform().getOrderZ();
        }
    }

    public int getOrderZ() {
        int realZ = orderZ;
        if (parent != null)
            realZ += parent.getTransform().getOrderZ();
        return realZ;
    }
}
