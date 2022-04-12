package controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import httpResourses.KVClient;
import model.*;

import java.util.ArrayList;
import java.util.List;


public class HTTPTaskManager extends FileBackedTasksManager {
    private KVClient client;
    private String uri;
    private ObjectMapper mapper =
            new ObjectMapper().findAndRegisterModules().disable(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE);


    public HTTPTaskManager(String uri) {
        this.uri = uri;
        client = new KVClient(uri);
    }

    private void save() {
        String json = "";
        Fleet serialize = new Fleet();
        try {
            serialize.setTasks(super.findAllTasks());
            json = mapper.writeValueAsString(serialize);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        client.put("tasks", json);

        String history = "";
        Fleet serializeHistory = new Fleet();
        try {
            serializeHistory.setTasks(super.getHistory());
            history = mapper.writeValueAsString(serializeHistory);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        client.put("history", history);
    }

    public HTTPTaskManager loadFromServer() {
        HTTPTaskManager manager = new HTTPTaskManager(uri);
        String json = client.load("tasks");
        if (json != null) {
            try {

                Fleet deserialize = mapper.readValue(json, Fleet.class);
                for (Task task : deserialize.getTasks()) {
                    if (task.getClass().equals(EpicTask.class)) {
                        EpicTask ep = (EpicTask) task;
                        manager.addTaskWOutSave(ep);
                    } else if (task.getClass().equals(SubTask.class)) {
                        SubTask sub = (SubTask) task;
                        manager.addTaskWOutSave(sub);
                    } else {
                        manager.addTaskWOutSave(task);
                    }
                }
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

        String history = client.load("history");
        if (history != null) {
            try {
                Fleet deserializeHistory = mapper.readValue(history, Fleet.class);
                for (Task task : deserializeHistory.getTasks()) {
                    if (task.getClass().equals(EpicTask.class)) {
                        EpicTask ep = (EpicTask) task;
                        manager.findEpicTaskById(ep.getId());
                    } else if (task.getClass().equals(SubTask.class)) {
                        SubTask sub = (SubTask) task;
                        manager.findSubtaskById(sub.getId());
                    } else {
                        manager.findTaskById(task.getId());
                    }
                }
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return manager;
    }

    @Override
    public List<SubTask> findTaskByEpic(EpicTask epic) {
        List<SubTask> list = super.findTaskByEpic(epic);
        save();
        return list;

    }

    @Override
    public Task findTaskById(int id) {
        Task task = super.findTaskById(id);
        save();
        return task;
    }

    @Override
    public SubTask findSubtaskById(int id) {
        SubTask sub = super.findSubtaskById(id);
        save();
        return sub;
    }

    @Override
    public EpicTask findEpicTaskById(int id) {
        EpicTask ep = super.findEpicTaskById(id);
        save();
        return ep;
    }

    private void addTaskWOutSave(Task task) {
        super.addNewTask(task);
    }

    @Override
    public void addNewTask(Task task) {
        super.addNewTask(task);
        save();
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }

    @Override
    public void removeAll() {
        super.removeAll();
        save();
    }

    @Override
    public void removeTaskById(int id) {
        super.removeTaskById(id);
        save();
    }

    @Override
    public void removeEpicTaskByID(int id) {
        super.removeEpicTaskByID(id);
        save();
    }

    @Override
    public void removeSubTaskByID(int id) {
        super.removeSubTaskByID(id);
        save();
    }
}
