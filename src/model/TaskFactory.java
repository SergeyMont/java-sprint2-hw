package model;

public class TaskFactory {
    public Task createTask(TaskTypes type, String name, String details, int id) {

        Task task = null;
        switch (type) {
            case EPIC_TASK:
                task = new EpicTask(name, details, id);
                break;
            case TASK:
                task = new Task(name, details, id);
                break;
            case SUBTASK:
                task = new SubTask(name, details, id);
                break;
            default:
                System.out.println("Неверный тип задачи" + type);
        }
        return task;
    }
}
