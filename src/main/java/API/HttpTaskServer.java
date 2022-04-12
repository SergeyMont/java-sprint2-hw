package API;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import controller.Managers;
import controller.TotalManager;
import model.EpicTask;
import model.StatusTask;
import model.SubTask;
import model.Task;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class HttpTaskServer {
    private static final int PORT = 8080;
    private HttpServer httpServer;

    public HttpTaskServer() throws IOException {
        this.httpServer = HttpServer.create(new InetSocketAddress("localhost", PORT), 0);
        TotalManager taskManager = Managers.getDefault();
        Task task = new Task("Wash", "Load", 2, StatusTask.NEW, 30L,
                "02.01.2020-12:30");
        taskManager.addNewTask(task);
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();


        httpServer.createContext("/tasks", tasksHttpHandler(taskManager, mapper));

        httpServer.createContext("/tasks/history", historyHttpHandler(taskManager, mapper));

        httpServer.createContext("/tasks/prioritize", prioritizeHttpHandler(taskManager, mapper));

        httpServer.createContext("/tasks/task", taskHttpHandler(taskManager, mapper));


        httpServer.createContext("/tasks/epic", epicHttpHandler(taskManager, mapper));

        httpServer.createContext("/tasks/subtask", subtaskHttpHandler(taskManager, mapper));
    }

    private HttpHandler subtaskHttpHandler(TotalManager taskManager,
                                           ObjectMapper mapper) {
        return exchange -> {
            try {
                switch (exchange.getRequestMethod()) {
                    case "GET":
                        String key = exchange.getRequestURI().getRawQuery();
                        String response;
                        if (key.isEmpty() & exchange.getRequestBody() != null) {
                            response =
                                    mapper.writeValueAsString(taskManager.findTaskByEpic(mapper.readValue(exchange.getRequestBody(), EpicTask.class)));
                        } else {
                            int id = Integer.parseInt(key);
                            response = mapper.writeValueAsString(taskManager.findSubtaskById(id));
                        }
                        Headers headers = exchange.getResponseHeaders();
                        headers.set("Content-Type",
                                "application/json");
                        exchange.sendResponseHeaders(200, response.length());

                        try (OutputStream os = exchange.getResponseBody()) {
                            os.write(response.getBytes());
                        }
                        break;
                    case "POST":
                        if (exchange.getRequestBody() != null) {
                            taskManager.addNewTask(mapper.readValue(exchange.getRequestBody(),
                                    SubTask.class));
                            exchange.sendResponseHeaders(201, 0);
                        } else {
                            exchange.sendResponseHeaders(400, 0);
                        }
                        break;
                    case "PUT":
                        if (exchange.getRequestBody() != null) {
                            taskManager.updateTask(mapper.readValue(exchange.getRequestBody(),
                                    SubTask.class));
                            exchange.sendResponseHeaders(204, 0);
                        } else {
                            exchange.sendResponseHeaders(400, 0);
                        }
                        break;
                    case "DELETE":
                        String k = exchange.getRequestURI().getRawQuery();
                        if (!k.isEmpty()) {
                            int id = Integer.parseInt(k);
                            taskManager.removeSubTaskByID(id);
                            exchange.sendResponseHeaders(204, -1);
                        } else {
                            exchange.sendResponseHeaders(400, 0);
                        }
                        break;
                    default:
                        System.out.println("/tasks/subtask ждёт HTTP-запрос, а получил " + exchange.getRequestMethod());
                        exchange.sendResponseHeaders(405, 0);
                }
            } finally {
                exchange.close();
            }
        };
    }

    private HttpHandler taskHttpHandler(TotalManager taskManager,
                                        ObjectMapper mapper) {
        return exchange -> {
            try {
                switch (exchange.getRequestMethod()) {
                    case "GET":
                        String key = exchange.getRequestURI().getRawQuery();
                        String response;
                        if (key.isEmpty()) {
                            response = mapper.writeValueAsString(taskManager.findAllTasks());
                        } else {
                            int id = Integer.parseInt(key);
                            response = mapper.writeValueAsString(taskManager.findTaskById(id));
                        }
                        Headers headers = exchange.getResponseHeaders();
                        headers.set("Content-Type",
                                "application/json");
                        exchange.sendResponseHeaders(200, response.length());

                        try (OutputStream os = exchange.getResponseBody()) {
                            os.write(response.getBytes());
                        }
                        break;
                    case "POST":
                        if (exchange.getRequestBody() != null) {
                            taskManager.addNewTask(mapper.readValue(exchange.getRequestBody(),
                                    Task.class));
                            exchange.sendResponseHeaders(201, 0);
                        } else {
                            exchange.sendResponseHeaders(400, 0);
                        }
                        break;
                    case "PUT":
                        if (exchange.getRequestBody() != null) {
                            taskManager.updateTask(mapper.readValue(exchange.getRequestBody(),
                                    Task.class));
                            exchange.sendResponseHeaders(204, 0);
                        } else {
                            exchange.sendResponseHeaders(400, 0);
                        }
                        break;
                    case "DELETE":
                        String k = exchange.getRequestURI().getRawQuery();
                        if (!k.isEmpty()) {
                            int id = Integer.parseInt(k);
                            taskManager.removeTaskById(id);
                            exchange.sendResponseHeaders(204, -1);
                        } else {
                            exchange.sendResponseHeaders(400, 0);
                        }
                        break;
                    default:
                        System.out.println("/tasks/task ждёт HTTP-запрос, а получил " + exchange.getRequestMethod());
                        exchange.sendResponseHeaders(405, 0);
                }
            } finally {
                exchange.close();
            }
        };

    }

    private HttpHandler prioritizeHttpHandler(TotalManager taskManager, ObjectMapper mapper) {
        return exchange -> {
            try {
                switch (exchange.getRequestMethod()) {
                    case "GET":
                        String response =
                                mapper.writeValueAsString(taskManager.getPrioritizedTasks());
                        Headers headers = exchange.getResponseHeaders();
                        headers.set("Content-Type",
                                "application/json");
                        exchange.sendResponseHeaders(200, response.length());

                        try (OutputStream os = exchange.getResponseBody()) {
                            os.write(response.getBytes());
                        }
                        break;
                    default:
                        System.out.println("/tasks/prioritize ждёт GET-запрос, а получил " + exchange.getRequestMethod());
                        exchange.sendResponseHeaders(405, 0);
                }
            } finally {
                exchange.close();
            }
        };
    }

    private HttpHandler epicHttpHandler(TotalManager taskManager,
                                        ObjectMapper mapper) {
        return exchange -> {
            try {
                switch (exchange.getRequestMethod()) {
                    case "GET":
                        String key = exchange.getRequestURI().getRawQuery();
                        String response;
                        if (key.isEmpty()) {
                            response = mapper.writeValueAsString(taskManager.findAllEpicTask());
                        } else {
                            int id = Integer.parseInt(key);
                            response = mapper.writeValueAsString(taskManager.findEpicTaskById(id));
                        }
                        Headers headers = exchange.getResponseHeaders();
                        headers.set("Content-Type",
                                "application/json");
                        exchange.sendResponseHeaders(200, response.length());

                        try (OutputStream os = exchange.getResponseBody()) {
                            os.write(response.getBytes());
                        }
                        break;
                    case "POST":
                        if (exchange.getRequestBody() != null) {
                            taskManager.addNewTask(mapper.readValue(exchange.getRequestBody(),
                                    EpicTask.class));
                            exchange.sendResponseHeaders(201, 0);
                        } else {
                            exchange.sendResponseHeaders(400, 0);
                        }
                        break;
                    case "PUT":
                        if (exchange.getRequestBody() != null) {
                            taskManager.updateTask(mapper.readValue(exchange.getRequestBody(),
                                    EpicTask.class));
                            exchange.sendResponseHeaders(204, 0);
                        } else {
                            exchange.sendResponseHeaders(400, 0);
                        }
                        break;
                    case "DELETE":
                        String k = exchange.getRequestURI().getRawQuery();
                        if (!k.isEmpty()) {
                            int id = Integer.parseInt(k);
                            taskManager.removeEpicTaskByID(id);
                            exchange.sendResponseHeaders(204, -1);
                        } else {
                            exchange.sendResponseHeaders(400, 0);
                        }
                        break;
                    default:
                        System.out.println("/tasks/epic ждёт HTTP-запрос, а получил " + exchange.getRequestMethod());
                        exchange.sendResponseHeaders(405, 0);
                }
            } finally {
                exchange.close();
            }
        };
    }

    private HttpHandler historyHttpHandler(TotalManager taskManager, ObjectMapper mapper) {
        return exchange -> {
            try {
                switch (exchange.getRequestMethod()) {
                    case "GET":
                        String response = mapper.writeValueAsString(taskManager.getHistory());
                        Headers headers = exchange.getResponseHeaders();
                        headers.set("Content-Type",
                                "application/json");
                        exchange.sendResponseHeaders(200, response.length());

                        try (OutputStream os = exchange.getResponseBody()) {
                            os.write(response.getBytes());
                        }
                        break;
                    default:
                        System.out.println("/tasks/history ждёт GET-запрос, а получил " + exchange.getRequestMethod());
                        exchange.sendResponseHeaders(405, 0);
                }
            } finally {
                exchange.close();
            }
        };
    }

    private HttpHandler tasksHttpHandler(TotalManager taskManager, ObjectMapper mapper) {
        return exchange -> {
            try {
                switch (exchange.getRequestMethod()) {
                    case "GET":
                        String response = mapper.writeValueAsString(taskManager.findAllTasks());
                        Headers headers = exchange.getResponseHeaders();
                        headers.set("Content-Type",
                                "application/json");
                        exchange.sendResponseHeaders(200, response.length());

                        try (OutputStream os = exchange.getResponseBody()) {
                            os.write(response.getBytes());
                        }
                        break;
                    case "DELETE":
                        taskManager.removeAll();
                        exchange.sendResponseHeaders(204, -1);
                        break;
                    default:
                        System.out.println("/tasks ждёт GET/DELETE-запрос, а получил " + exchange.getRequestMethod());
                        exchange.sendResponseHeaders(405, 0);
                }
            } finally {
                exchange.close();
            }
        };
    }

    public void start() {
        System.out.println("Запускаем сервер на порту " + PORT);
        System.out.println("Открой в браузере http://localhost:" + PORT + "/");
        httpServer.start();
    }
}
