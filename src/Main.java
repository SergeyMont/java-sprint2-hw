import controller.Managers;
import controller.TotalManager;
import model.EpicTask;
import model.Status;
import model.SubTask;
import model.Task;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
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
                    taskManager.findSubtaskById(4).setStatus(Status.DONE);
                    taskManager.findSubtaskById(5).setStatus(Status.IN_PROGRESS);
                    taskManager.findSubtaskById(6).setStatus(Status.NEW);
                    System.out.println(taskManager.findTaskById(3).getStatus());
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
                    System.out.println(taskManager.getHistory().toString());
                    break;
                case 0:
                    return;
            }
        }
    }
}
