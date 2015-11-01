package me.glassfish.controllers;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import me.glassfish.DAOs.SubscriptionDAO;
import me.glassfish.models.*;
import me.glassfish.properties.AppProperties;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by woqpw on 21.10.15.
 */
@Path("/redirect")
public class Application {
    @Inject
    private UserController userController;

    @Inject
    private SubscriptionController subscriptionController;

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
        userController.addUser(user);
//  TODO        Нужно придумать схему красивого создания подписки с сохранением подписки и подписчика
        Subscription subscription1 = new Subscription("pavelvelikov",1338289792,1338289792);
        Subscription subscription2 = new Subscription("caradelevingne",3255807,1338289792);
        subscriptionController.addSubscription(subscription1);
        subscriptionController.addSubscription(subscription2);
        return newObject;
    }

    @GET
    @Path("/getLastUpdate")
    @Produces(MediaType.TEXT_PLAIN)
    public String getLastUpdate(@QueryParam("username")String targetName,@QueryParam("clientname")String clientName){
        String endpoint = "https://api.instagram.com/v1/users/";
        String answer;
        /*subscriptions.put("pavelvelikov",1338289792);
        subscriptions.put("caradelevingne",3255807);
        users.put(user.getUsername(),user);*/
        logger.info("username is {}",targetName);//targetName
        logger.info("clientname is {}",clientName);//clientName
//        logger.info("check is user here {}",userController.checkUserByUsername(clientname));
//        int answer = userController.getIdByUsername(clientname);

        if (!userController.checkUserByUsername(clientName)) {
            answer = "no such user in our list";
            logger.info("error in checking clientByUsername");
            return answer;
        }
        int c_id = userController.getIdByUsername(clientName);
        if(!subscriptionController.checkSubscriptionBytNameAndcId(targetName,c_id)){
            answer = "you can't get this user updates";
            logger.info("error checkink clientId in Subscription");
            return answer;
        }
        String token = userController.getAccessTokenBycId(c_id);
        int t_id = subscriptionController.getTargetIdBytUsername(targetName);
        String link = endpoint+t_id+"/media/recent"+"?access_token="+token;
        webResource = client.resource(link);
        JSONObject bigObject = new JSONObject(webResource.get(String.class));
        logger.info("json keys {}",bigObject.keys());
        logger.info("len {}",bigObject.length());
        logger.info("names {}",bigObject.names());
        JSONArray dataObject = (JSONArray) bigObject.get("data");
        logger.info("0 is {}",dataObject.get(0));
        JSONObject imageInfoArr = (JSONObject)dataObject.get(0);
        String created_time = (String) imageInfoArr.get("created_time");
        logger.info("created time {}",created_time);
        JSONObject imageObject = (JSONObject) imageInfoArr.get("images");
        JSONObject standart_image = (JSONObject) imageObject.get("standard_resolution");
        String SIU = standart_image.getString("url");
        return SIU;
    }
//    3255807
//    1338289792
}