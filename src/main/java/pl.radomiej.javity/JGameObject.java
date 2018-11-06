/*
 * Copyright (c) 2018 di support GmbH
 */

package pl.radomiej.javity;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.radomiej.javity.components.Transform;

import java.util.*;


public class JGameObject {
    private static final Logger log = LoggerFactory.getLogger(JGameObject.class);

    public static void dontDestroyOnLoad(JGameObject gameObject) {
        gameObject.setNotDestroyOnLoad(true);
    }

    protected String name = "GameObject";
    protected String tag;
    protected String objectId;


    private transient boolean started, notDestroyOnLoad;
    private boolean enabled = true;
    private boolean destroy, prefab = false;
    private Map<String, JComponent> componentsMap = new LinkedHashMap<>();
    private List<JComponent> components = new ArrayList<>();
    private transient Transform transform;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String prefabId) {
        this.objectId = prefabId;
    }

    public <T extends JComponent> boolean hasComponent(Class<T> component) {
        return getComponent(component) != null;
    }

    public void start() {
        started = true;
    }

    public void awake() {
        transform = getComponent(Transform.class);
    }

    public JGameObject() {
        createTransform();
    }

    private void createTransform() {
        transform = new Transform();
        addComponent(transform);
    }


    public void addComponent(JComponent component) {
//        if(hasComponent(component.getClass())) {
//            log.error("JGameObject", "Component current exist in this object: " + this + " component: " + component);
//            return;
//        }

        component.setGameObject(this);
        componentsMap.put(component.getClass().getName(), component);
        components.add(component);

//        JTime.Instance.runTaskInNextFrame(new Task() {
//            @Override
//            public void invoke() {
//                component.awake();
//                component.start();
//            }
//        });
    }

    public <T extends JComponent> List<T> getComponentsInChildren(Class<T> class1) {
        List<JGameObject> children = JSceneManager.getCurrentScene().findGameObjectsWithParent(this);
        List<T> results = new ArrayList<>(children.size());
        for (JGameObject child : children) {
            for (JComponent component : child.components) {
                if (class1.isAssignableFrom(component.getClass())) {
                    results.add((T) component);
                }
            }
        }
        return results;
    }

    public <T extends JComponent> T getComponentInChildren(Class<T> class1) {
        List<JGameObject> children = JSceneManager.getCurrentScene().findGameObjectsWithParent(this);
        List<T> results = new ArrayList<>(children.size());
        for (JGameObject child : children) {
            for (JComponent component : child.components) {
                if (class1.isAssignableFrom(component.getClass())) {
                    return (T) component;
                }
            }
        }
        return null;
    }

    public <T extends JComponent> T getComponentInParent(Class<T> class1) {
        return transform.getParent().getComponent(class1);
    }

    public <T extends JComponent> List<T> getComponentsInParent(Class<T> class1) {
        return transform.getParent().getComponents(class1);
    }

    @SuppressWarnings("unchecked")
    public <T extends JComponent> List<T> getComponents(Class<T> componentType) {
        ArrayList<T> validComponents = new ArrayList<T>();
        for (JComponent component : components) {
            if (component.getClass().isAssignableFrom(componentType)) {
                validComponents.add((T) component);
            }
        }
        return validComponents;
    }

    public Collection<JComponent> getAllComponents() {
        ArrayList<JComponent> results = new ArrayList<JComponent>(this.components);
        return results;
    }

    public <T extends JComponent> T getComponent(Class<T> componentType) {
        JComponent component = componentsMap.get(componentType.getName());
        if (component == null) return null;
        return componentType.cast(component);
    }

    public JComponent getComponent(String componentName) {
        for (JComponent component : componentsMap.values()) {
            if (component.getClass().getSimpleName().equalsIgnoreCase(componentName)) {
                return component;
            }
        }
        return null;
    }

    static void destroy(JComponent componentToRemove) {
        if (componentToRemove == null) {
            log.error("GameObject:destroy", "componentToRemove is null");
            return;
        }
        if (componentToRemove.getGameObject() == null) {
            log.error("GameObject:destroy", "GameObject is null for Component");
            return;
        }
        if (componentToRemove.isEnabled()) componentToRemove.onDisable();
        componentToRemove.remove();
        componentToRemove.getGameObject().removeComponent(componentToRemove);
    }

    void removeComponent(JComponent componentToRemove) {
        JComponent remove = componentsMap.get(componentToRemove.getClass().getName());
        if (remove != null && remove.equals(componentToRemove)) {
            componentsMap.remove(componentToRemove.getClass().getName());
            //Fill if we have other the same class component;
            for (JComponent component : components) {
                if (componentToRemove.getClass() != component.getClass()) continue;

                componentsMap.put(componentToRemove.getClass().getName(), component);
                break;
            }
        }
        components.remove(componentToRemove);
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "GameObject name: " + getName() + " tag: " + getTag() + " uuid: " + getObjectId();
    }

    public Transform getTransform() {
        return transform;
    }

    public void setNotDestroyOnLoad(boolean notDestroyOnLoad) {
        this.notDestroyOnLoad = notDestroyOnLoad;
    }

    public boolean isDontDestroy() {
        return notDestroyOnLoad;
    }

    public boolean isStarted() {
        return started;
    }

    public void destroy() {
        destroy = true;

        for (JComponent component : getAllComponents()) {
            destroy(component);
        }

        componentsMap.clear();
//		componentsMap = null;
    }


    public void setEnabled(boolean enabled) {
        setEnabled(enabled, true);
    }

    public void setEnabled(boolean enabled, boolean propagateOnChilderen) {
        if (this.enabled == enabled)
            return;

        this.enabled = enabled;
        if (enabled) {
            for (JComponent component : componentsMap.values()) {
                component.onEnabled();
            }
        } else {
            for (JComponent component : componentsMap.values()) {
                component.onDisable();
            }
        }

        if (!propagateOnChilderen) return;

        List<JGameObject> children = JSceneManager.getCurrentScene().findGameObjectsWithParent(this);
        for (JGameObject child : children) {
            child.setEnabled(enabled, true);
        }
    }

    public boolean isDestroy() {
        return destroy;
    }

    // TODO change to Prefab Object allows use child-parent structure
    public boolean isPrefab() {
        return prefab;
    }

    public void setTransform(Transform transform2) {
        this.transform = transform2;
    }

    public boolean isEnabled() {
        return enabled;
    }

}
