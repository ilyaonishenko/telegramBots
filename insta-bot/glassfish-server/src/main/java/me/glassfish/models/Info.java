package me.glassfish.models;

/**
 * Created by woqpw on 24.10.15.
 */
public class Info {
    String client_id;
    String client_secret;
    String redirect_uri;
    String code1;
    String grant_type = "authorization_code";
    public Info(){}
    public Info(String client_id,String client_secret,String redirect_uri,String code1){
        this.client_id = client_id;
        this.client_secret = client_secret;
        this.redirect_uri = redirect_uri;
        this.code1 = code1;
    }
    @Override
    public String toString(){
        return new StringBuffer("client_id = ").append(this.client_id)
                .append("client_secret = ").append(this.client_secret)
                .append("grant_type = ").append(this.grant_type)
                .append("redirect)uri = ").append(this.redirect_uri)
                .append("code = ").append(code1).toString();
    }

}
