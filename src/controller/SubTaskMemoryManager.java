package controller;

import model.SubTask;
import model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SubTaskMemoryManager implements TaskController{
    private HashMap<Integer, SubTask> subTasks = new HashMap<>();
    @Override
    public List findAllTask() {
        List<Task> allSub = new ArrayList<>(subTasks.values());
        return allSub;
    }

    @Override
    public Task getTaskById(int id) {
        return subTasks.get(id);
    }

    @Override
    public void addNewTask(Task task) {
        subTasks.put(task.getId(), (SubTask)task);
    }

    @Override
    public void updateTask(Task task) {
        subTasks.replace(task.getId(), (SubTask)task);

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
