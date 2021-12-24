public class SubTask extends Task{
    boolean isEpic;

    public SubTask(String name, String details, int id, String status) {
        super(name, details, id, status);
        isEpic=false;
    }
}
