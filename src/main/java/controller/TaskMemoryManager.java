package controller;

import model.EpicTask;
import model.SubTask;
import model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TaskMemoryManager<T extends Task> implements TaskManager<T> {
    private HashMap<Integer, T> tasks = new HashMap<>();

    @Override
    public List<T> findAllTask() {
        List<T> allTasks = new ArrayList<>(tasks.values());
        return allTasks;
    }

    @Override
    public T getTaskById(int id) {
        return tasks.get(id);
    }

    @Override
    public void addNewTask(T task) {
        tasks.put(task.getId(), task);
    }

    @Override
    public void updateTask(T task) {
        if (task.getClass() == EpicTask.class) {
            ((EpicTask) task).setSubTasks((ArrayList<SubTask>) ((EpicTask) tasks.get(task.getId())).getSubTasks());
        }
        tasks.replace(task.getId(), task);

    }

    @Override
    public void removeAll() {
        tasks.clear();
    }

    @Override
    public void removeTaskById(int id) {
        if (tasks.containsKey(id)) {
            tasks.remove(id);
        } else {
            System.out.println("Такой задачи с ID: " + id + " не существует");
        }
    }
}
