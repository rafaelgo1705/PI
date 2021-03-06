package com.example.rr147.projetointegrador.helper;

import android.os.AsyncTask;
import android.util.Log;

import com.example.rr147.projetointegrador.activity.ProdutosActivity;
import com.example.rr147.projetointegrador.adapter.ProdutoAdapter;
import com.example.rr147.projetointegrador.database.ConnectAPI;
import com.example.rr147.projetointegrador.domain.Produto;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

public class BuscarProdutos extends AsyncTask<String, Void, String> {
    private int codigoStatus = 0;
    private ProdutosActivity produtosActivity = new ProdutosActivity();
    private ArrayList<Produto> listaProdutos = new ArrayList<Produto>();

    public void request(String url, String method) {
        this.execute(url, method);
    }

    @Override
    protected String doInBackground(String... param) {
        String body = null;
        HttpURLConnection httpURLConnection = null;
        StringBuffer stringBuffer = new StringBuffer();

        try {
            URL url = new URL(ConnectAPI.conectarAPI() + param[0]);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod(param[1]);
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.setDoInput(true);

            codigoStatus = httpURLConnection.getResponseCode();

            BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                stringBuffer.append(inputLine);
            }

            body = stringBuffer.toString();

            Gson gson = new Gson();
            Produto[] produtos = gson.fromJson(body, Produto[].class);

            for (int i = 0; i < produtos.length; i++) {
                listaProdutos.add(produtos[i]);
            }
            Log.i("produtos", String.valueOf(listaProdutos.size()));

            DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
            wr.writeBytes(param[2]);
            wr.flush();
            wr.close();

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

        return body;
    }

    @Override
    protected void onPostExecute(String produtos) {
        if (codigoStatus == 200) {
            produtosActivity.listProdutos.addAll(listaProdutos);

            ProdutoAdapter adapter = new ProdutoAdapter(produtosActivity.context, listaProdutos);
            produtosActivity.listViewProdutos.setAdapter(adapter);
        }
    }
}
