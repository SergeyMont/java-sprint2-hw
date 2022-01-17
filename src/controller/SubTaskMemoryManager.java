package controller;

import model.SubTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SubTaskMemoryManager implements TaskManager<SubTask> {
    private HashMap<Integer, SubTask> subTasks = new HashMap<>();

    @Override
    public List findAllTask() {
        List<SubTask> allSub = new ArrayList<>(subTasks.values());
        return allSub;
    }

    @Override
    public SubTask getTaskById(int id) {
        return subTasks.get(id);
    }

    @Override
    public void addNewTask(SubTask task) {
        subTasks.put(task.getId(), task);
    }

    @Override
    public void updateTask(SubTask task) {
        subTasks.replace(task.getId(), task);

    }

    @Override
    public void removeAll() {
        subTasks.clear();
    }

    @Override
    public void removeTaskById(int id) {
        subTasks.remove(id);
    }
}
