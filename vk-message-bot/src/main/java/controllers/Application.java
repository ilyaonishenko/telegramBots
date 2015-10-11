package controllers;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import org.json.JSONArray;
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
        try {
            run();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                    int chatid = message
                            .getJSONObject("chat")
                            .getInt("id");
                    String username = message
                            .getJSONObject("chat")
                            .getString("username");
                    String text = message
                            .getString("text");
                    if (text.contains("/start")){
                        String reply = "Hi, this is an cool bot\n" +
                                "Your chat_id is " + chatid + "\n" +
                                "Your username is " + username;
                        sendMessage(chatid,reply);
                    }
                    else if (text.contains("/echo")){
                        sendMessage(chatid,"Received "+text);
                    }
                    else if (text.contains("/toupper")){
                        String param = text.substring("/toupper".length(),text.length());
                        sendMessage(chatid,param.toUpperCase());
                    }
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
}
