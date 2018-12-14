package com.example.rr147.projetointegrador.database;

public class ConnectAPI {
    private static String url;

    public static final String conectarAPI(){
        url = "http://192.168.0.103:8080";
        return url;
    }
}
