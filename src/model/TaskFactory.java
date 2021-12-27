package model;

public class TaskFactory {
    public Task createTask(TaskTypes type, String name, String details, int id) {

        Task task = null;
        switch (type) {
            case ЭПИК:
                task = new EpicTask(name, details, id);
                break;
            case ЗАДАЧА:
                task = new Task(name, details, id);
                break;
            case ПОДЗАДАЧА:
                task = new SubTask(name, details, id);
                break;
            default:
                System.out.println("Неверный тип задачи" + type);
        }
        return task;
    }
}
