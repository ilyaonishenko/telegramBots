package me.vkMessBot.controllers;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by woqpw on 12.10.15.
 */
public class VkAuth {
    private String client_id = "5102517&";
    private String main_url = "https://oauth.vk.com/authorize?";
    private String scope = "scope= friends,photos,audio,video,messages&";
    private String redirect_uri="http://localhost:8080/getAccessToken";
    private final String response_type = "code";
    private String apiVersion = "5.37";
    public void authorize(){
        String httpsURL = main_url+client_id+scope+redirect_uri+response_type+apiVersion;
        try{
            URL url = new URL(httpsURL);
            HttpsURLConnection connection = (HttpsURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader bufferedReader = new BufferedReader((
                    new InputStreamReader(connection.getInputStream())
                    ));
            String input;
            while((input = bufferedReader.readLine())!=null){
                System.out.println(input);
            }
            bufferedReader.close();
            System.out.println("buffReader closed");
            //TODO запрос от сервера для получения code

            //TODO сделать запрос к серверу для получения токена
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public
}
