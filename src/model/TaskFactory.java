package model;

public class TaskFactory {
    public static Task createTask(int id, TaskTypes type, String name, Status status,
                                  String details, int epicID) {

        Task task = null;
        switch (type) {
            case EPIC_TASK:
                task = new EpicTask(name, details, id, status);
                break;
            case TASK:
                task = new Task(name, details, id, status);
                break;
            case SUBTASK:
                task = new SubTask(name, details, id, status, epicID);
                break;
            default:
                System.out.println("Неверный тип задачи" + type);
        }
        return task;
    }
}
