package httpResourses;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class KVClient {
    private static final String auth = "?API_KEY=";
    private String uri;
    private String registration;
    private final HttpClient client;

    public KVClient(String uri) {
        client = HttpClient.newHttpClient();
        this.uri = uri;
        URI connect = URI.create(uri + "register");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(connect)
                .GET()
                .build();
        try {
            final HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                registration = response.body();
            }

        } catch (NullPointerException | IOException | InterruptedException e) { // обрабатываем
            // ошибки отправки запроса
            System.out.println("Во время выполнения запроса возникла ошибка.\n" +
                    "Проверьте, пожалуйста, адрес и повторите попытку.");
        }

    }

    public void put(String key, String json) {
        URI connect = URI.create(uri + "save/" + key + auth + registration);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(connect)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
        try {
            client.send(request, HttpResponse.BodyHandlers.ofString());

        } catch (NullPointerException | IOException | InterruptedException e) {
            System.out.println("Во время выполнения запроса возникла ошибка.\n" +
                    "Проверьте, пожалуйста, адрес и повторите попытку.");
        }

    }

    public String load(String key) {
        URI connect = URI.create(uri + "load/" + key + auth + registration);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(connect)
                .GET()
                .build();
        try {
            final HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                return response.body();
            }

        } catch (NullPointerException | IOException | InterruptedException e) {
            System.out.println("Во время выполнения запроса возникла ошибка.\n" +
                    "Проверьте, пожалуйста, адрес и повторите попытку.");
        }
        return null;
    }
}
