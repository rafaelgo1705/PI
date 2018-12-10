package com.example.rr147.projetointegrador.activity;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

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
import com.example.rr147.projetointegrador.R;
import com.example.rr147.projetointegrador.database.ConnectAPI;
import com.example.rr147.projetointegrador.domain.Cliente;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private EditText editTextEmail;
    private EditText editTextSenha;

    private String url = ConnectAPI.conectarAPI();
    private Cliente cliente;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextSenha = (EditText) findViewById(R.id.editTextSenha);

    }

    public void logar(View view){
        if(validarCampos()) {
            cliente = new Cliente();
            cliente.setEmail(editTextEmail.getText().toString());
            cliente.setSenha(editTextSenha.getText().toString());

            autenticar(cliente);
        }
    }

    public void autenticar(final Cliente cliente){
        Map<String, String> postParam = new HashMap<String, String>();
        postParam.put("email", cliente.getEmail());
        postParam.put("senha", cliente.getSenha());

        queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, url+"/cliente/autenticar/", new JSONObject(postParam),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        abrirTelaInicial(cliente.getEmail());
                        try {
                            Log.i("token", "Sucesso: "+String.valueOf(response.getJSONObject("Authorization")));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("token", "Erro: "+String.valueOf(error));
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                try {
                    String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));

                    JSONObject result = null;

                    if (jsonString != null && jsonString.length() > 0)
                        result = new JSONObject(jsonString);

                    return Response.success(result, HttpHeaderParser.parseCacheHeaders(response));

                } catch (UnsupportedEncodingException e) {
                    return Response.error(new ParseError(e));

                } catch (JSONException je) {
                    return Response.error(new ParseError(je));
                }
            }
        };
        queue.add(jsonObjReq);
    }

    private void abrirTelaInicial(String email){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("email", email);
        startActivity(intent);
        finish();
    }

    public void cadastrar(View view){
        Intent intent = new Intent(this, CadastroClienteActivity.class);
        startActivity(intent);
        finish();
    }

    private boolean validarCampos(){
        String erro = "";

        if(editTextEmail.getText().toString().trim().equals("")){
            erro += "O campo email está vazio!\n";

        }
        if(editTextSenha.getText().toString().trim().equals("")){
            erro += "O campo senha está vazio!\n";
        }

        if(erro.length() > 0){
            AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this);
            alert.setTitle("Erro");
            alert.setMessage(erro);
            alert.setNeutralButton("OK", null);
            alert.show();
            return false;

        }else{
            return true;
        }
    }
}
