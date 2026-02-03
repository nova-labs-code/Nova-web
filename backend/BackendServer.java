// Requires Java 11+ and Gson library
// Add Gson to your project: https://github.com/google/gson

import static spark.Spark.*;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.*;
import com.google.gson.*;

public class BackendServer {
    public static void main(String[] args) {
        port(4567); // Optional, set server port

        get("/search", (req, res) -> {
            String query = req.queryParams("q");
            if(query == null || query.isEmpty()) return "{}";

            String apiKey = "YOUR_API_KEY_HERE"; // Replace with your RapidAPI key
            String url = "https://google-search74.p.rapidapi.com/?" +
                         "query=" + URLEncoder.encode(query, "UTF-8") +
                         "&limit=20&related_keywords=true";

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("x-rapidapi-key", apiKey)
                    .header("x-rapidapi-host", "google-search74.p.rapidapi.com")
                    .GET()
                    .build();

            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            res.type("application/json");
            return response.body();
        });
    }
}