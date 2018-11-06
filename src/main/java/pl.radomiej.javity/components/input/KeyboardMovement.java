/*
 * Copyright (c) 2018 di support GmbH
 */

package pl.radomiej.javity.components.input;


import pl.radomiej.javity.JComponent;
import pl.radomiej.javity.JInput;
import pl.radomiej.javity.JTime;

import java.awt.event.KeyEvent;

public class KeyboardMovement extends JComponent {

    @Override
    public void update() {
        float speed = 1;
        if(JInput.isKeyPressed(KeyEvent.VK_SHIFT)){
            speed *= 10;
        }


        float deltaX = 0;
        float deltaY = 0;

        if(JInput.isKeyPressed(KeyEvent.VK_LEFT)){
            deltaX = -speed * JTime.Instance.getDelta();
        }else if(JInput.isKeyPressed(KeyEvent.VK_RIGHT)){
            deltaX = speed * JTime.Instance.getDelta();
        }

        if(JInput.isKeyPressed(KeyEvent.VK_UP)){
            deltaY = speed * JTime.Instance.getDelta();
        }else if(JInput.isKeyPressed(KeyEvent.VK_DOWN)){
            deltaY = -speed * JTime.Instance.getDelta();
        }

        if(deltaX != 0 || deltaY != 0) getTransform().setPosition(getTransform().getPosition().add(deltaX, deltaY));
        if(JInput.isKeyPressed(KeyEvent.VK_CONTROL)){
            System.out.println("GameObject name: " + getGameObject().getName() + " position: " + getTransform().getPosition());
        }
    }
}
