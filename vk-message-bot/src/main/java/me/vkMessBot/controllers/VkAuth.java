package me.vkMessBot.controllers;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by woqpw on 12.10.15.
 */
public class VkAuth {
    private static String client_id = "client_id=5102517&";
    private static String main_url = "https://oauth.vk.com/authorize?";
    private static String scope = "scope= friends,photos,audio,video,messages&";
    private static String redirect_uri="redirect_uri=http://localhost:8080/redirect/getAccessToken&";
    private static String display = "display=page&";
    private static final String response_type = "response_type=code&";
    private static String apiVersion = "v=5.37";
    private static String code;
    private VkAuth(){}
    private static VkAuth vkAuth;
//    String httpsURL = main_url+client_id+scope+redirect_uri+response_type+apiVersion;
    static String httpsURL;
    public static VkAuth vkAuthInit(){
        if(vkAuth==null){
            vkAuth = new VkAuth();
        }
        return vkAuth;
    }
    private static String createLink(){
        return main_url+display+client_id+scope+redirect_uri+response_type+apiVersion;
    }
    public static void authorize(){
        httpsURL = createLink();
        try{
            URL url = new URL(httpsURL);
            HttpsURLConnection connection = (HttpsURLConnection)url.openConnection();
            //TODO запрос от сервера для получения code
            setCode(getCodeVK());
            //TODO сделать запрос к серверу для получения токена
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String getCodeVK() throws IOException {
        URL url = new URL(redirect_uri+"?code");
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        connection.setRequestMethod("GET");
        BufferedReader bufferedReader = new BufferedReader((
                new InputStreamReader(connection.getInputStream())
        ));
        String input;
        while((input = bufferedReader.readLine())!=null){
            System.out.println(input);
        }
        System.out.println("new input(code) is "+input);
        bufferedReader.close();
        System.out.println("buffReader closed2");
        return input;
    }

    public static String getCode() {
        return code;
    }

    public static void setCode(String code) {
        VkAuth.code = code;
    }
}
