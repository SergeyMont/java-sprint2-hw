package controller;

import model.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;

public class FileBackedTasksManager extends InMemoryTaskManager {
    private File file;

    public FileBackedTasksManager(){
        super();
    }

    public FileBackedTasksManager(File file) {
        this.file = file;
    }

    private void save() {
        try (BufferedWriter bwr =
                     new BufferedWriter(new FileWriter(file, StandardCharsets.UTF_8))) {
            List<Task> list = findAllTasks();
            bwr.write("id,type,name,status,description,epic, duration, startTime\n");
            for (Task task : list) {
                bwr.write(task.writeString() + "\n");
            }
            bwr.write(" \n");
            List<Task> history = getHistory();
            for (Task task : history) {
                bwr.write(task.getId() + ",");
            }

        } catch (IOException e) {
            throw new ManagerSaveException("Произошла ошибка во время записи файла.");
        }

    }

    public static FileBackedTasksManager loadFromFile(File file) {
        FileBackedTasksManager fbtm = new FileBackedTasksManager(file);
        String str;
        try {
            str = Files.readString(file.toPath());
            List<String> data = Arrays.asList(str.split("\n"));
            int index = data.indexOf(" ");
            String lastLine = "";
            List<Integer> historyID = new ArrayList<>();
            if (data.size() > index + 1) {
                lastLine = data.get(index + 1);
                historyID = fbtm.historyFromString(lastLine);
            }

            int a=0;
            if(lastLine.isEmpty()){
                a=1;
            }
            else{a=2;}
            Map<Integer, Task> map = new HashMap<>();
            for (int i = 1; i < data.size()-a; i++) {
                Task task = fbtm.fromString(data.get(i));
                fbtm.addTaskWOutSave(task);
                map.put(task.getId(), task);
            }
            if (!lastLine.isEmpty() | !lastLine.isBlank()) {
                for (int i = 0; i < historyID.size(); i++) {
                    Task task = map.get(historyID.get(i));

                    if (task instanceof EpicTask) {
                        fbtm.findEpicTaskById(task.getId());
                    }
                    if (task instanceof SubTask) {
                        fbtm.findSubtaskById(task.getId());
                    }
                    if (task.getClass()== Task.class) {
                        fbtm.findTaskById(task.getId());
                    }
                }
            }
            //something wrong
        } catch (IOException e) {
            throw new ManagerSaveException("Произошла ошибка во время чтения файла.");
        }
        return fbtm;
    }

    private Task fromString(String value) {
        String[] arr = value.split(",");
        int id = Integer.parseInt(arr[0]);
        TaskTypes type = TaskTypes.valueOf(arr[1]);
        String name = arr[2];
        String details = arr[3];
        StatusTask status = StatusTask.valueOf(arr[4]);
        Long duration=Long.parseLong(arr[5]);
        String startTime=arr[6];
        Integer epicID;
        if (arr.length > 7) {
            epicID = Integer.parseInt(arr[7]);
        } else {
            epicID = 0;
        }
        return TaskFactory.createTask(id, type, name, status, details, epicID, duration, startTime);
    }

    private List<Integer> historyFromString(String value) {
        List<Integer> result = new ArrayList<>();
        if(!value.isEmpty()) {
            String[] arr = value.split(",");
            for (int i = 0; i < arr.length; i++) {
                result.add(Integer.valueOf(arr[i]));
            }
        }
        return result;
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
