package controller;

import resourses.FileManager;

public class Managers {
    public static TotalManager getDefault() {
        return new FileBackedTasksManager(FileManager.connectRepository());
    }
}
