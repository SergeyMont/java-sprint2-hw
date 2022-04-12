package controller;

import httpResourses.KVServer;
import model.EpicTask;
import model.SubTask;
import model.Task;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static model.StatusTask.*;
import static org.junit.jupiter.api.Assertions.*;

class HTTPTaskManagerTest {

    HTTPTaskManager manager=new HTTPTaskManager("http://localhost:8078/");
    List<Task> list = new ArrayList<>();
    Task taskTest = new Task("Помыть посуду", "Загрузить посудомойку", 2,
            NEW, 20L, "01.01.2020-12:30");
    EpicTask epic2Test = new EpicTask("Сделать ремонт", "Выполнить ремонт до " +
            "Нового " +
            "года", 3, NEW, 20L, "01.01.2020-12:30");
    SubTask sub1Test = new SubTask("Отшпаклевать стены", "Шпаклевка и шлифовка",
            4, NEW, 20L, "02.01.2020-12:30", 3);
    SubTask sub2Test = new SubTask("Положить ламинат", "Подложка и ламинат", 5,
            NEW, 20L, "03.01.2020-12:30", 3);
    SubTask sub3Test = new SubTask("Поклеить обои", "Обои с подбором", 6,
            NEW, 20L, "04.01.2020-12:30", 3);
    @BeforeAll
    static void initialize() throws IOException {
        KVServer base= new KVServer();
        base.start();
    }

    @Test
    void saveAndLoadFromServerEmptyTasks() {
        HTTPTaskManager fromServer =
                new HTTPTaskManager("http://localhost:8078/").loadFromServer();
        assertEquals(manager.findAllTasks(), fromServer.findAllTasks());
        assertEquals(manager.getHistory(),fromServer.getHistory());
    }

    @Test
    void saveAndLoadFromServerSingleEpic() {
        manager.addNewTask(taskTest);
        manager.addNewTask(epic2Test);
        HTTPTaskManager fromServer =
                new HTTPTaskManager("http://localhost:8078/").loadFromServer();
        assertEquals(manager.findAllTasks(), fromServer.findAllTasks());
        assertEquals(manager.getHistory(),fromServer.getHistory());
    }

    @Test
    void saveAndLoadFromServerHistory() {
        manager.addNewTask(taskTest);
        manager.addNewTask(epic2Test);
        manager.addNewTask(sub1Test);
        manager.addNewTask(sub2Test);
        manager.addNewTask(sub3Test);
        manager.findTaskById(2);
        manager.findSubtaskById(5);
        manager.findSubtaskById(6);
        HTTPTaskManager fromServer =
                new HTTPTaskManager("http://localhost:8078/").loadFromServer();
        assertEquals(manager.findAllTasks(), fromServer.findAllTasks());
        assertEquals(manager.getHistory(),fromServer.getHistory());
    }

    @Test
    void findTaskByEpic() {
        manager.addNewTask(epic2Test);
        manager.addNewTask(sub1Test);
        manager.addNewTask(sub2Test);
        list.add(sub1Test);
        list.add(sub2Test);
        assertEquals(list, manager.findTaskByEpic(epic2Test));
    }

    @Test
    void findTaskById() {
        manager.addNewTask(taskTest);
        assertEquals(taskTest, manager.findTaskById(2));
    }

    @Test
    void findSubtaskById() {
        manager.addNewTask(epic2Test);
        manager.addNewTask(sub1Test);
        assertEquals(sub1Test, manager.findSubtaskById(4));
    }

    @Test
    void findEpicTaskById() {
        manager.addNewTask(epic2Test);
        assertEquals(epic2Test, manager.findEpicTaskById(3));
    }

    @Test
    void addNewTask() {
        manager.addNewTask(taskTest);
        manager.addNewTask(epic2Test);
        manager.addNewTask(sub1Test);
        list.add(taskTest);
        list.add(epic2Test);
        list.add(sub1Test);
        assertEquals(list, manager.findAllTasks());
    }

    @Test
    void updateTask() {
        Task task2 = new Task("AAAAA", "DDDD", 2, IN_PROGRESS, 60L, "01.01" +
                ".2020-10:30");
        EpicTask ep2 = new EpicTask("yyy", "uuu " +
                "года", 3, NEW,
                120L, "02.01.2020-12:30");
        SubTask sub4 = new SubTask("Отшпаклевать стены", "Шпаклевка и шлифовка", 4, DONE,
                60L, "02.01.2020-12:30", 3);
        manager.addNewTask(taskTest);
        manager.addNewTask(epic2Test);
        manager.addNewTask(sub1Test);
        manager.updateTask(task2);
        manager.updateTask(ep2);
        manager.updateTask(sub4);
        assertEquals(task2, manager.findTaskById(2));
        assertEquals(ep2, manager.findEpicTaskById(3));
        assertEquals(sub4, manager.findSubtaskById(4));
    }

    @Test
    void removeAll() {
        manager.addNewTask(taskTest);
        manager.addNewTask(epic2Test);
        manager.addNewTask(sub1Test);
        assertNotEquals(list, manager.findAllTasks());
        manager.removeAll();
        assertEquals(list, manager.findAllTasks());
    }

    @Test
    void removeTaskById() {
        manager.addNewTask(taskTest);
        manager.addNewTask(epic2Test);
        manager.addNewTask(sub1Test);
        list.add(epic2Test);
        list.add(sub1Test);
        assertNotEquals(list, manager.findAllTasks());
        manager.removeTaskById(2);
        assertEquals(list, manager.findAllTasks());
    }

    @Test
    void removeEpicTaskByID() {
        manager.addNewTask(taskTest);
        manager.addNewTask(epic2Test);
        manager.addNewTask(sub1Test);
        list.add(taskTest);
        list.add(sub1Test);
        assertNotEquals(list, manager.findAllTasks());
        manager.removeEpicTaskByID(3);
        assertEquals(list, manager.findAllTasks());
    }

    @Test
    void removeSubTaskByID() {
        manager.addNewTask(taskTest);
        manager.addNewTask(epic2Test);
        manager.addNewTask(sub1Test);
        list.add(taskTest);
        list.add(epic2Test);
        assertNotEquals(list, manager.findAllTasks());
        manager.removeSubTaskByID(4);
        assertEquals(list, manager.findAllTasks());
    }
}