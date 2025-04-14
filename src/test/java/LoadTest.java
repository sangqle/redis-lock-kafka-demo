import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoadTest {
    public static void main(String[] args) {
        int numberOfRequests = 100;
        int threadPoolSize = 20;  // Adjust based on your testing need
        ExecutorService executor = Executors.newFixedThreadPool(threadPoolSize);
        HttpClient client = HttpClient.newHttpClient();

        for (int i = 0; i < numberOfRequests; i++) {
            final int id = i;
            executor.submit(() -> {
                String requestId = "req-" + id;
                String url = "http://localhost:8088/api/data/update?requestId=" + requestId + "&delta=1";
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .POST(HttpRequest.BodyPublishers.noBody())
                        .build();
                try {
                    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                    System.out.println("Response for " + requestId + ": " + response.body());
                } catch (Exception e) {
                    System.err.println("Error with " + requestId);
                    e.printStackTrace();
                }
            });
        }
        executor.shutdown();
    }
}
