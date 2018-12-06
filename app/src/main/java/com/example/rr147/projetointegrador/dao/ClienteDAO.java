package com.example.rr147.projetointegrador.dao;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.rr147.projetointegrador.database.ConnectAPI;
import com.example.rr147.projetointegrador.domain.Cliente;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClienteDAO {
    private String url = ConnectAPI.conectarAPI();
    private RequestQueue queue;
    private final ArrayList<Boolean> verifica = new ArrayList<>();

    public void salvar(final Cliente cliente, final Context context){
        queue = Volley.newRequestQueue(context);

        Map<String, String> postParam= new HashMap<String, String>();
        postParam.put("nome", cliente.getNome());
        postParam.put("email", cliente.getEmail());
        postParam.put("telefone", cliente.getTelefone());
        postParam.put("senha", cliente.getSenha());
        postParam.put("dataNascimento", cliente.getDataNascimento());
        postParam.put("sexo", cliente.getSexo());

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, url+"/cliente/", new JSONObject(postParam),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };
        queue.add(jsonObjReq);
    }

    public void alterar(Cliente cliente){

    }

    public void remover(Cliente cliente){

    }

    public Cliente buscar(){
        Cliente cliente = new Cliente();

        return cliente;
    }
}
