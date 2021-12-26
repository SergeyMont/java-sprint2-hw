import model.EpicTask;
import model.Status;
import model.SubTask;
import model.Task;

import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    int idGenerator=0;

    HashMap<Integer,Task> tasks=new HashMap<>();
    HashMap<EpicTask, ArrayList<SubTask>> epicTaskSubTask=new HashMap<>();

//    Получение списка всех задач.
    public ArrayList<Task> findAllTasks(){
        ArrayList<Task>allTasks=new ArrayList<>(tasks.values());
        for (ArrayList<SubTask> v: epicTaskSubTask.values()) {
            allTasks.addAll(v);
        }
        return allTasks;
    }
//    Получение списка всех эпиков.
    public ArrayList<Task> findAllEpicTask(){
        ArrayList<Task>allEpic=new ArrayList<>(epicTaskSubTask.keySet());
        return allEpic;
    }
//    Получение списка всех подзадач определённого эпика.
    public ArrayList<SubTask> findTaskByEpic(EpicTask epic){
        return epicTaskSubTask.get(epic);
    }
//    Получение задачи любого типа по идентификатору.
    public Task findTaskById(int id){
        Task task=new  Task();
        for (Task t: tasks.values()) {
            if(t.getId() == id){
                task=t;
            }else{
                task=null;
            }
        }
        for (EpicTask epic: epicTaskSubTask.keySet()) {
            if(epic.getId()==id){
                task=epic;
            }else{task=null;}
        }
        for (ArrayList<SubTask>arrSub: epicTaskSubTask.values()) {
            for (SubTask sub: arrSub) {
                if (sub.getId()==id){
                    task=sub;
                }else{task=null;}
            }
        }
        return task;
    }
//    Добавление новой задачи, эпика и подзадачи. Сам объект должен передаваться в качестве параметра.
    public void addNewTask (Task task){
        Task t=new Task();
        if (t.compareClass(task)) {
        tasks.put(task.getId(), task);
        }

        EpicTask epic=new EpicTask();
        if (epic.compareClass(task)) {
        epicTaskSubTask.put((EpicTask) task, new ArrayList<>());
        }

        SubTask sub=new SubTask();
        if (sub.compareClass(task)) {
            sub=(SubTask) task;
            for (EpicTask ep: epicTaskSubTask.keySet()) {
                if(ep.getId()==sub.getEpicID()){
                    epicTaskSubTask.get(ep).add(sub);
                }
            }
        }
    }
//    Обновление задачи любого типа по идентификатору. Новая версия объекта передаётся в виде параметра.
    public void updateTask(Task task){
        Task t=new Task();
        if (t.compareClass(task)) {
            tasks.replace(task.getId(), task);
        }

        EpicTask epic=new EpicTask();
        if (epic.compareClass(task)) {
            epic=(EpicTask) task;
            HashMap <EpicTask, ArrayList<SubTask>>save=new HashMap();
            for (EpicTask ep: epicTaskSubTask.keySet()) {
                if(ep.getId()==epic.getId()){
                    save.put(epic,epicTaskSubTask.get(ep));
                    epicTaskSubTask.remove(ep);
                    epicTaskSubTask.putAll(save);

                }
            }
        }

        SubTask sub=new SubTask();
        if (sub.compareClass(task)) {
            sub=(SubTask) task;
            for (EpicTask ep: epicTaskSubTask.keySet()) {
                if(ep.getId()==sub.getEpicID()){
                    ArrayList<SubTask>local=epicTaskSubTask.get(ep);
                    for (SubTask s: local) {
                        if (s.getId()== sub.getId()){
                            local.remove(s);
                            local.add(sub);
                            epicTaskSubTask.put(ep,local);
                        }
                    }
                }
            }
        }
    }
//    Удаление ранее добавленных задач — всех и по идентификатору.
    public void removeAll(){
        tasks.clear();
        epicTaskSubTask.clear();
    }

    public void removeById(int id){
        if(tasks.containsKey(id)){
            tasks.remove(id);
        }

        for (EpicTask ep: epicTaskSubTask.keySet()) {
            if(ep.getId()==id){
                epicTaskSubTask.remove(ep);
            }else{
                for (SubTask sub:epicTaskSubTask.get(ep)) {
                    if(sub.getId()==id){
                        epicTaskSubTask.get(ep).remove(sub);
                    }
                }
            }
        }
    }

    public void refreshEpicStatus(){
        for (EpicTask ep: epicTaskSubTask.keySet()) {
            if(epicTaskSubTask.get(ep).isEmpty()){
                ep.setStatus(Status.NEW);
            }else {
                boolean isNewStatus=false;
                boolean isDoneStatus=false;
                for (SubTask sub:epicTaskSubTask.get(ep)) {
                    if(sub.getStatus()==Status.NEW){
                        isNewStatus=true;
                    }else if(sub.getStatus()==Status.DONE){
                        isDoneStatus=true;
                    }
                }
                if(isNewStatus && !isDoneStatus){
                 ep.setStatus(Status.NEW);
                }else if(isDoneStatus && !isNewStatus){
                    ep.setStatus(Status.DONE);
                }else{
                    ep.setStatus(Status.IN_PROGRESS);
                }
            }
        }
    }
}
