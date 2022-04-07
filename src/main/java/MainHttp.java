import API.HttpTaskServer;
import httpResourses.KVServer;

import java.io.IOException;

public class MainHttp {
    public static void main(String[] args) {
        try {
            HttpTaskServer server=new HttpTaskServer();
            server.start();
            KVServer base=new KVServer();
            base.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
