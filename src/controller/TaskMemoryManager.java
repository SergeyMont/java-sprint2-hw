package controller;

import model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TaskMemoryManager implements TaskController {
    private HashMap<Integer, Task> tasks = new HashMap<>();

    @Override
    public List findAllTask() {
        List<Task> allTasks = new ArrayList<>(tasks.values());
        return allTasks;
    }

    @Override
    public Task getTaskById(int id) {
        return tasks.get(id);
    }

    @Override
    public void addNewTask(Task task) {
        tasks.put(task.getId(), task);
    }

    @Override
    public void updateTask(Task task) {
        tasks.replace(task.getId(), task);

    }

    @Override
    public void removeAll() {
        tasks.clear();
    }

    @Override
    public void removeTaskById(int id) {
        tasks.remove(id);
    }
}
