package com.example.rr147.projetointegrador.helper;

import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.rr147.projetointegrador.activity.LoginActivity;
import com.example.rr147.projetointegrador.activity.MainActivity;
import com.example.rr147.projetointegrador.database.ConnectAPI;
import com.example.rr147.projetointegrador.domain.Cliente;
import com.example.rr147.projetointegrador.domain.Secao;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class ManipularAutenticacao extends AsyncTask<String, Void, String> {
    private Cliente cliente;
    int codigoStatus = 0;


    public void request(String url, String method, JSONObject json) {
        this.execute(url, method, json.toString());
    }


    @Override
    protected String doInBackground(String... param) {
        String auth = null;
        HttpURLConnection httpURLConnection = null;

        try {
            URL url = new URL(ConnectAPI.conectarAPI() + param[0]);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod(param[1]);
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);


            DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
            wr.writeBytes(param[2]);
            wr.flush();
            wr.close();

            codigoStatus = httpURLConnection.getResponseCode();
            auth = httpURLConnection.getHeaderField("Authorization");


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

        return auth;

    }

    @Override
    protected void onPostExecute(String token) {
        if (codigoStatus == 200) {

            Secao secao = new Secao(LoginActivity.context);
            secao.setToken(token);

            Intent i = new Intent(LoginActivity.context, MainActivity.class);
            LoginActivity.context.startActivity(i);
        } else {
            Toast.makeText(LoginActivity.context, "Cliente não encontrado!", Toast.LENGTH_LONG).show();
        }

    }
}