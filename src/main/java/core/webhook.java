package core;

import com.google.gson.JsonObject;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class webhook {
    public static String Webhook(String URL, JsonObject Jsondata){
        String data = Jsondata.toString();
        logger.log("TRACE","Core_Webhook",data);
        URL url = null;
        try {
            url = new URL(URL);
        } catch (MalformedURLException e) {
            logger.log("ERROR","Core_Webhook",e.getMessage());
        }
        HttpURLConnection http = null;
        try {
            assert url != null;
            http = (HttpURLConnection)url.openConnection();
        } catch (IOException e) {
            logger.log("ERROR","Core_Webhook",e.getMessage());
        }
        try {
            assert http != null;
            http.setRequestMethod("POST");
        } catch (ProtocolException e) {
            logger.log("ERROR","Core_Webhook",e.getMessage());
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
            logger.log("ERROR","Core_Webhook",e.getMessage());
        }
        try {
            assert stream != null;
            stream.write(out);
        } catch (IOException | NullPointerException e) {
            logger.log("ERROR","Core_Webhook",e.getMessage());
        }
        try {
            if(http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                return "Success";
            }else {
                return "null";
            }
        } catch (IOException e) {
            logger.log("ERROR","Core_Webhook",e.getMessage());
            return "null";
        }
    }
}
