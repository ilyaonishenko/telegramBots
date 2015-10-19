package vkMessBot.controllers;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by woqpw on 17.10.15.
 */
@Path("/redirection")
public class RedirectOne {
    /*public Response redirectFirstTime(){
        String output = "authorize in vk:  https://oauth.vk.com/authorize?display=page&client_id=5102517&scope= friends,photos,audio,video,messages&redirect_uri=https://oauth.vk.com/blank.html&response_type=code&v=5.37";
        return Response.status(200).entity(output).build();
    }*/
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String sayPlainTextHello() {
        return "Hello Jersey";
    }
}