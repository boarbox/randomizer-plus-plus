package science.boarbox.randomizer_plus_plus.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public final class JsonUtil {
    private static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .enableComplexMapKeySerialization()
            .create();

    public static String readToString(InputStream stream) {
        var isr = new InputStreamReader(stream, StandardCharsets.UTF_8);
        var reader = new BufferedReader(isr);
        return String.join("\n", reader.lines().toArray(String[]::new));
    }


    public static JsonElement readToJson(InputStream stream) {
        return JsonUtil.readToJson(JsonUtil.readToString(stream));
    }

    public static JsonElement readToJson(String input) {
        return JsonParser.parseString(input);
    }

    public static void saveToFile(Path path, String name, JsonElement contents) {
        File file = path.resolve(name).toFile();
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            FileUtils.writeStringToFile(file, gson.toJson(contents), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
