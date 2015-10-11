package controllers;

import org.json.JSONObject;
import properties.BotProperties;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by woqpw on 11.10.15.
 */
public class Application {
    public static void main(String[] args) {
        init();
    }
    public static void init(){
        JSONObject message;
        String httpsURL = "https://api.telegram.org/bot"+BotProperties.token+"/getMe";
        try {
            URL url = new URL(httpsURL);
            HttpsURLConnection connection = (HttpsURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            String input;
            while((input = bufferedReader.readLine())!=null){
                System.out.println(input);
            }
            bufferedReader.close();
            System.out.println("closed");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
