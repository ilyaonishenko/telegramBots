package me.glassfish.controllers;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import me.glassfish.models.AppProperties;
import me.glassfish.models.User;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by woqpw on 21.10.15.
 */
@Path("/redirect")
public class Application {
    private Logger logger = LoggerFactory.getLogger(Application.class);
    String code;
    com.sun.jersey.api.client.Client client = com.sun.jersey.api.client.Client.create();
    WebResource webResource;
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String redirectLink(){
        logger.info("glassfishes","in meth redirectLink");
        final String ending = "https://api.instagram.com/oauth/authorize/?";
        String redirectLink = ending+ AppProperties.getClient_id()+"&"+AppProperties.getRedirect_uri()+"&"+AppProperties.getResponse_type();
        return "<html><body>"+"<a link href="+redirectLink+">go to inst auth2</a>"+"</body></html>";
    }
    @GET
    @Path("/code")
    @Produces(MediaType.TEXT_PLAIN)
    public String setCode(@QueryParam("code")String code){
        logger.info("glassfishes");
        final String ending = "https://api.instagram.com/oauth/access_token";
        this.code = code;
        logger.info("code {}",code);
        String object = AppProperties.getClient_id()+"&"+AppProperties.getClient_secret()+"&"+AppProperties.getGrant_type()+"&"+AppProperties.getRedirect_uri()+"&code="+code;
        webResource = client.resource(ending);
        ClientResponse response = webResource.type("application/x-www-form-urlencoded").post(ClientResponse.class,object);
        String newObject = response.getEntity(String.class);
        JSONObject jsonObject = new JSONObject(newObject);
        JSONObject userJson = jsonObject.getJSONObject("user");
        User user = new User(userJson.get("username").toString(),userJson.get("bio").toString(),userJson.get("website").toString(),
                userJson.get("profile_picture").toString(),userJson.get("full_name").toString(),Integer.parseInt(userJson.getString("id")));
        logger.info("userClass {}",user);
        return newObject;
    }
}