package resourses;

import controller.ManagerSaveException;

import java.io.File;
import java.io.IOException;

public class FileManager {
    public static File connectRepository(){
        File file=new File("data.csv");
        if (!file.isFile()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new ManagerSaveException("Произошла ошибка во время создания файла.");
            }
        }
        return file;
    }
}
