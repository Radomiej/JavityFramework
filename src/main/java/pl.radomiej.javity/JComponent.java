/*
 * Copyright (c) 2018 di support GmbH
 */

package pl.radomiej.javity;

import com.badlogic.gdx.math.Vector2;
import pl.radomiej.javity.components.Transform;

public abstract class JComponent {
    private transient JGameObject gameObject;
    private transient Transform transform;
    private boolean enabled = true;


    public void setGameObject(JGameObject gameObject) {
        this.gameObject = gameObject;
        this.transform = gameObject.getComponent(Transform.class);
    }

    public JGameObject getGameObject() {
        return gameObject;
    }

    public void setEnabled(boolean enable) {
        if (enable == enabled) {
            return;
        }
        this.enabled = enable;
        if (enable) {
            onEnabled();
        } else {
            onDisable();
        }
    }

    public boolean isEnabled() {
        return enabled;
    }

    public Transform getTransform() {
        return transform;
    }

    public void awake() {
    }

    public void start() {
    }

    public void preUpdate() {
    }

    public void update() {
    }

    public void render() {
    }

    public void preRender() {
    }


    public void postRender() {
    }

    public void preGuiRender() {
    }

    public void postGuiRender() {
    }

    public void lateUpdate() {
    }

    public void remove() {
    }

    public void onDisable() {
    }

    public void onEnabled() {
    }

    public void onPause() {
    }

    public void onResume() {
    }

    public void onMouseClicked() {
    }

    public void onMouseDragged(Vector2 draggedDelta) {
    }

    public void onMousePressed() {
    }

    public void onMouseRelased() {
    }

    public void onMouseOver() {
    }

    public void draw() {
    }
}
