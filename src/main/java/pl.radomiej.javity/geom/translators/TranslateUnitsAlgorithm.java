/*
 * Copyright (c) 2018 di support GmbH
 */

package pl.radomiej.javity.geom.translators;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public interface TranslateUnitsAlgorithm {
    Vector2 translateVector2(Vector2 toTranslate);

    Vector2 translateVector2(Vector2 toTranslate, Vector2 out);

    Rectangle translateRectangle(Rectangle toTranslate);

    Rectangle translateRectangle(Rectangle toTranslate, Rectangle out);
}
