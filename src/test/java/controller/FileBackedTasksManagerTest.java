package controller;

import model.EpicTask;

import static model.StatusTask.*;
import model.SubTask;
import model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import resourses.FileManager;

import static org.junit.jupiter.api.Assertions.*;

class FileBackedTasksManagerTest extends TotalManagerTest {
    FileBackedTasksManager fbtm = new
            FileBackedTasksManager(FileManager.connectRepository());
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

    @BeforeEach
    public void reload(){
        FileManager.connectRepository().delete();
    }

    @Test
    void saveAndLoadFromFileEmptyTasks() {
        FileBackedTasksManager fromFile =
                FileBackedTasksManager.loadFromFile(FileManager.connectRepository());
        assertEquals(fbtm.findAllTasks(), fromFile.findAllTasks());
        assertEquals(fbtm.getHistory(),fromFile.getHistory());
    }

    @Test
    void saveAndLoadFromFileSingleEpic() {
        fbtm.addNewTask(taskTest);
        fbtm.addNewTask(epic2Test);
        FileBackedTasksManager fromFile =
                FileBackedTasksManager.loadFromFile(FileManager.connectRepository());
        assertEquals(fbtm.findAllTasks(), fromFile.findAllTasks());
        assertEquals(fbtm.getHistory(),fromFile.getHistory());
    }

    @Test
    void saveAndLoadFromFileHistory() {
        fbtm.addNewTask(taskTest);
        fbtm.addNewTask(epic2Test);
        fbtm.addNewTask(sub1Test);
        fbtm.addNewTask(sub2Test);
        fbtm.addNewTask(sub3Test);
        fbtm.findTaskById(2);
        fbtm.findSubtaskById(5);
        fbtm.findSubtaskById(6);
        FileBackedTasksManager fromFile =
                FileBackedTasksManager.loadFromFile(FileManager.connectRepository());
        assertEquals(fbtm.findAllTasks(), fromFile.findAllTasks());
        assertEquals(fbtm.getHistory(),fromFile.getHistory());
    }

}