import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Main {
    public static void main(String[] args) throws Exception {
        // Create OkHttpClient instance
        OkHttpClient client = new OkHttpClient();

        // Build a GET request to a test server
        Request request = new Request.Builder()
                .url("https://httpbin.org/get") // dummy test server
                .build();

        // Execute the request and get the response
        Response response = client.newCall(request).execute();

        // Print the response body
        System.out.println(response.body().string());

        // Close the response
        response.close();
    }
}

