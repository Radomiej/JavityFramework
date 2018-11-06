/*
 * Copyright (c) 2018 di support GmbH
 */

package pl.radomiej.javity;

import com.badlogic.gdx.math.Vector2;

import java.awt.event.*;
import java.util.HashSet;
import java.util.Set;

public class JInput implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {
    private static JInput localInput;
    private static boolean lastIsTouched;
    private static boolean isTouched;
    private static boolean justTouched;
    private static boolean justClicked;
    private static boolean inputExhaused;
    private static Vector2 mousePosition = new Vector2();
    private static Set<Integer> pressedKeys = new HashSet<>();
    private static float freezeTime;
    public static JInput getInternalProcessor() {
        return localInput;
    }

    static {
        localInput = new JInput();
    }

    public static boolean isKeyPressed(int keyCode) {
        return pressedKeys.contains(keyCode);
    }

    public static boolean isJustTouched() {
        return justTouched;
    }

    public static boolean isJustClicked() {
        return justClicked;
    }

    public static float getX() {
        return mousePosition.x;
    }

    public static float getY() {
        return mousePosition.y;
    }

    public static boolean isInputExhaused() {
        return inputExhaused;
    }

    public static void freezeInput(float freezeTime){
        JInput.freezeTime = freezeTime;
    }

    public static void setInputExhaused() {
        inputExhaused = true;
    }

    public static void tick() {
        if(freezeTime > 0){
            freezeTime -= JTime.Instance.getDelta();
            inputExhaused = true;
            justTouched = false;
            return;
        }

        inputExhaused = false;

        if (isTouched && !lastIsTouched) {
            justTouched = true;
        } else {
            justTouched = false;
        }


    }

    public static void saveOldStatus() {
        lastIsTouched = isTouched;
        if(justClicked) JTime.Instance.runTaskInNextFrame(() ->{ justClicked = false;});
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        pressedKeys.add(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        pressedKeys.remove(e.getKeyCode());
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        mouseMoved(e);
        justClicked = true;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        mouseMoved(e);
        isTouched = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mouseMoved(e);
        isTouched = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mousePosition.set(e.getX(), inverseY(e.getY()) - 1);
//        System.out.println("mouse position: " + mousePosition);
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {

    }

    public static int inverseY(int awtY) {
        return JEngine.Instance.getAppHeight() - awtY;
    }

    public static float inverseY(float y) {
        return JEngine.Instance.getAppHeight() - y;
    }
}
