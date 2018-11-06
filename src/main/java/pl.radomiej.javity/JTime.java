/*
 * Copyright (c) 2018 di support GmbH
 */

package pl.radomiej.javity;



import pl.radomiej.javity.timer.Task;
import pl.radomiej.javity.timer.TimerTask;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public enum JTime {
    Instance;

    /**
     * In secounds
     */
    private float delta;
    private long lastSystemTime;

    private List<TimerTask> timerTasks = new ArrayList<TimerTask>();
    private List<Task> nextFrameTasks = new ArrayList<Task>();

    public float getDelta(){
        return delta;
    }

    public void addTask(Task task, float timeToInvoke) {
        TimerTask timerTask = new TimerTask();
        timerTask.setTask(task);
        timerTask.setTimeToInvoke(timeToInvoke);
        timerTasks.add(timerTask);
    }

    public void runTaskInNextFrame(Task task) {
        nextFrameTasks.add(task);
    }

    void tick(){
        updateDelta();
        tickNextFrameTask();

        for(int x = timerTasks.size() - 1; x >= 0; x--){
            TimerTask timerTask = timerTasks.get(x);
            timerTask.setTimeToInvoke(timerTask.getTimeToInvoke() - delta);
            if(timerTask.getTimeToInvoke() <= 0){
                timerTask.getTask().invoke();
                timerTasks.remove(x);
            }
        }
    }

    private void updateDelta() {

        long currentSystemTime = System.currentTimeMillis();
        long deltaTime = currentSystemTime - lastSystemTime;
//        System.out.println("deltaTime: " + deltaTime);
        lastSystemTime = currentSystemTime;


        delta = deltaTime / 1000f;
//        delta = Math.min(deltaTime / 1000f - JTime.Instance.delta, 0.1f);
    }

    private void tickNextFrameTask() {
        if(nextFrameTasks.isEmpty()) return;

        List<Task> tasksSnapshot = new LinkedList<>(nextFrameTasks);
        nextFrameTasks.clear();
        for(Task task : tasksSnapshot){
            task.invoke();
        }
    }

    public void clearTasks() {
        timerTasks.clear();
        nextFrameTasks.clear();
    }


}
