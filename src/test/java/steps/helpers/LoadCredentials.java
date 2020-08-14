package steps.helpers;

import jdk.nashorn.internal.parser.JSONParser;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class LoadCredentials {
    public static String getCredentialsFromJson(String filePath, String JsonKey) throws FileNotFoundException {
            FileReader reader = new FileReader(filePath);
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
            String jsonValue = (String) jsonObject.get(JsonKey);
    return jsonValue;

    }
}