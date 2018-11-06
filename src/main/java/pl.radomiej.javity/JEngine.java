/*
 * Copyright (c) 2018 di support GmbH
 */

package pl.radomiej.javity;

import com.badlogic.gdx.math.Vector2;
import pl.radomiej.javity.hardware.JGraphics;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.awt.*;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public enum JEngine {
    Instance;

    private Dimension appDimension = new Dimension(1920, 1080);

    public int getAppWidth() {
        return appDimension.width;
    }

    public int getAppHeight() {
        return appDimension.height;
    }

    public void setAppDimension(Dimension appDimension) {
        this.appDimension = appDimension;
    }

    private Color clearColor = Color.white;

    private boolean forceFocus = false;

    private boolean pureRun = false;

    public Color getClearColor() {
        return new Color(clearColor.getRed(), clearColor.getGreen(), clearColor.getBlue(), clearColor.getAlpha());
    }

    public void setClearColor(Color clearColor) {
        if(clearColor == null) this.clearColor = Color.white;
        else this.clearColor = clearColor;
    }



    public boolean isForceFocus() {
        return forceFocus;
    }

    public void setForceFocus(boolean forceFocus) {
        this.forceFocus = forceFocus;
    }



    private JScene scene;

    private JGraphics gl;
    public JGraphics getOpenGL(){
        return gl;
    }

    private JEngine() {
    }

    public void init(JGraphics gl) {
        this.gl = gl;
    }

    public void update() {

        // Update Mouse Input
        updateMouseXXX();

        // Pre update game objects
        JTime.Instance.tick();

        List<JGameObject> snapshoot = new LinkedList<>(scene.getGameObjects());
        Collections.reverse(snapshoot);
        for (JGameObject gameObject : snapshoot) {
            if (!gameObject.isEnabled())
                continue;

            Iterable<JComponent> components = gameObject.getAllComponents();
            for (JComponent component : components) {
                if (component.isEnabled())
                    component.preUpdate();
            }
        }
        // Update game objects
        for (JGameObject gameObject : snapshoot) {
            if (!gameObject.isEnabled())
                continue;

            Iterable<JComponent> components = gameObject.getAllComponents();
            for (JComponent component : components) {
                if (component.isEnabled())
                    component.update();
            }
        }
        //Late update
        for (JGameObject gameObject : snapshoot) {
            if (!gameObject.isEnabled())
                continue;

            Iterable<JComponent> components = gameObject.getAllComponents();
            for (JComponent component : components) {
                if (component.isEnabled())
                    component.lateUpdate();
            }
        }


        destroyObjectsToScene();
        addObjectsToScene();

        JInput.saveOldStatus();

    }

    private void updateMouseXXX() {
        JInput.tick();
    }

    private void destroyObjectsToScene() {
        // Destroy Objects to remove
        List<JGameObject> gameObjects = scene.getObjectToRemove();
        for (int x = 0; x < gameObjects.size(); x++) {
            JGameObject gameObject = gameObjects.get(x);
            scene.proccessGameObjectDestroy(gameObject);
        }
        scene.clearObjectsToDestroy();
    }

    private void addObjectsToScene() {
        // Add Objects to add
        List<JGameObject> gameObjects = scene.getObjectToAdd();
        for (int x = 0; x < gameObjects.size(); x++) {
            JGameObject gameObject = gameObjects.get(x);
            scene.proccessGameObjectAdd(gameObject);
        }
        scene.clearObjectsToAdd();
    }

    public void draw() {
      draw(true);
    }

    public void draw(boolean clearScreen){
        if(clearScreen) clearScreen();

        for (JGameObject gameObject : scene.getGameObjects()) {
            if (!gameObject.isEnabled())
                continue;

            Iterable<JComponent> components = gameObject.getAllComponents();
            for (JComponent component : components) {
                if (component.isEnabled())
                    component.draw();
            }
        }
    }

    private void clearScreen() {
        throw new NotImplementedException();
    }

    JScene getCurrentScene() {
        return scene;
    }

    void setCurrentScene(JScene sceneToLoad) {
        scene = sceneToLoad;

        // Awake all GameObjects
        for (JGameObject gameObject : scene.getGameObjects()) {
            scene.awakeGameObject(gameObject);
        }

        // Start all GameObjects
        List<JGameObject> gameObjects = scene.getGameObjects();
        for (int x = 0; x < gameObjects.size(); x++) {
            JGameObject gameObject = gameObjects.get(x);
            scene.startGameObject(gameObject);
            scene.enableGameObject(gameObject);
        }

    }

    public void unloadAllScenes() {
        if(JEngine.Instance.getCurrentScene() != null) JEngine.Instance.getCurrentScene().dispose();
        JTime.Instance.clearTasks();
    }

    public void addGameObject(JGameObject objectToAdd) {
        addGameObject(objectToAdd, objectToAdd.getTransform().getPosition());
    }

    public void addGameObject(JGameObject objectToAdd, Vector2 position) {
        if (scene == null) return;
        objectToAdd.getTransform().setPosition(position);
        scene.getObjectToAdd().add(objectToAdd);
    }



}
