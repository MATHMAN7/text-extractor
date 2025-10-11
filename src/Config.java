import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {
    public static String getApiKey() throws IOException {
        Properties prop = new Properties();

        FileInputStream input = new FileInputStream("src/config.properties");
        prop.load(input);
        input.close();
        return prop.getProperty("OCR_API_KEY");
    }
}
