package model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import static model.StatusTask.*;


public class Task {
    final private String name;
    final private String details;
    final private int id;
    private StatusTask status;
    private Duration duration;
    private LocalDateTime startTime;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy-HH:mm");

    public Task() {
        name = "";
        details = "";
        id = 0;
        status = StatusTask.NEW;
    }

    public Task(String name, int id) {
        this.name = name;
        this.details = null;
        this.id = id;
        this.status = NEW;
    }

    public Task(String name, String details, int id) {
        this.name = name;
        this.details = details;
        this.id = id;
        this.status = NEW;
    }

    public Task(String name, String details, int id, StatusTask status, Long duration,
                String startTime) {
        this.name = name;
        this.details = details;
        this.id = id;
        this.status = status;
        this.duration = Duration.ofMinutes(duration);
        this.startTime = LocalDateTime.parse(startTime, formatter);
    }

    public Task(Task task) {
        this.name = task.name;
        this.details = task.details;
        this.id = task.id;
        this.status = task.status;
    }

    public String getName() {
        return name;
    }

    public String getDetails() {
        return details;
    }

    public int getId() {
        return id;
    }

    public StatusTask getStatus() {
        return status;
    }

    public void setStatus(StatusTask status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", details='" + details + '\'' +
                ", id=" + id +
                ", status=" + status +
                ", duration=" + duration +
                ", startTime=" + startTime +
                '}';
    }

    public String writeString() {
        return id + "," + TaskTypes.TASK + "," +
                name + ',' +
                details + ',' +
                status + ',' +
                duration.toMinutes() + ',' +
                startTime.format(formatter)+ ',';
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime(){
        return startTime.plus(duration);
    }
}
