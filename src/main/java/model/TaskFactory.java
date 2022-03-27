package model;

public class TaskFactory {
    public static Task createTask(int id, TaskTypes type, String name, StatusTask status,
                                  String details, int epicID, Long duration,String startTime) {

        Task task = null;
        switch (type) {
            case EPIC_TASK:
                task = new EpicTask(name, details, id, status, duration, startTime);
                break;
            case TASK:
                task = new Task(name, details, id, status, duration, startTime);
                break;
            case SUBTASK:
                task = new SubTask(name, details, id, status, duration, startTime, epicID);
                break;
            default:
                System.out.println("Неверный тип задачи" + type);
        }
        return task;
    }
}
