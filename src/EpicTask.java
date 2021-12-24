public class EpicTask extends Task {

    boolean isEpic;

    public EpicTask(String name, String details, int id, String status) {
        super(name, details, id, status);
        isEpic=true;
    }
}
