package com.example.rr147.projetointegrador.domain;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Secao {
    private SharedPreferences prefs;

    public Secao(Context cntx) {
        // TODO Auto-generated constructor stub
        prefs = PreferenceManager.getDefaultSharedPreferences(cntx);
    }

    public void setToken(String token) {
        prefs.edit().putString("token", token).commit();
    }

    public String token() {
        String token = prefs.getString("token", "");
        return token;
    }
}
