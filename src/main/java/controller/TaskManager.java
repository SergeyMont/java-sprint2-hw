package controller;

import model.Task;

import java.util.List;

public interface TaskManager<T extends Task> {
    List<T> findAllTask();

    T getTaskById(int id);

    void addNewTask(T task);

    void updateTask(T task);

    void removeAll();

    void removeTaskById(int id);
}
