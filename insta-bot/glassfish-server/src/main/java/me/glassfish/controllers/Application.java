package me.glassfish.controllers;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import me.glassfish.models.AppProperties;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by woqpw on 21.10.15.
 */
@Path("/redirect")
public class Application {
    String code;
    com.sun.jersey.api.client.Client client = com.sun.jersey.api.client.Client.create();
    WebResource webResource;
    //TODO убрать необходимость кликать по ссылочке вручную. Сделать как в setcode.
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String redirectLink(){
        final String ending = "https://api.instagram.com/oauth/authorize/?";
        String redirectLink = ending+ AppProperties.getClient_id()+"&"+AppProperties.getRedirect_uri()+"&"+AppProperties.getResponse_type();
        return "<html><body>"+"<a link href="+redirectLink+">go to inst auth2</a>"+"</body></html>";
    }
    @GET
    @Path("/code")
    @Produces(MediaType.TEXT_PLAIN)
    public String setCode(@QueryParam("code")String code){
        final String ending = "https://api.instagram.com/oauth/access_token";
        this.code = code;
        String object = AppProperties.getClient_id()+"&"+AppProperties.getClient_secret()+"&"+AppProperties.getGrant_type()+"&"+AppProperties.getRedirect_uri()+"&code="+code;
        webResource = client.resource(ending);
        ClientResponse response = webResource.type("application/x-www-form-urlencoded").post(ClientResponse.class,object);
        String newObject = response.getEntity(String.class);
        System.out.println(newObject);
        return newObject;
    }
}