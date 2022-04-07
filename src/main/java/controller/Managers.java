package controller;

import resourses.FileManager;

public class Managers {
    public static TotalManager getDefault() {
        return new HTTPTaskManager("http://localhost:8078/");
    }
}
