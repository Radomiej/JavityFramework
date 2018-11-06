/*
 * Copyright (c) 2018 di support GmbH
 */

package pl.radomiej.javity;

public class JSceneManager {
    public static JScene getCurrentScene() {
        return JEngine.Instance.getCurrentScene();
    }


    public static void loadScene(JScene sceneToLoad) {
        JEngine.Instance.unloadAllScenes();
        addScene(sceneToLoad);
    }

    public static void addScene(JScene sceneToAdd) {
        JEngine.Instance.setCurrentScene(sceneToAdd);
        if (sceneToAdd instanceof JDefaultScene) ((JDefaultScene) sceneToAdd).create();
    }

    public static boolean unloadScene(JScene sceneToUnload) {
        if(sceneToUnload != JEngine.Instance.getCurrentScene()) return false;

        JEngine.Instance.unloadAllScenes();
        return true;

    }
}
