import okhttp3.*;
import com.google.gson.*;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Main {
    private static final String OCR_URL = "https://api.ocr.space/parse/image";

    public static void main(String[] args) throws Exception {
        String apiKey = Config.getApiKey();
        System.out.println("API Key Loaded: " + apiKey);

        File imageFile = new File("src/test.png");
        String extractedText = extractTextFromImage(apiKey, imageFile);
        System.out.println("Extracted Text:\n" + extractedText);
    }

    public static String extractTextFromImage(String apiKey, File imageFile) throws IOException {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("apikey", apiKey)
                .addFormDataPart("file", imageFile.getName(),
                        RequestBody.create(MediaType.parse("image/png"), imageFile))
                .addFormDataPart("language", "eng")
                .build();

        Request request = new Request.Builder()
                .url(OCR_URL)
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();
        String jsonResponse = response.body().string();
        response.close();

        JsonObject obj = JsonParser.parseString(jsonResponse).getAsJsonObject();
        JsonArray parsedResults = obj.getAsJsonArray("ParsedResults");
        if (parsedResults != null && parsedResults.size() > 0) {
            return parsedResults.get(0).getAsJsonObject().get("ParsedText").getAsString();
        } else {
            return "No text found!";
        }
    }
}
