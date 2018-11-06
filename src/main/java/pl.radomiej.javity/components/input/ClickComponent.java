/*
 * Copyright (c) 2018 di support GmbH
 */

package pl.radomiej.javity.components.input;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import pl.radomiej.javity.JComponent;
import pl.radomiej.javity.JInput;
import pl.radomiej.javity.components.BoundaryComponent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ClickComponent extends JComponent {
    private final Runnable onClickListenr;
    private boolean exhaustedTransparent;

    public ClickComponent(Runnable onClickListener) {
        this.onClickListenr = onClickListener;
    }

    /**
     *
     * @param onClickListenr event listener, when click occured for this component.
     * @param exhaustedTransparent Don`t check and don`t set exhausted in JInput. Example. if you click this component and other, both will be process.
     */
    public ClickComponent(Runnable onClickListenr, boolean exhaustedTransparent) {
        this(onClickListenr);
        this.exhaustedTransparent = exhaustedTransparent;
    }

    @Override
    public void update() {
        if (!JInput.isJustClicked()) return;
        if (onClickListenr == null) return;
        if (JInput.isInputExhaused() || exhaustedTransparent) return;

        Rectangle mouseBounds = new Rectangle(JInput.getX(), JInput.getY(), 1, 1);

        List<Rectangle> bounds = getBounds();
        for (Rectangle bound : bounds) {
            if (Intersector.overlaps(mouseBounds, bound)) {
                onClickListenr.run();
                if(!exhaustedTransparent) JInput.setInputExhaused();
                return;
            }
        }


    }

    private List<Rectangle> getBounds() {
        List<Rectangle> bounds = new ArrayList<>();

        Collection<JComponent> components = getGameObject().getAllComponents();
        for(JComponent component : components){
            if(component instanceof BoundaryComponent){
                BoundaryComponent boundaryComponent = (BoundaryComponent) component;
                bounds.add(boundaryComponent.getBoundary());
            }
        }

        return bounds;
    }
}
