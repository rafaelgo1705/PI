package com.example.rr147.projetointegrador.helper;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rr147.projetointegrador.activity.CarrinhoActivity;
import com.example.rr147.projetointegrador.activity.LoginActivity;
import com.example.rr147.projetointegrador.activity.ProdutosActivity;
import com.example.rr147.projetointegrador.database.ConnectAPI;
import com.example.rr147.projetointegrador.domain.ItemCarrinho;
import com.example.rr147.projetointegrador.domain.Produto;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FinalizarCompra extends AsyncTask<String, Void, String> {
    private int codigoStatus = 0;
    private CarrinhoActivity carrinhoActivity = new CarrinhoActivity();
    private List<ItemCarrinho> listItemCarrinho = new ArrayList<>();
    private List<Produto> listProdutos = new ArrayList<>();
    private Context con;
    private TextView t;

    public void request(String url, String method, String token) {
        this.execute(url, method, token);
    }

    @Override
    protected String doInBackground(String... param) {


        String body = null;
        HttpURLConnection httpURLConnection = null;
        StringBuffer response = new StringBuffer();

        try {
            URL url = new URL(ConnectAPI.conectarAPI() + param[0]);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod(param[1]);
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.setRequestProperty("Authorization", param[2]);

            codigoStatus = httpURLConnection.getResponseCode();


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

        return null;
    }


    @Override
    protected void onPostExecute(String carrinho) {
        if (codigoStatus == 200){
            Toast.makeText(LoginActivity.context,"Você realizou suas compras!", Toast.LENGTH_LONG).show();

            Intent i = new Intent(LoginActivity.context, ProdutosActivity.class);
            LoginActivity.context.startActivity(i);
        }else{
            Toast.makeText(LoginActivity.context,"Não foi possível realizar suas compras!",Toast.LENGTH_LONG).show();
        }

    }
}
