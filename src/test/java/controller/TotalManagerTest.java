package controller;

import model.EpicTask;
import model.StatusTask;
import model.SubTask;
import model.Task;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TotalManagerTest {

    TotalManager manager=new InMemoryTaskManager();
    List<Task> list = new ArrayList<>();
    Task task = new Task("Помыть посуду", "Загрузить посудомойку", 2, StatusTask.NEW, 60L, "01.01" +
            ".2020-12:30");
    EpicTask ep1 = new EpicTask("Сделать ремонт", "Выполнить ремонт до Нового " +
            "года", 3,StatusTask.NEW,
            60L, "02.01.2020-12:30");
    SubTask sub1 = new SubTask("Отшпаклевать стены", "Шпаклевка и шлифовка", 4, StatusTask.NEW,
            60L, "02.01.2020-12:30", 3);
    SubTask sub2 = new SubTask("Положить ламинат", "Подложка и ламинат", 5, StatusTask.NEW, 60L,
            "10.01.2020-12:30", 3);
    SubTask sub3 = new SubTask("Поклеить обои", "Обои с подбором", 6, StatusTask.NEW, 60L, "05.01" +
            ".2020-12:30", 3);

    @Test
    void findAllTasks() {
        assertEquals(list,manager.findAllTasks());
        manager.addNewTask(task);
        list.add(task);
        assertEquals(list,manager.findAllTasks());
    }

    @Test
    void findAllEpicTask() {
        manager.addNewTask(ep1);
        manager.addNewTask(sub1);
        manager.addNewTask(sub2);
        list.add(ep1);
        assertEquals(list,manager.findAllEpicTask());
    }

    @Test
    void findTaskByEpic() {
        manager.addNewTask(ep1);
        manager.addNewTask(sub1);
        manager.addNewTask(sub2);
        list.add(sub1);
        list.add(sub2);
        assertEquals(list,manager.findTaskByEpic(ep1));
    }

    @Test
    void findTaskById() {
        manager.addNewTask(task);
        assertEquals(task,manager.findTaskById(2));
    }

    @Test
    void findSubtaskById() {
        manager.addNewTask(ep1);
        manager.addNewTask(sub1);
        assertEquals(sub1,manager.findSubtaskById(4));
    }

    @Test
    void findEpicTaskById() {
        manager.addNewTask(ep1);
        assertEquals(ep1,manager.findEpicTaskById(3));
    }

    @Test
    void addNewTask() {
        manager.addNewTask(task);
        manager.addNewTask(ep1);
        manager.addNewTask(sub1);
        list.add(task);
        list.add(ep1);
        list.add(sub1);
        assertEquals(list,manager.findAllTasks());
    }

    @Test
    void updateTask() {
        Task task2 = new Task("AAAAA", "DDDD", 2, StatusTask.IN_PROGRESS, 60L, "01.01" +
                ".2020-10:30");
        EpicTask ep2 = new EpicTask("yyy", "uuu " +
                "года", 3,StatusTask.NEW,
                120L, "02.01.2020-12:30");
        SubTask sub4 = new SubTask("Отшпаклевать стены", "Шпаклевка и шлифовка", 4, StatusTask.DONE,
                60L, "02.01.2020-12:30", 3);
        manager.addNewTask(task);
        manager.addNewTask(ep1);
        manager.addNewTask(sub1);
        manager.updateTask(task2);
        manager.updateTask(ep2);
        manager.updateTask(sub4);
        assertEquals(task2,manager.findTaskById(2));
        assertEquals(ep2,manager.findEpicTaskById(3));
        assertEquals(sub4,manager.findSubtaskById(4));
    }

    @Test
    void removeAll() {
        manager.addNewTask(task);
        manager.addNewTask(ep1);
        manager.addNewTask(sub1);
        assertNotEquals(list,manager.findAllTasks());
        manager.removeAll();
        assertEquals(list,manager.findAllTasks());
    }

    @Test
    void removeTaskById() {
        manager.addNewTask(task);
        manager.addNewTask(ep1);
        manager.addNewTask(sub1);
        list.add(ep1);
        list.add(sub1);
        assertNotEquals(list,manager.findAllTasks());
        manager.removeTaskById(2);
        assertEquals(list,manager.findAllTasks());
    }

    @Test
    void removeEpicTaskByID() {
        manager.addNewTask(task);
        manager.addNewTask(ep1);
        manager.addNewTask(sub1);
        list.add(task);
        list.add(sub1);
        assertNotEquals(list,manager.findAllTasks());
        manager.removeEpicTaskByID(3);
        assertEquals(list,manager.findAllTasks());
    }

    @Test
    void removeSubTaskByID() {
        manager.addNewTask(task);
        manager.addNewTask(ep1);
        manager.addNewTask(sub1);
        list.add(task);
        list.add(ep1);
        assertNotEquals(list,manager.findAllTasks());
        manager.removeSubTaskByID(4);
        assertEquals(list,manager.findAllTasks());
    }

    @Test
    void getHistory() {
        assertEquals(list,manager.getHistory());
        manager.addNewTask(task);
        manager.addNewTask(ep1);
        manager.addNewTask(sub1);
        assertEquals(list,manager.getHistory());
        manager.findTaskById(2);
        manager.findEpicTaskById(3);
        manager.findSubtaskById(4);
        list.add(task);
        list.add(ep1);
        list.add(sub1);
        assertEquals(list,manager.getHistory());
        manager.findSubtaskById(4);
        manager.findSubtaskById(4);
        assertEquals(list,manager.getHistory());
        manager.removeEpicTaskByID(3);
        list.remove(ep1);
        list.remove(sub1);
        assertEquals(list,manager.getHistory());
    }

    @Test
    void getPrioritizedTasks() {
        assertEquals(list, manager.getPrioritizedTasks());
        manager.addNewTask(task);
        manager.addNewTask(ep1);
        manager.addNewTask(sub1);
        manager.addNewTask(sub2);
        manager.addNewTask(sub3);
        list.add(task);
        list.add(sub1);
        list.add(sub3);
        list.add(sub2);
        assertEquals(list, manager.getPrioritizedTasks());
    }
}