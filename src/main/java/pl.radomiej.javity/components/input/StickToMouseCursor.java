/*
 * Copyright (c) 2018 di support GmbH
 */

package pl.radomiej.javity.components.input;

import com.badlogic.gdx.math.Vector2;
import pl.radomiej.javity.JComponent;
import pl.radomiej.javity.JInput;

public class StickToMouseCursor extends JComponent {

    @Override
    public void update() {
        getTransform().setPosition(new Vector2(JInput.getX(), JInput.getY()));
    }
}
