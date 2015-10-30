package me.glassfish.controllers;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import me.glassfish.models.Subscription;
import me.glassfish.models.SubscriptionDAO;
import me.glassfish.models.UserDAO;
import me.glassfish.properties.AppProperties;
import me.glassfish.models.User;
import org.json.JSONArray;
import org.json.JSONObject;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
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
    String access_token;
    com.sun.jersey.api.client.Client client = com.sun.jersey.api.client.Client.create();
    WebResource webResource;
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String redirectLink(){
        logger.info("glassfishes {}","in meth redirectLink");
        final String ending = "https://api.instagram.com/oauth/authorize/?";
        String redirectLink = ending+ AppProperties.getClient_id()+"&"+AppProperties.getRedirect_uri()+"&"+AppProperties.getResponse_type();
        return "<html><body>"+"<a link href="+redirectLink+">go to inst auth2</a>"+"</body></html>";
    }
    @GET
    @Path("/code")
    @Produces(MediaType.TEXT_PLAIN)
    public String setCode(@QueryParam("code")String code){
        UserDAO.getUserDAO();
        logger.info("glassfishes");
        final String ending = "https://api.instagram.com/oauth/access_token";
        this.code = code;
        String object = AppProperties.getClient_id()+"&"+AppProperties.getClient_secret()+"&"+AppProperties.getGrant_type()+"&"+AppProperties.getRedirect_uri()+"&code="+code;
        webResource = client.resource(ending);
        ClientResponse response = webResource.type("application/x-www-form-urlencoded").post(ClientResponse.class,object);
        String newObject = response.getEntity(String.class);
        JSONObject jsonObject = new JSONObject(newObject);
        JSONObject userJson = jsonObject.getJSONObject("user");
        access_token = jsonObject.getString("access_token");
        User user = new User(userJson.get("username").toString(),userJson.get("bio").toString(),userJson.get("website").toString(),
                userJson.get("profile_picture").toString(),userJson.get("full_name").toString(),Integer.parseInt(userJson.getString("id")),
                access_token);
        logger.info("userClass {}",user);
        UserDAO.addUser(user);
        Subscription subscription1 = new Subscription("pavelvelikov",1338289792,1338289792);
        Subscription subscription2 = new Subscription("caradelevingne",3255807,1338289792);
        SubscriptionDAO.addSubscription(subscription1);
        SubscriptionDAO.addSubscription(subscription2);
//        datastore.save(user);
//        Container.addToSubscriptions("pavelvelikov",1338289792);
//        Container.addToSubscriptions("caradelevingne",3255807);
        return newObject;
    }
    @GET
    @Path("/getLastUpdate")
    @Produces(MediaType.TEXT_PLAIN)
    public String getLastUpdate(@QueryParam("username")String username,@QueryParam("clientname")String clientname){
        String endpoint = "https://api.instagram.com/v1/users/";
        /*subscriptions.put("pavelvelikov",1338289792);
        subscriptions.put("caradelevingne",3255807);
        users.put(user.getUsername(),user);*/
        logger.info("username is {}",username);
        logger.info("clientname is {}",clientname);
        String finalLink;
//        if(Container.subscriptions.containsKey(username)){
//            String token = Container.users.get(clientname).getAccess_token();
//            String fullLink = endpoint+Container.subscriptions.get(username)+"/media/recent"+"?access_token="+token;
        String fullLink = "www";
            webResource = client.resource(fullLink);
            JSONObject bigObject = new JSONObject(webResource.get(String.class));
            logger.info("json keys {}",bigObject.keys());
            logger.info("len {}",bigObject.length());
            logger.info("names {}",bigObject.names());
            JSONArray dataObject = (JSONArray) bigObject.get("data");
            logger.info("0 is {}",dataObject.get(0));
            JSONObject imageInfoArr = (JSONObject)dataObject.get(0);
            JSONObject imageObject = (JSONObject) imageInfoArr.get("images");
            JSONObject standart_image = (JSONObject) imageObject.get("standard_resolution");
            String SIU = standart_image.getString("url");
            return SIU;
    }
//    3255807
//    1338289792
}