package model;

import java.util.Objects;

import static model.Status.NEW;

public class Task {
    final private String name;
    final private String details;
    final private int id;
    private Status status;

    public Task() {
        name = "";
        details = "";
        id = 0;
        status = NEW;
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

    public Task(String name, String details, int id, Status status) {
        this.name = name;
        this.details = details;
        this.id = id;
        this.status = status;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
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
                '}';
    }

    public String writeString() {
        return id + "," + TaskTypes.TASK + "," +
                name + ',' +
                details + ',' +
                status + ',';
    }
}
