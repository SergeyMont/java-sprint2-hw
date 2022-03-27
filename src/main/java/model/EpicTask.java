package model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EpicTask extends Task {

    public void setSubTasks(ArrayList<SubTask> subTasks) {
        this.subTasks = subTasks;
    }

    private ArrayList<SubTask> subTasks;

    public EpicTask() {
        super();
    }

    public EpicTask(String name, String details, int id) {
        super(name, details, id);
        subTasks = new ArrayList<>();
    }

    public EpicTask(String name, String details, int id, StatusTask status, Long duration,
                    String startTime) {
        super(name, details, id, status, duration, startTime);
        subTasks = new ArrayList<>();
    }

    public EpicTask(EpicTask epic) {
        super(epic);
    }

    public List<SubTask> getSubTasks() {
        return subTasks;
    }

    @Override
    public StatusTask getStatus() {
        if (subTasks.isEmpty()) {
            return StatusTask.NEW;
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
                return StatusTask.NEW;
            } else if (countDoneStatus == subTasks.size()) {
                return StatusTask.DONE;
            } else {
                return StatusTask.IN_PROGRESS;
            }
        }
    }

    @Override
    public LocalDateTime getEndTime() {
        if (subTasks.isEmpty()) {
            return LocalDateTime.MIN;//TO DO Think about it
        } else {
            LocalDateTime start = subTasks.get(0).getStartTime();
            Duration duration = Duration.ofMillis(0);
            for (SubTask sub : subTasks) {
                duration=duration.plus(sub.getDuration());
                if (start.isAfter(sub.getStartTime())) {
                    start = sub.getStartTime();
                }
            }
            super.setStartTime(start);
            super.setDuration(duration);
        }
        return super.getEndTime();
    }

    @Override
    public String toString() {
        return "EpicTask{" +
                " name='" + super.getName() + '\'' +
                ", details='" + super.getDetails() + '\'' +
                ", id=" + super.getId() +
                ", status=" + super.getStatus() +
                ", duration=" + super.getDuration() +
                ", startTime=" + super.getStartTime() +
                '}';
    }

    @Override
    public String writeString() {
        return super.getId() + "," + TaskTypes.EPIC_TASK + "," +
                super.getName() + ',' +
                super.getDetails() + ',' +
                super.getStatus() + ',' +
                super.getDuration().toMinutes() + ',' +
                super.getStartTime().format(super.formatter) + ',';
    }
}
