package model;

public class SubTask extends Task {
    private int epicID;

    public SubTask() {
        super();
    }

    public SubTask(String name, String details, int id) {
        super(name, details, id);
    }

    public SubTask(String name, String details, int id, int epicID) {
        super(name, details, id);
        this.epicID = epicID;
    }

    public SubTask(String name, String details, int id, Status status, int epicID) {
        super(name, details, id);
        this.epicID = epicID;
    }

    public SubTask(SubTask subTask) {
        super(subTask);
        this.epicID = subTask.getId();
    }

    public int getEpicID() {
        return epicID;
    }

    @Override
    public String toString() {
        return "SubTask{" +
                " epicID=" + epicID +
                ", name='" + super.getName() + '\'' +
                ", details='" + super.getDetails() + '\'' +
                ", id=" + super.getId() +
                ", status=" + super.getStatus() +
                '}';
    }

    @Override
    public String writeString() {
        return super.getId() + "," + TaskTypes.SUBTASK + "," +
                super.getName() + ',' +
                super.getDetails() + ',' +
                super.getStatus() + ',' + epicID;
    }
}
