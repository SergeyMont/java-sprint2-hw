package model;

import java.util.ArrayList;

public class EpicTask extends Task {

    private ArrayList<SubTask> subTasks;

    public EpicTask() {
        super();
    }

    public EpicTask(String name, String details, int id) {
        super(name, details, id);
        subTasks = new ArrayList<>();
    }

    public EpicTask(EpicTask epic) {
        super(epic);
    }

    public ArrayList<SubTask> getSubTasks() {
        return subTasks;
    }

    @Override
    public Status getStatus() {
        if (subTasks.isEmpty()) {
            return Status.NEW;
        } else {
            boolean isNewStatus = false;
            boolean isDoneStatus = false;
            for (SubTask sub : subTasks) {
                if (sub.getStatus() == Status.NEW) {
                    isNewStatus = true;
                } else if (sub.getStatus() == Status.DONE) {
                    isDoneStatus = true;
                }
            }
            if (isNewStatus && !isDoneStatus) {
                return Status.NEW;
            } else if (isDoneStatus && !isNewStatus) {
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
}
