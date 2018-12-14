package com.example.rr147.projetointegrador.database;

public class ConnectAPI {
    private static String url;

    public static final String conectarAPI(){
        url = "http://192.168.1.244:8080";
        return url;
    }
}
