package com.example.rr147.projetointegrador.helper;

import android.os.AsyncTask;

import com.example.rr147.projetointegrador.activity.ProdutosActivity;
import com.example.rr147.projetointegrador.database.ConnectAPI;
import com.example.rr147.projetointegrador.domain.Categoria;
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

public class BuscarCategoria extends AsyncTask<String, Void, String> {
    private int codigoStatus = 0;
    private List<Categoria> listaCategoria = new ArrayList<>();
    private ProdutosActivity listaProdutosFragment = new ProdutosActivity();

    public void request(String url, String method) {
        this.execute(url, method);
    }

    @Override
    protected String doInBackground(String... pos) {
        String body = null;
        HttpURLConnection httpURLConnection = null;
        StringBuffer stringBuffer = new StringBuffer();

        try {
            URL url = new URL(ConnectAPI.conectarAPI() + pos[0]);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod(pos[1]);
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);

            codigoStatus = httpURLConnection.getResponseCode();

            BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                stringBuffer.append(inputLine);
            }

            body = stringBuffer.toString();

            Gson gson = new Gson();
            Categoria[] categorias = gson.fromJson(body, Categoria[].class);

            for (int i = 0; i < categorias.length; i++) {
                listaCategoria.add(categorias[i]);
            }

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
    protected void onPostExecute(String categoria) {

        /*listaProdutosFragment.categoriaArrayList.addAll(listaCategoria);
        listaProdutosFragment.produtoArrayAdapter.notifyDataSetChanged();*/
    }
}
