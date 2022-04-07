import controller.FileBackedTasksManager;
import controller.HTTPTaskManager;
import controller.Managers;
import controller.TotalManager;
import httpResourses.KVServer;
import model.EpicTask;

import static model.StatusTask.*;

import model.SubTask;
import model.Task;
import resourses.FileManager;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        KVServer base= null;
        try {
            base = new KVServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
        base.start();
        TotalManager taskManager = Managers.getDefault();
        EpicTask epic = new EpicTask("Покормить кошку", "Кошка ест только Sheba", 1);
        while (true) {
            Menu.printMenu();
            int command = scanner.nextInt();
            switch (command) {
                case 1:
                    System.out.println(taskManager.findAllTasks().toString());
                    break;
                case 2:
                    System.out.println(taskManager.findAllEpicTask().toString());
                    break;
                case 3:

                    System.out.println(taskManager.findTaskByEpic(epic).toString());
                    break;
                case 4:
                    System.out.println("Введите id Task для поиска");
                    int id = scanner.nextInt();
                    System.out.println(taskManager.findTaskById(id).toString());
                    System.out.println("Введите id SubTask для поиска");
                    int id2 = scanner.nextInt();
                    System.out.println(taskManager.findSubtaskById(id2).toString());
                    System.out.println("Введите id EpicTask для поиска");
                    int id3 = scanner.nextInt();
                    System.out.println(taskManager.findEpicTaskById(id3).toString());
                    break;
                case 5:
                    Task task = new Task("Помыть посуду", "Загрузить посудомойку", 2);
                    EpicTask epic2 = new EpicTask("Сделать ремонт", "Выполнить ремонт до Нового " +
                            "года", 3);
                    SubTask sub1 = new SubTask("Отшпаклевать стены", "Шпаклевка и шлифовка", 4, 3);
                    SubTask sub2 = new SubTask("Положить ламинат", "Подложка и ламинат", 5, 3);
                    SubTask sub3 = new SubTask("Поклеить обои", "Обои с подбором", 6, 3);
                    taskManager.addNewTask(epic);
                    taskManager.addNewTask(epic2);
                    taskManager.addNewTask(task);
                    taskManager.addNewTask(sub1);
                    taskManager.addNewTask(sub2);
                    taskManager.addNewTask(sub3);
                    taskManager.findSubtaskById(4).setStatus(DONE);
                    taskManager.findSubtaskById(5).setStatus(IN_PROGRESS);
                    taskManager.findSubtaskById(6).setStatus(NEW);
                    System.out.println(taskManager.findEpicTaskById(3).getStatus());
                    break;
                case 6:
                    Task task2 = new Task("Помыть посуду до блеска", "Загрузить посудомойку " +
                            "посудой и чистящим средством", 2);
                    SubTask sub4 = new SubTask("Поклеить обои", "Прогрунтовать стены. Обои с " +
                            "подбором", 6, 3);
                    EpicTask epic3 = new EpicTask("Покормить кошку", "Кошка ест только Sheba " +
                            "после прогулки", 1);
                    taskManager.updateTask(task2);
                    taskManager.updateTask(sub4);
                    taskManager.updateTask(epic3);
                    break;
                case 7:
                    System.out.println("Введите id для удаления");
                    int id4 = scanner.nextInt();
                    taskManager.removeTaskById(id4);
                    System.out.println("Задача с id " + id4 + " удалена");
                    break;
                case 8:
                    taskManager.removeAll();
                    System.out.println("Все задачи удалены");
                    break;
                case 9:
                    System.out.println("Вызываем 4 разных задачи");
                    taskManager.findEpicTaskById(3);
                    taskManager.findSubtaskById(4);
                    taskManager.findSubtaskById(5);
                    taskManager.findTaskById(2);
                    System.out.println(taskManager.getHistory().toString());
                    System.out.println("Вызываем повторно 2 разных задачи");
                    taskManager.findEpicTaskById(3);
                    taskManager.findEpicTaskById(3);
                    taskManager.findTaskById(2);
                    taskManager.findTaskById(2);
                    System.out.println(taskManager.getHistory().toString());
                    System.out.println("Удаляем задачу, которая есть в истории");
                    taskManager.removeTaskById(2);
                    System.out.println(taskManager.getHistory().toString());
                    System.out.println("Удаляем эпик и его подзадачи");
                    taskManager.removeEpicTaskByID(3);
                    System.out.println(taskManager.getHistory().toString());
                    break;
                case 10:
                    System.out.println("Load Tasks");
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
                    taskManager.addNewTask(taskTest);
                    taskManager.addNewTask(epic2Test);
                    taskManager.addNewTask(sub1Test);
                    taskManager.addNewTask(sub2Test);
                    taskManager.addNewTask(sub3Test);
                    //epic2Test.getEndTime();
                    taskManager.findEpicTaskById(3);
                    System.out.println("Add History");
//                    taskManager.findTaskById(2);
//                    taskManager.findEpicTaskById(3);
//                    taskManager.findSubtaskById(5);
                    System.out.println("Old manager");
                    System.out.println("All tasks");
                    System.out.println(taskManager.findAllTasks().toString());
                    System.out.println("History");
                    System.out.println(taskManager.getHistory().toString());
                    HTTPTaskManager loadFromServer =
                            new HTTPTaskManager("http://localhost:8078/").loadFromServer();
                    System.out.println("New manager from file");
                    System.out.println("All tasks");
                    System.out.println(loadFromServer.findAllTasks().toString());
                    System.out.println("History");
                    System.out.println(loadFromServer.getHistory().toString());
                    break;
                case 0:
                    scanner.close();
                    base.stop();
                    return;
            }
        }
    }
}
