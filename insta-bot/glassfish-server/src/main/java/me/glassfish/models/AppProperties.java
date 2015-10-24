package me.glassfish.models;

/**
 * Created by woqpw on 24.10.15.
 */
public class AppProperties {
    private static AppProperties appProperties;
    private static final String client_id = "client_id=eddc8161a72f4c8bb37a80bae0c6c3f4";
    private static final String client_secret = "client_secret=0df8b6a629a84427a100b6234af16b53";
    private static final String grant_type = "grant_type=authorization_code";
    private static final String redirect_uri = "redirect_uri=http://localhost:8080/glassfish/gf/redirect/code";
    private static final String response_type= "response_type=code";

    private AppProperties(){}

    public static AppProperties getAppProperties(){
        if(appProperties==null)
            appProperties = new AppProperties();
        return  appProperties;
    }


    public static String getClient_id() {
        return client_id;
    }

    public static String getClient_secret() {
        return client_secret;
    }

    public static String getGrant_type() {
        return grant_type;
    }

    public static String getRedirect_uri() {
        return redirect_uri;
    }

    public static String getResponse_type(){
        return response_type;
    }
}
