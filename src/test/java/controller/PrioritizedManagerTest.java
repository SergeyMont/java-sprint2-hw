package controller;

import model.EpicTask;
import model.StatusTask;
import model.SubTask;
import model.Task;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PrioritizedManagerTest {
    PrioritizedManager prioritizedManager = new InMemoryPrioritizedTasks();
    List<Task> list = new ArrayList<>();
    Task task = new Task("Помыть посуду", "Загрузить посудомойку", 2, StatusTask.NEW, 60L, "01.01" +
            ".2020-12:30");
    EpicTask ep1 = new EpicTask("Сделать ремонт", "Выполнить ремонт до Нового " +
            "года", 3);
    SubTask sub1 = new SubTask("Отшпаклевать стены", "Шпаклевка и шлифовка", 4, StatusTask.NEW,
            60L, "02.01.2020-12:30", 3);
    SubTask sub2 = new SubTask("Положить ламинат", "Подложка и ламинат", 5, StatusTask.NEW, 60L,
            "10.01.2020-12:30", 3);
    SubTask sub3 = new SubTask("Поклеить обои", "Обои с подбором", 6, StatusTask.NEW, 60L, "05.01" +
            ".2020-12:30", 3);

    @Test
    void getPrioritized() {
        assertEquals(list, prioritizedManager.getPrioritized());
        prioritizedManager.addPrioritize(task);
        prioritizedManager.addPrioritize(sub1);
        prioritizedManager.addPrioritize(sub2);
        prioritizedManager.addPrioritize(sub3);
        list.add(task);
        list.add(sub1);
        list.add(sub3);
        list.add(sub2);
        assertEquals(list, prioritizedManager.getPrioritized());
    }

    @Test
    void addPrioritize() {
        assertTrue(prioritizedManager.addPrioritize(task));
    }

    @Test
    void remove() {
        prioritizedManager.addPrioritize(task);
        prioritizedManager.addPrioritize(sub1);
        list.add(task);
        assertNotEquals(list, prioritizedManager.getPrioritized());
        prioritizedManager.remove(sub1);
        assertEquals(list, prioritizedManager.getPrioritized());
    }

    @Test
    void removeAll() {
        prioritizedManager.addPrioritize(task);
        prioritizedManager.addPrioritize(sub1);
        assertNotEquals(list, prioritizedManager.getPrioritized());
        prioritizedManager.removeAll();
        assertEquals(list, prioritizedManager.getPrioritized());
    }
}