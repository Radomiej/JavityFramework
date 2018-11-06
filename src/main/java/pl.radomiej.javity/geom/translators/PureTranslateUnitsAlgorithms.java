/*
 * Copyright (c) 2018 di support GmbH
 */

package pl.radomiej.javity.geom.translators;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;


public class PureTranslateUnitsAlgorithms implements  TranslateUnitsAlgorithm {
    @Override
    public Vector2 translateVector2(Vector2 toTranslate) {
        return new Vector2(toTranslate.x, -toTranslate.y);
    }

    @Override
    public Vector2 translateVector2(Vector2 toTranslate, Vector2 out) {
        return out.set(toTranslate.x, -toTranslate.y);
    }

    @Override
    public Rectangle translateRectangle(Rectangle toTranslate) {
        Rectangle result = new Rectangle(toTranslate);
        result.y = -result.y;
        return result;
    }

    @Override
    public Rectangle translateRectangle(Rectangle toTranslate, Rectangle out) {
        out.set(toTranslate);
        out.y = -out.y;
        return out;
    }
}
