package com.example.rr147.projetointegrador.helper;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;

import com.example.rr147.projetointegrador.activity.CarrinhoActivity;
import com.example.rr147.projetointegrador.adapter.CarrinhoAdapter;
import com.example.rr147.projetointegrador.database.ConnectAPI;
import com.example.rr147.projetointegrador.domain.Carrinho;
import com.example.rr147.projetointegrador.domain.ItemCarrinho;
import com.example.rr147.projetointegrador.domain.Produto;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MostrarCarrinho extends AsyncTask<String, Void, String> {
    private int codigoStatus = 0;
    private CarrinhoActivity carrinhoActivity = new CarrinhoActivity();
    private List<ItemCarrinho> listItemCarrinho = new ArrayList<>();
    private List<Produto> listProdutos = new ArrayList<>();
    private Context context;
    private TextView textView;


    public MostrarCarrinho(Context context, TextView textView) {
        this.context = context;
        this.textView = textView;
    }


    public void request(String url, String method, String token) {
        this.execute(url, method, token);
    }


    @Override
    protected String doInBackground(String... param) {


        String res_body = null;
        HttpURLConnection httpURLConnection = null;
        StringBuffer response = new StringBuffer();

        try {
            URL url = new URL(ConnectAPI.conectarAPI() + param[0]);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod(param[1]);
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.setRequestProperty("Authorization", param[2]);
            httpURLConnection.setDoInput(true);

            codigoStatus = httpURLConnection.getResponseCode();


            BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            res_body = response.toString();

            Gson gson = new Gson();
            Carrinho carrinho = gson.fromJson(res_body, Carrinho.class);

            listItemCarrinho = carrinho.getItensCarrinho();


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
        carrinhoActivity.listItemCarrinho.addAll(listItemCarrinho);

        for (int i = 0; i<listItemCarrinho.size(); i++){
            listProdutos.add(listItemCarrinho.get(i).getProduto());
        }

        CarrinhoAdapter adapter = new CarrinhoAdapter(context, listItemCarrinho);
        carrinhoActivity.listView.setAdapter(adapter);

    }
}
