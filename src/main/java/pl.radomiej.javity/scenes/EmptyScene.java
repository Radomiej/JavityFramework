/*
 * Copyright (c) 2018 di support GmbH
 */

package pl.radomiej.javity.scenes;

import com.badlogic.gdx.math.Vector2;
import pl.radomiej.javity.JDefaultScene;
import pl.radomiej.javity.JEngine;
import pl.radomiej.javity.JGameObject;
import pl.radomiej.javity.components.ui.JLabel;

import java.awt.*;

public class EmptyScene extends JDefaultScene {
    @Override
    protected void create() {
        JEngine.Instance.setClearColor(Color.gray);

        JGameObject label = new JGameObject();
        JEngine.Instance.addGameObject(label, new Vector2(JEngine.Instance.getAppWidth() / 2, JEngine.Instance.getAppHeight() / 2));



    }
}
