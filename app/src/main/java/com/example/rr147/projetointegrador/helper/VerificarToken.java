package com.example.rr147.projetointegrador.helper;

import android.content.Intent;
import android.os.AsyncTask;

import com.example.rr147.projetointegrador.activity.LoginActivity;
import com.example.rr147.projetointegrador.activity.MainActivity;
import com.example.rr147.projetointegrador.database.ConnectAPI;
import com.example.rr147.projetointegrador.domain.Cliente;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class VerificarToken extends AsyncTask<String, Void, Integer> {
    private Cliente cliente;

    public void request(String url, String method, String token) {
        this.execute(url, method, token);
    }

    @Override
    protected Integer doInBackground(String... param) {

        int statusCode = 0;
        HttpURLConnection httpURLConnection = null;

        try {
            URL url = new URL(ConnectAPI.conectarAPI() + param[0]);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod(param[1]);
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.setRequestProperty("Authorization", param[2]);
            httpURLConnection.setDoInput(true);

            statusCode = httpURLConnection.getResponseCode();


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }

        return statusCode;

    }


    @Override
    protected void onPostExecute(Integer code) {

        if (code.equals(200)) {
            Intent i = new Intent(LoginActivity.context, MainActivity.class);
            LoginActivity.context.startActivity(i);

        } else {
            return;
        }

    }
}
