package model;

public class SubTask extends Task{
    boolean isEpic;
    int epicID;

    public SubTask() {
        super();
    }

    public SubTask(String name, String details, int id) {
        super(name, details, id);
        isEpic=false;
    }

    public SubTask(String name, String details, int id, int epicID) {
        super(name, details, id);
        isEpic=false;
        this.epicID=epicID;
    }

    public SubTask(SubTask subTask){
        super(subTask);
        this.epicID= subTask.id;
    }

    public int getEpicID() {
        return epicID;
    }

    @Override
    public String toString() {
        return "SubTask{" +
                "isEpic=" + isEpic +
                ", epicID=" + epicID +
                ", name='" + name + '\'' +
                ", details='" + details + '\'' +
                ", id=" + id +
                ", status=" + status +
                '}';
    }
}
