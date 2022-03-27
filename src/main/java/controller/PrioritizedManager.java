package controller;

import model.Task;

import java.util.List;

public interface PrioritizedManager  {
    public List<Task> getPrioritized();

    public boolean addPrioritize(Task task);

    void remove(Task task);

    void removeAll();
}
