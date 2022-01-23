package core;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class network {
    public static JsonObject Post(JsonObject Jsondata)  {
        String data = Jsondata.toString();
        logger.log("TRACE","Core_Post",data);
        URL url = null;
        try {
            url = new URL("http://150.117.110.118:10150/");
        } catch (MalformedURLException e) {
            logger.log("ERROR","Core_Post",e.getMessage());
        }
        HttpURLConnection http = null;
        try {
            assert url != null;
            http = (HttpURLConnection)url.openConnection();
        } catch (IOException e) {
            logger.log("ERROR","Core_Post",e.getMessage());
        }
        try {
            assert http != null;
            http.setRequestMethod("POST");
        } catch (ProtocolException e) {
            logger.log("ERROR","Core_Post",e.getMessage());
        }
        http.setDoOutput(true);
        http.setConnectTimeout(5000);
        http.setReadTimeout(5000);
        http.setRequestProperty("Authorization", "Bearer mt0dgHmLJMVQhvjpNXDyA83vA_PxH23Y");
        http.setRequestProperty("Content-Type", "application/json");
        byte[] out = data.getBytes(StandardCharsets.UTF_8);
        OutputStream stream = null;
        try {
            stream = http.getOutputStream();
        } catch (IOException e) {
            logger.log("ERROR","Core_Post",e.getMessage());
        }
        try {
            assert stream != null;
            stream.write(out);
        } catch (IOException e) {
            logger.log("ERROR","Core_Post",e.getMessage());
        }
        String inputLine;
        StringBuilder response = new StringBuilder();
        try {
            if(http.getResponseCode() == HttpURLConnection.HTTP_OK){
                BufferedReader in = new BufferedReader(new InputStreamReader(http.getInputStream()));
                while ((inputLine = in .readLine()) != null) {
                    response.append(inputLine);
                } in .close();
            }else {
                logger.log("ERROR","Core_Post","API service did not respond");
                return null;
            }
        } catch (Exception e) {
            logger.log("ERROR","Core_Post",e.getMessage());
        }
            JsonElement jsonElement = JsonParser.parseString(response.toString());
            logger.log("DEBUG", "Core_Post", response.toString());
            if(!response.isEmpty()) {
                return jsonElement.getAsJsonObject();
            }else {
                return null;
            }
    }
}
