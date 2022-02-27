package model;

import java.util.ArrayList;
import java.util.List;

public class EpicTask extends Task {

    private ArrayList<SubTask> subTasks;

    public EpicTask() {
        super();
    }

    public EpicTask(String name, String details, int id) {
        super(name, details, id);
        subTasks = new ArrayList<>();
    }

    public EpicTask(String name, String details, int id, Status status) {
        super(name, details, id);
        subTasks = new ArrayList<>();
    }

    public EpicTask(EpicTask epic) {
        super(epic);
    }

    public List<SubTask> getSubTasks() {
        return subTasks;
    }

    @Override
    public Status getStatus() {
        if (subTasks.isEmpty()) {
            return Status.NEW;
        } else {
            int countNewStatus = 0;
            int countDoneStatus = 0;
            for (SubTask sub : subTasks) {
                switch (sub.getStatus()) {
                    case NEW:
                        countNewStatus++;
                        break;
                    case DONE:
                        countDoneStatus++;
                }
            }
            if (countNewStatus == subTasks.size()) {
                return Status.NEW;
            } else if (countDoneStatus == subTasks.size()) {
                return Status.DONE;
            } else {
                return Status.IN_PROGRESS;
            }
        }
    }

    @Override
    public String toString() {
        return "EpicTask{" +
                " name='" + super.getName() + '\'' +
                ", details='" + super.getDetails() + '\'' +
                ", id=" + super.getId() +
                ", status=" + super.getStatus() +
                '}';
    }

    @Override
    public String writeString() {
        return super.getId() + "," + TaskTypes.EPIC_TASK + "," +
                super.getName() + ',' +
                super.getDetails() + ',' +
                super.getStatus() + ',';
    }
}
