package controller;

import model.EpicTask;
import model.SubTask;
import model.Task;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HistoryManagerTest {
    HistoryManager historyManager = new InMemoryHistoryManager();
    List<Task> list = new ArrayList<>();
    Task task = new Task("Помыть посуду", "Загрузить посудомойку", 2);
    EpicTask ep1 = new EpicTask("Сделать ремонт", "Выполнить ремонт до Нового " +
            "года", 3);
    SubTask sub1 = new SubTask("Отшпаклевать стены", "Шпаклевка и шлифовка", 4, 3);
    SubTask sub2 = new SubTask("Положить ламинат", "Подложка и ламинат", 5, 3);
    SubTask sub3 = new SubTask("Поклеить обои", "Обои с подбором", 6, 3);

    @Test
    void add() {
        historyManager.add(ep1);
        list.add(ep1);
        assertNotNull(historyManager.getHistory());
        assertEquals(list, historyManager.getHistory());
    }

    @Test
    void addDuplication() {
        historyManager.add(ep1);
        historyManager.add(ep1);
        list.add(ep1);
        assertEquals(list, historyManager.getHistory());
    }

    void createFourTasks() {
        historyManager.add(task);
        historyManager.add(ep1);
        historyManager.add(sub1);
        historyManager.add(sub2);
        historyManager.add(sub3);
    }

    @Test
    void removeFromStart() {
        createFourTasks();
        list.add(ep1);
        list.add(sub1);
        list.add(sub2);
        list.add(sub3);
        historyManager.remove(2);
        assertEquals(list, historyManager.getHistory());
    }

    @Test
    void removeFromMiddle() {
        createFourTasks();
        list.add(task);
        list.add(ep1);
        list.add(sub2);
        list.add(sub3);
        historyManager.remove(4);
        assertEquals(list, historyManager.getHistory());
    }

    @Test
    void removeFromEnd() {
        createFourTasks();
        list.add(task);
        list.add(ep1);
        list.add(sub1);
        list.add(sub2);
        historyManager.remove(6);
        assertEquals(list, historyManager.getHistory());
    }

    @Test
    void getEmptyHistory() {
        assertEquals(list, historyManager.getHistory());
    }

    @Test
    void removeAll() {
        createFourTasks();
        historyManager.removeAll();
        assertEquals(list, historyManager.getHistory());
    }
}