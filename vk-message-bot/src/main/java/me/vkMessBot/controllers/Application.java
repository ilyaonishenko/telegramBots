package me.vkMessBot.controllers;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import me.vkMessBot.properties.BotProperties;
import org.glassfish.jersey.client.ClientConfig;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.UriBuilder;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;


/**
 * Created by woqpw on 11.10.15.
 */
public class Application{
    private static final String webServiceURI = "http://localhost:8080/RESTful_Jersey_Hello_World";
    public static void main(String[] args) {
        init();
        ClientConfig clientConfig = new ClientConfig();
        Client client = ClientBuilder.newClient(clientConfig);
        URI serviceURI = UriBuilder.fromUri(webServiceURI).build();
        WebTarget webTarget = client.target(serviceURI);
    }
    public static void init(){
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

    public static void run() throws Exception{
        int last_update_id = 0;
        HttpResponse<JsonNode> response;
        while (true){
            response = getUpdates(last_update_id++);
            if(response.getStatus()==200){
                JSONArray responses = response.getBody().getObject().getJSONArray("result");
                if(responses.isNull(0))
                    continue;
                else last_update_id = responses
                        .getJSONObject(responses.length()-1)
                        .getInt("update_id")+1;
                for(int i=0;i<responses.length();i++){
                    JSONObject message = responses
                            .getJSONObject(i)
                            .getJSONObject("message");
                    System.out.println("this message is "+message);
                    int chatid = message
                            .getJSONObject("chat")
                            .getInt("id");
                    String username = message
                            .getJSONObject("chat")
                            .getString("username");
                    String text = message
                            .getString("text");
                    System.out.println("message text is "+text);
                    if (text.contains("/start")){
                        System.out.println("start");
                        String[] reply1 = new String[]{"AUTHORIZATION ON VK","two"};
                        String[] reply2 = new String[]{"three","hide_keyboard"};
                        sendMessage(chatid,"you mean start?",customKeyboard(reply1,reply2));
                    }
                    /*else if (text.contains("/echo")){
                        System.out.println("echo");
                        sendMessage(chatid,"Received "+text);
                    }
                    else if (text.contains("/toupper")){
                        System.out.println("toupper");
                        String param = text.substring("/toupper".length(),text.length());
                        sendMessage(chatid,param.toUpperCase());
                    }
                    else if(text.contains("hide_keyboard")){
                        System.out.println("i'm here");
                        sendMessage(chatid, "no keyboard ok", customKeyboardHide());
                    }*/
                }
            }
        }
    }




    public static HttpResponse<JsonNode> getUpdates(int offset) throws Exception{
        return Unirest.post(BotProperties.endpoint+BotProperties.token+"/getUpdates")
                .field("offset",offset)
                .asJson();
    }
    public static HttpResponse<JsonNode> sendMessage(int chatid, String text) throws Exception{
        return Unirest.post(BotProperties.endpoint+BotProperties.token+"/sendMessage")
                .field("chat_id",chatid)
                .field("text",text)
                .asJson();
    }
    public static JSONObject customKeyboard(String[] array1,String[] array2) throws Exception{
        JSONObject object = new JSONObject();
        object.append("keyboard",array1);
        object.append("keyboard",array2);
        System.out.println(object.toString());
        return  object;
    }
    public static HttpResponse<JsonNode> sendMessage(int chatid,String text,JSONObject object) throws  Exception{
        return Unirest.post(BotProperties.endpoint + BotProperties.token + "/sendMessage")
                .field("chat_id", chatid)
                .field("text",text)
                .field("reply_markup", object)
                .asJson();
    }
    public static JSONObject customKeyboardHide(){
        JSONObject object = new JSONObject();
        object.append("hide_keyboard",true);
        return object;
    }
}
