/*
 * Copyright (c) 2018 di support GmbH
 */

package pl.radomiej.javity;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;
import pl.radomiej.javity.components.Transform;
import pl.radomiej.javity.serializer.JsonSceneSerializer;

import java.util.*;

public class JScene {
    private List<JGameObject> gameObjects = new ArrayList<JGameObject>();
    private HashMap<UUID, JGameObject> loadSceneObjects = new HashMap<UUID, JGameObject>();
    private boolean run;
    private transient List<JGameObject> objectToRemove = new ArrayList<JGameObject>();
    private transient List<JGameObject> objectToAdd = new ArrayList<JGameObject>();

    public void initialize() {
        run = true;
    }

    public List<JGameObject> getGameObjects() {
        return new ArrayList<JGameObject>(gameObjects);
    }

    public void destroyGameObject(JGameObject gameObject) {
        objectToRemove.add(gameObject);
    }

    public void proccessGameObjectDestroy(JGameObject gameObject) {
        gameObjects.remove(gameObject);
        gameObject.setEnabled(false);
        gameObject.destroy();
    }

    public void proccessGameObjectAdd(JGameObject gameObject) {
        awakeGameObject(gameObject);
        gameObjects.add(gameObject);
        startGameObject(gameObject);
        enableGameObject(gameObject);
    }

    public JGameObject instantiateGameObject(JGameObject gameObject, Vector2 position) {
        Json json = JsonSceneSerializer.json;
        proxyGameObject(gameObject);
        String gameObjectJson = json.toJson(gameObject);
        unproxyGameObject(gameObject);

        JGameObject newObject = json.fromJson(JGameObject.class, gameObjectJson);
        unproxyGameObject(newObject);

        Transform transform = newObject.getComponent(Transform.class);
        newObject.setTransform(transform);
        transform.setParent(null);
        transform.setPosition(position);

        // TODO move to next frame?
        if (run) {
            awakeGameObject(newObject);
        }

        gameObjects.add(newObject);

        if (run) {
            startGameObject(newObject);
            enableGameObject(newObject);
        }
        return newObject;
    }

    public JGameObject instantiateGameObject(Vector2 position) {
        JGameObject newObject = new JGameObject();

        Transform transform = newObject.getComponent(Transform.class);
        transform.setPosition(position.cpy());

        /**
         * If scene is running add to queue new game objects.
         * Otherwise simple add to gameObjects list, then they will be auto prepare on initialize scene
         * after run bulider method.
         */
        if (run) {
            objectToAdd.add(newObject);
        } else {
            gameObjects.add(newObject);
        }
        return newObject;
    }

    private void unproxyGameObject(JGameObject gameObject) {
//        proxator.unproxy(gameObject, this);
    }

    private void proxyGameObject(JGameObject gameObject) {
//        proxator.proxy(gameObject, this);
    }

    public HashMap<UUID, JGameObject> getLoadSceneObjects() {
        return loadSceneObjects;
    }



    public void awakeGameObject(JGameObject gameObject) {
        if (!gameObject.isStarted()) {
            gameObject.awake();
            for (JComponent component : gameObject.getAllComponents()) {
                component.setGameObject(gameObject);
            }
            for (JComponent component : gameObject.getAllComponents()) {
                component.awake();
            }
        }
    }


    public void startGameObject(JGameObject gameObject) {
        if (!gameObject.isStarted()) {
            for (JComponent component : gameObject.getAllComponents()) {
                component.start();
            }
            gameObject.start();
        }
    }

    public void enableGameObject(JGameObject gameObject) {
        for (JComponent component : gameObject.getAllComponents()) {
            if (component.isEnabled()) {
                component.onEnabled();
            }
        }
    }


    /**
     * @return the objectToRemove
     */
    public List<JGameObject> getObjectToRemove() {
        return objectToRemove;
    }

    /**
     * @return the objectToAdd
     */
    public List<JGameObject> getObjectToAdd() {
        return objectToAdd;
    }

    public void clearObjectsToDestroy() {
        objectToRemove.clear();
    }

    public void clearObjectsToAdd() {
        objectToAdd.clear();
    }

    public List<JGameObject> findGameObjectsWithTag(String tag) {
        List<JGameObject> results = new ArrayList<JGameObject>();
        for (JGameObject gameObject : gameObjects) {
            if (gameObject.getTag() != null && gameObject.getTag().equalsIgnoreCase(tag))
                results.add(gameObject);
        }
        return results;
    }

    public JGameObject findGameObjectWithTag(String tag) {
        for (JGameObject gameObject : gameObjects) {
            if (gameObject.getTag() != null && gameObject.getTag().equalsIgnoreCase(tag))
                return gameObject;
        }
        return null;
    }


    public void dispose() {
        for(JGameObject go : gameObjects){
            destroyGameObject(go);
        }
        run = false;
    }

    public boolean isRun() {
        return run;
    }

    public List<JGameObject> findGameObjectsWithParent(JGameObject parent) {
        List<JGameObject> children = new ArrayList<>();
        for (JGameObject child : gameObjects) {
            if (child.getTransform().getParent() == parent)
                children.add(child);
        }
        for (JGameObject child : objectToAdd) {
            if (child.getTransform().getParent() == parent)
                children.add(child);
        }
        return children;
    }
}
