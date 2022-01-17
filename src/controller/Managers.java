package controller;

public class Managers {
    public static TotalManager getDefault() {
        return new InMemoryTaskManager();
    }
}
