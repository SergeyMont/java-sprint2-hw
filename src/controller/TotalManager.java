package controller;

import model.EpicTask;
import model.SubTask;
import model.Task;

import java.util.List;

public interface TotalManager {
    //    Получение списка всех задач.
    List<Task> findAllTasks();

    //    Получение списка всех эпиков.
    List<EpicTask> findAllEpicTask();

    //    Получение списка всех подзадач определённого эпика.
    List<SubTask> findTaskByEpic(EpicTask epic);

    //    Получение задачи любого типа по идентификатору.
    Task findTaskById(int id);

    SubTask findSubtaskById(int id);

    EpicTask findEpicTaskById(int id);

    //    Добавление новой задачи, эпика и подзадачи. Сам объект должен передаваться в качестве
    //    параметра.
    void addNewTask(Task task);

    //    Обновление задачи любого типа по идентификатору. Новая версия объекта передаётся в виде
    //    параметра.
    void updateTask(Task task);

    //    Удаление ранее добавленных задач — всех и по идентификатору.
    void removeAll();

    void removeTaskById(int id);

    void removeEpicTaskByID(int id);

    void removeSubTaskByID(int id);

    List<Task> getHistory();
}
