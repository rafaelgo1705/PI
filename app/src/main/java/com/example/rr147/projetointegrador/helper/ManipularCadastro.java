package com.example.rr147.projetointegrador.helper;

import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.rr147.projetointegrador.activity.CadastroClienteActivity;
import com.example.rr147.projetointegrador.activity.LoginActivity;
import com.example.rr147.projetointegrador.database.ConnectAPI;
import com.example.rr147.projetointegrador.domain.Cliente;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class ManipularCadastro extends AsyncTask<String, Void, Integer> {
    private Cliente cliente;


    public void request(String url, String method, JSONObject json) {
        this.execute(url, method, json.toString());
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
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);


            DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
            wr.writeBytes(param[2]);
            wr.flush();
            wr.close();

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
        if (code.equals(201)) {
            Toast.makeText(CadastroClienteActivity.context, "Usuário cadastrado!", Toast.LENGTH_SHORT).show();

            Intent i = new Intent(CadastroClienteActivity.context, LoginActivity.class);
            CadastroClienteActivity.context.startActivity(i);
        } else {
            Toast.makeText(CadastroClienteActivity.context, "Verifique suas credenciais!", Toast.LENGTH_LONG).show();
        }
    }
}
