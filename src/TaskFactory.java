public class TaskFactory {
    public Task createTask(TaskTypes type, String name, String details, int id, String status){

        Task task=null;
        switch (type){
            case ЭПИК:
                task=new EpicTask(name, details, id, status);
                break;
            case ЗАДАЧА:
                task=new Task(name, details, id, status);
                break;
            case ПОДЗАДАЧА:
                task=new SubTask(name, details, id, status);
                break;
            default:
                System.out.println("Неверный тип задачи"+ type);
        }
        return task;
    }
}
