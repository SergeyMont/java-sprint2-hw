package controller;

import model.EpicTask;
import model.Status;
import model.SubTask;
import model.Task;

import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {

    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HashMap<Integer, SubTask> subTasks = new HashMap<>();
    private HashMap<Integer, EpicTask> epicTasks = new HashMap<>();

    //    Получение списка всех задач.
    public ArrayList<Task> findAllTasks() {
        ArrayList<Task> allTasks = new ArrayList<>(tasks.values());
        allTasks.addAll(subTasks.values());
        return allTasks;
    }

    //    Получение списка всех эпиков.
    public ArrayList<Task> findAllEpicTask() {
        ArrayList<Task> allEpic = new ArrayList<>(epicTasks.values());
        return allEpic;
    }

    //    Получение списка всех подзадач определённого эпика.
    public ArrayList<SubTask> findTaskByEpic(EpicTask epic) {
        return epic.getSubTasks();
    }

    //    Получение задачи любого типа по идентификатору.
    public Task findTaskById(int id) {
        if (tasks.containsKey(id)) {
            return tasks.get(id);
        }
        return null;
    }

    public SubTask findSubtaskById(int id) {
        if (subTasks.containsKey(id)) {
            return subTasks.get(id);
        }
        return null;
    }

    public EpicTask findEpicTaskById(int id) {
        if (epicTasks.containsKey(id)) {
            return epicTasks.get(id);
        }
        return null;
    }

    //    Добавление новой задачи, эпика и подзадачи. Сам объект должен передаваться в качестве
    //    параметра.
    public void addNewTask(Task task) {
        Task t = new Task();
        if (t.compareClass(task)) {
            tasks.put(task.getId(), task);
        }

        EpicTask epic = new EpicTask();
        if (epic.compareClass(task)) {
            epicTasks.put(task.getId(), (EpicTask) task);
        }

        SubTask sub = new SubTask();
        if (sub.compareClass(task)) {
            sub = (SubTask) task;
            subTasks.put(task.getId(), sub);
            epicTasks.get(sub.getEpicID()).getSubTasks().add(sub);
            refreshEpicStatus();
        }
    }

    //    Обновление задачи любого типа по идентификатору. Новая версия объекта передаётся в виде
    //    параметра.
    public void updateTask(Task task) {
        Task t = new Task();
        if (t.compareClass(task)) {
            tasks.replace(task.getId(), task);
        }

        EpicTask epic = new EpicTask();
        if (epic.compareClass(task)) {
            epic = (EpicTask) task;
            epicTasks.replace(task.getId(), epic);
        }

        SubTask sub = new SubTask();
        if (sub.compareClass(task)) {
            sub = (SubTask) task;
            subTasks.replace(task.getId(), sub);
            int index = epicTasks.get(sub.getEpicID()).getSubTasks().indexOf(sub);
            epicTasks.get(sub.getEpicID()).getSubTasks().set(index, sub);
            refreshEpicStatus();
        }
    }

    //    Удаление ранее добавленных задач — всех и по идентификатору.
    public void removeAll() {
        tasks.clear();
        epicTasks.clear();
        subTasks.clear();
    }

    public void removeTaskById(int id) {
        if (tasks.containsKey(id)) {
            tasks.remove(id);
        } else {
            System.out.println("Такой задачи с ID: " + id + " не существует");
        }
    }

    public void removeEpicTaskByID(int id) {
        if (epicTasks.containsKey(id)) {
            epicTasks.remove(id);
        } else {
            System.out.println("Такой задачи с ID: " + id + " не существует");
        }
    }

    public void removeSubTaskByID(int id) {
        if (subTasks.containsKey(id)) {
            subTasks.remove(id);
        } else {
            System.out.println("Такой задачи с ID: " + id + " не существует");
        }
    }

    public void refreshEpicStatus() {
        for (EpicTask ep : epicTasks.values()) {
            if (ep.getSubTasks().isEmpty()) {
                ep.setStatus(Status.NEW);
            } else {
                boolean isNewStatus = false;
                boolean isDoneStatus = false;
                for (SubTask sub : ep.getSubTasks()) {
                    if (sub.getStatus() == Status.NEW) {
                        isNewStatus = true;
                    } else if (sub.getStatus() == Status.DONE) {
                        isDoneStatus = true;
                    }
                }
                if (isNewStatus && !isDoneStatus) {
                    ep.setStatus(Status.NEW);
                } else if (isDoneStatus && !isNewStatus) {
                    ep.setStatus(Status.DONE);
                } else {
                    ep.setStatus(Status.IN_PROGRESS);
                }
            }
        }
    }
}
