package controller;

import model.Task;

import java.time.LocalDateTime;
import java.util.*;

public class InMemoryPrioritizedTasks implements PrioritizedManager {
    NavigableMap<LocalDateTime, Task> map = new TreeMap<>();

    @Override
    public List<Task> getPrioritized() {
        return new LinkedList<>(map.values());
    }

    @Override
    public boolean addPrioritize(Task task) {
        map.put(task.getStartTime(), task);
        return true;
    }

    @Override
    public void remove(Task task) {
        map.remove(task.getStartTime());
    }

    @Override
    public void removeAll() {
        map.clear();
    }
}
