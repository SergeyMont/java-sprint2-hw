package controller;

import model.EpicTask;
import model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EpicTaskMemoryManager implements TaskController {
    private HashMap<Integer, EpicTask> epicTasks = new HashMap<>();

    @Override
    public List<Task> findAllTask() {
        List<Task> allEpic = new ArrayList<>(epicTasks.values());
        return allEpic;
    }

    @Override
    public EpicTask getTaskById(int id) {
        return epicTasks.get(id);
    }

    @Override
    public void addNewTask(Task epic) {
        epicTasks.put(epic.getId(), (EpicTask) epic);
    }

    @Override
    public void updateTask(Task epic) {
        epicTasks.replace(epic.getId(), (EpicTask) epic);
    }

    @Override
    public void removeAll() {
        epicTasks.clear();
    }

    @Override
    public void removeTaskById(int id) {
        if (epicTasks.containsKey(id)) {
            epicTasks.remove(id);
        } else {
            System.out.println("Такой задачи с ID: " + id + " не существует");
        }
    }
}
