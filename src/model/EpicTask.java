package model;

public class EpicTask extends Task {

    boolean isEpic;

    public EpicTask() {
        super();
    }

    public EpicTask(String name, String details, int id) {
        super(name, details, id);
        isEpic = true;
    }

    public EpicTask(EpicTask epic) {
        super(epic);
        this.isEpic = epic.isEpic;
    }

    @Override
    public String toString() {
        return "EpicTask{" +
                "isEpic=" + isEpic +
                ", name='" + name + '\'' +
                ", details='" + details + '\'' +
                ", id=" + id +
                ", status=" + status +
                '}';
    }
}
