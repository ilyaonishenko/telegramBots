package me.glassfish.controllers;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import me.glassfish.models.Info;
import org.glassfish.jersey.client.ClientConfig;
import org.json.JSONObject;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import java.awt.*;
import java.net.URI;

/**
 * Created by woqpw on 21.10.15.
 */
@Path("/redirect")
public class Application {
    private static final String webServiceURI1 = "http://localhost:8080/glassfish/gf";
    private static String webServiceURI2 = "https://api.instagram.com/oauth/access_token/?client_id=eddc8161a72f4c8bb37a80bae0c6c3f4&client_secret=0df8b6a629a84427a100b6234af16b53&grant_type=authorization_code&redirect_uri=http://localhost:8080/glassfish/gf/redirect/code&code=";
    private String code;
    private String token;
    //TODO убрать необходимость кликать по ссылочке вручную. Сделать как в setcode.
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String redirectLink(){
        String redirectLink = "http://localhost:8080/glassfish/gf/redirect";
        redirectLink = "https://api.instagram.com/oauth/authorize/?client_id=eddc8161a72f4c8bb37a80bae0c6c3f4&redirect_uri=http://localhost:8080/glassfish/gf/redirect/code&response_type=code";
//        &scope=basic+comments+relationships+likes
        return "<html><body>"+"<a link href="+redirectLink+">go to inst auth2</a>"+"</body></html>";
    }
    /*
    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("/new")
    public String redirectLink1(){
        String redirectLink = "http://localhost:8080/glassfish/gf/redirect";
        redirectLink = "https://api.instagram.com/oauth/authorize/?client_id=eddc8161a72f4c8bb37a80bae0c6c3f4&redirect_uri=http://localhost:8080/glassfish/gf/redirect/codenew&response_type=token";
//        &scope=basic+comments+relationships+likes
        return "<html><body>"+"<a link href="+redirectLink+">go to inst auth2</a>"+"</body></html>";
    }
    @GET
    @Path("/codenew")
    @Produces(MediaType.TEXT_HTML)
    public String setCodeNew(@QueryParam("access_token")String access_token){
        System.out.println(access_token);
        return "<html><body><h1>"+access_token+"</h1></body></html>";
    }*/
    @GET
    @Path("/code")
    @Produces(MediaType.TEXT_PLAIN)
    public String setCode(@QueryParam("code")String code){
        System.out.println(code);
        this.code = code;
        String client_id ="eddc8161a72f4c8bb37a80bae0c6c3f4";
        String client_secret = "0df8b6a629a84427a100b6234af16b53";
        String redirect_uri = "http://localhost:8080/glassfish/gf/redirect/code";
        String code1 = "code="+code;
        /*ClientConfig clientConfig = new ClientConfig();
        Client client = ClientBuilder.newClient(clientConfig);
        webServiceURI2=webServiceURI2+code;
        URI serviceURI = UriBuilder.fromUri(webServiceURI2).build();
        WebTarget webTarget = client.target(serviceURI);
        System.out.println("still working");
        System.out.println(webTarget.request().accept(MediaType.APPLICATION_JSON).get(JSONObject.class).toString());*/
//        ClientConfig clientConfig = new ClientConfig();
//        Client client = ClientBuilder.newClient(clientConfig);
//        webServiceURI2=webServiceURI2+code;
        String link = "https://api.instagram.com/oauth/access_token";
//        URI serviceURI = UriBuilder.fromUri(link).build();
//        WebTarget webTarget = client.target(serviceURI);
//        JSONObject object = createJSONObject(client_id,client_secret,redirect_uri,code1);
//        System.out.println(object);
        Info info = new Info(client_id,client_secret,redirect_uri,code);
//        webTarget.request(MediaType.APPLICATION_JSON_TYPE)
//                .post(Entity.entity(info,MediaType.APPLICATION_JSON_TYPE));
//        JSONObject newObject = webTarget.request().accept(MediaType.APPLICATION_JSON_TYPE).get(JSONObject.class);
//        String object= "\"client_id\":\"eddc8161a72f4c8bb37a80bae0c6c3f4\",\"client_secret\":\"0df8b6a629a84427a100b6234af16b53\",\"grant_type\":\"authorization_code\",\"redirect_uri\":\"http://localhost:8080/glassfish/gf/redirect/code\",\"code\":\""+code+"\"";
        String object= "client_id=eddc8161a72f4c8bb37a80bae0c6c3f4&client_secret=0df8b6a629a84427a100b6234af16b53&grant_type=authorization_code&redirect_uri=http://localhost:8080/glassfish/gf/redirect/code&code="+code;
        com.sun.jersey.api.client.Client client = com.sun.jersey.api.client.Client.create();
        WebResource webResource = client.resource(link);
        ClientResponse response = webResource.type("application/x-www-form-urlencoded").post(ClientResponse.class,object);
        String newObject = response.getEntity(String.class);
        System.out.println(newObject);
        return newObject;
    }
    public String getCode() {
        return code;
    }
    /*@Path("/token")
    public void setToken(){
        String redirectLink = "https://api.instagram.com/oauth/access_token/?client-id=eddc8161a72f4c8bb37a80bae0c6c3f4&client_secret=0df8b6a629a84427a100b6234af16b53&grant_type=authorization_code&redirect_uri=http://localhost:8080/glassfish/gf/redirect/code&code="+getCode();
    }*/
    public String getToken() {
        return token;
    }
    private JSONObject createJSONObject(String client_id,String client_secret,
                                        String redirect_uri,String code){
        JSONObject object = new JSONObject();
        object.put("client_id",client_id);
        object.put("client_secret",client_secret);
        object.put("grant_type","authorization_code");
        object.put("redirect_uri",redirect_uri);
        object.put("code",code);
        return object;
    }
}