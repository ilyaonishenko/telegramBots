package me.vkMessBot.properties;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by woqpw on 11.10.15.
 */
public class BotProperties {
    public final static String token = "125892015:AAH0274n9NvFfzfFokHQ_xiV8OXIubyDvlo";
    public final static String endpoint = "https://api.telegram.org/bot";

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String accessToken;

    public String code;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getCodeFromURL(String code){
        this.code = code;
        return code;
    }
    public void setCode(String code){
        this.code = code;
    }

}
