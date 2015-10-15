package me.vkMessBot.model;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by woqpw on 16.10.15.
 */
@Path("/")
public class Code {
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getCode(String code){
        return code;
    }
}
