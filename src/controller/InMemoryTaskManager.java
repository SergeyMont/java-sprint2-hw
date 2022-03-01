package controller;

import model.EpicTask;
import model.SubTask;
import model.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryTaskManager implements TotalManager {

    private TaskManager<EpicTask> epicTaskManager = new TaskMemoryManager<EpicTask>();
    private TaskManager<SubTask> subTaskManager = new TaskMemoryManager<SubTask>();
    private TaskManager<Task> taskManager = new TaskMemoryManager<Task>();
    private HistoryManager historyManager = new InMemoryHistoryManager();


    //    Получение списка всех задач.
    @Override
    public List<Task> findAllTasks() {
        List<Task> allTasks = new ArrayList<>(taskManager.findAllTask());
        allTasks.addAll(epicTaskManager.findAllTask());
        allTasks.addAll(subTaskManager.findAllTask());
        return allTasks;
    }

    //    Получение списка всех эпиков.
    @Override
    public List<EpicTask> findAllEpicTask() {
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
        final Task task = taskManager.getTaskById(id);
        historyManager.add(task);
        return task;
    }

    @Override
    public SubTask findSubtaskById(int id) {
        final SubTask sub = subTaskManager.getTaskById(id);
        historyManager.add(sub);
        return sub;
    }

    @Override
    public EpicTask findEpicTaskById(int id) {
        final EpicTask epic = epicTaskManager.getTaskById(id);
        historyManager.add(epic);
        return epic;
    }


    //    Добавление новой задачи, эпика и подзадачи. Сам объект должен передаваться в качестве
    //    параметра.
    @Override
    public void addNewTask(Task task) {

        if (task instanceof EpicTask) {
            EpicTask epic = (EpicTask) task;
            epicTaskManager.addNewTask(epic);
        }


        if (task instanceof SubTask) {
            SubTask sub = (SubTask) task;
            subTaskManager.addNewTask(sub);
            EpicTask ep = epicTaskManager.getTaskById(sub.getEpicID());
            ep.getSubTasks().add(sub);
        }

        if (task instanceof Task) {
            taskManager.addNewTask(task);
        }
    }

    //    Обновление задачи любого типа по идентификатору. Новая версия объекта передаётся в виде
    //    параметра.
    @Override
    public void updateTask(Task task) {

        if (Task.class == task.getClass()) {
            taskManager.updateTask(task);
        }

        if (task instanceof EpicTask) {
            EpicTask epic = (EpicTask) task;
            epicTaskManager.updateTask(epic);
        }

        if (task instanceof SubTask) {
            SubTask sub = (SubTask) task;
            subTaskManager.updateTask(sub);
            EpicTask ep = epicTaskManager.getTaskById(sub.getEpicID());
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
        historyManager.removeAll();
    }

    @Override
    public void removeTaskById(int id) {

        taskManager.removeTaskById(id);
        historyManager.remove(id);
    }

    @Override
    public void removeEpicTaskByID(int id) {

        List<SubTask> subTasks = epicTaskManager.getTaskById(id).getSubTasks();
        for (SubTask sub : subTasks) {
            historyManager.remove(sub.getId());
        }
        epicTaskManager.removeTaskById(id);
        historyManager.remove(id);
    }

    @Override
    public void removeSubTaskByID(int id) {

        subTaskManager.removeTaskById(id);
        historyManager.remove(id);
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }
}
