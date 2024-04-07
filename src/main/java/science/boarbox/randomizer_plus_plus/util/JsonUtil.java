package science.boarbox.randomizer_plus_plus.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public final class JsonUtil {
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
}
