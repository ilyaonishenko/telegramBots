package me.vkServMessBot.controllers;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * Created by woqpw on 19.10.15.
 */
@Path("/redir")
public class RedirectOne {
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String sayPlainTextHello() {
        return "Hello World RESTful Jersey!";
    }

    /*@GET
    @Produces(MediaType.TEXT_XML)
    public String sayXMLHello() {
        return "<?xml version=\"1.0\"?>" + "<hello> Hello World RESTful Jersey"
                + "</hello>";
    }*/

    @GET
    @Produces(MediaType.TEXT_HTML)
    public String sayHtmlHello() {
        System.out.println("here");
        String redirLink = "https://oauth.vk.com/authorize?display=page&client_id=5102517&scope=friends,photos,audio,video,messages&redirect_uri=http://localhost:8080/vkmesserv/redirect/redir/getcode&response_type=code&v=5.37";
//        String redirLink = "https://oauth.vk.com/blank.html";
        return "<html> " + "<title>" + "Hello World RESTful Jersey"
                + "</title>" + "<body><a link href="+redirLink+">" + "go to vk auth"
                + "</a></body>" + "</html> ";
    }
    @Path("/getcode")
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String getCode(@QueryParam("code") String code){
        System.out.println(code);
        return "<html><body><h1>"+code+"</h1></body></html>";
    }
}
