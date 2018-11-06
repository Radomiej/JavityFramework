/*
 * Copyright (c) 2018 di support GmbH
 */

package pl.radomiej.javity.geom;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Cake {
    private int vertices;
    private List<Vector2> path = new ArrayList<>();

    public Cake(float radius, float startAngle, float endAngle, int vertices){
        this.vertices = vertices;
        startAngle += 180;
        endAngle += 180;

        float angleDelta = Math.abs(endAngle - startAngle) / vertices;
        angleDelta = angleDelta * MathUtils.degRad;

        for(int i = 0; i <= vertices; i++){
            float currentAngle = startAngle * MathUtils.degRad + i * angleDelta;
            Vector2 pixelStep = new Vector2(MathUtils.cos(currentAngle)*radius,MathUtils.sin(currentAngle)*radius);
            path.add(pixelStep);
        }
    }

    public List<Vector2> getPath() {
        return path;
    }

}
