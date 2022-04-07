package model;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import static model.TaskTypes.*;

@JsonSerialize
public class SubTask extends Task {
//    @JsonProperty
//    private TaskTypes type=SUBTASK;
    @JsonProperty("epic_id")
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

    public SubTask(String name, String details, int id, StatusTask status, Long duration,
                   String startTime, int epicID) {
        super(name, details, id, status, duration, startTime);
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
                ", duration=" + super.getDuration() +
                ", startTime=" + super.getStartTime() +
                '}';
    }

    @Override
    public String writeString() {
        return super.getId() + "," + TaskTypes.SUBTASK + "," +
                super.getName() + ',' +
                super.getDetails() + ',' +
                super.getStatus() + ',' +
                super.getDuration().toMinutes() + ',' +
                super.getStartTime().format(super.formatter) + ',' + epicID;
    }
}
