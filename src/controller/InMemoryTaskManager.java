package controller;

import model.EpicTask;
import model.SubTask;
import model.Task;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class InMemoryTaskManager implements TaskManager {
    TaskController epicTaskManager = new EpicTaskMemoryManager();
    TaskController subTaskManager = new SubTaskMemoryManager();
    TaskController taskManager = new TaskMemoryManager();


    private List<Task> history = new LinkedList<>();

    //    Получение списка всех задач.
    @Override
    public List<Task> findAllTasks() {
        List<Task> allTasks = new ArrayList<>(taskManager.findAllTask());
        allTasks.addAll(subTaskManager.findAllTask());
        return allTasks;
    }

    //    Получение списка всех эпиков.
    @Override
    public List<Task> findAllEpicTask() {
        return epicTaskManager.findAllTask();
    }

    //    Получение списка всех подзадач определённого эпика.
    @Override
    public List<SubTask> findTaskByEpic(EpicTask epic) {
        return epic.getSubTasks();
    }

    //    Получение задачи любого типа по идентификатору.
    @Override
    public Task findTaskById(int id) {
        return taskManager.getTaskById(id);
    }

    @Override
    public SubTask findSubtaskById(int id) {
        final SubTask sub = (SubTask) subTaskManager.getTaskById(id);
        addHistory(sub);
        return sub;
    }

    @Override
    public EpicTask findEpicTaskById(int id) {
        final EpicTask epic = (EpicTask) epicTaskManager.getTaskById(id);
        addHistory(epic);
        return epic;
    }

    private void addHistory(Task task) {
        if (task == null) {
            return;
        }
        if (history.size() == 10) {
            history.remove(0);
        }
        history.add(task);
    }

    //    Добавление новой задачи, эпика и подзадачи. Сам объект должен передаваться в качестве
    //    параметра.
    @Override
    public void addNewTask(Task task) {
        Task t = new Task();
        if (t.compareClass(task)) {
            taskManager.addNewTask(task);
        }

        EpicTask epic = new EpicTask();
        if (epic.compareClass(task)) {
            epicTaskManager.addNewTask((EpicTask) task);
        }

        SubTask sub = new SubTask();
        if (sub.compareClass(task)) {
            sub = (SubTask) task;
            subTaskManager.addNewTask(sub);
            EpicTask ep = (EpicTask) epicTaskManager.getTaskById(sub.getEpicID());
            ep.getSubTasks().add(sub);
        }
    }

    //    Обновление задачи любого типа по идентификатору. Новая версия объекта передаётся в виде
    //    параметра.
    @Override
    public void updateTask(Task task) {
        Task t = new Task();
        if (t.compareClass(task)) {
            taskManager.updateTask(task);
        }

        EpicTask epic = new EpicTask();
        if (epic.compareClass(task)) {
            epic = (EpicTask) task;
            epicTaskManager.updateTask(epic);
        }

        SubTask sub = new SubTask();
        if (sub.compareClass(task)) {
            sub = (SubTask) task;
            subTaskManager.updateTask(sub);
            EpicTask ep = (EpicTask) epicTaskManager.getTaskById(sub.getEpicID());
            int index = ep.getSubTasks().indexOf(sub);
            ep.getSubTasks().set(index, sub);
        }
    }

    //    Удаление ранее добавленных задач — всех и по идентификатору.
    @Override
    public void removeAll() {
        taskManager.removeAll();
        epicTaskManager.removeAll();
        subTaskManager.removeAll();
    }

    @Override
    public void removeTaskById(int id) {
        taskManager.removeTaskById(id);
    }

    @Override
    public void removeEpicTaskByID(int id) {
        epicTaskManager.removeTaskById(id);
    }

    @Override
    public void removeSubTaskByID(int id) {
        subTaskManager.removeTaskById(id);
    }

    @Override
    public List getHistory() {
        return history;
    }
}
