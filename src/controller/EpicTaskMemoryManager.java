package controller;

import model.EpicTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EpicTaskMemoryManager implements TaskManager<EpicTask> {
    private HashMap<Integer, EpicTask> epicTasks = new HashMap<>();

    @Override
    public List<EpicTask> findAllTask() {
        List<EpicTask> allEpic = new ArrayList<>(epicTasks.values());
        return allEpic;
    }

    @Override
    public EpicTask getTaskById(int id) {
        return epicTasks.get(id);
    }

    @Override
    public void addNewTask(EpicTask epic) {
        epicTasks.put(epic.getId(), epic);
    }

    @Override
    public void updateTask(EpicTask epic) {
        epicTasks.replace(epic.getId(), epic);
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
