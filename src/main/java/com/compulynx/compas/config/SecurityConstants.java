package com.compulynx.compas.config;


import com.compulynx.compas.SpringApplicationContext;

public class  SecurityConstants {

    public static final long EXPIRATION_TIME = 864000000;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "jwtAuthorization";

    public static String getTokenSecret(){
        AppProperties appProperties = (AppProperties) SpringApplicationContext.getBean("appProperties");
//        return appProperties.getTokenSecret();
        return "secretToken";
    }
}
