package com.example.rr147.projetointegrador.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
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
import com.example.rr147.projetointegrador.domain.Secao;
import com.example.rr147.projetointegrador.helper.ManipularAutenticacao;
import com.example.rr147.projetointegrador.helper.VerificarToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private EditText editTextEmail;
    private EditText editTextSenha;

    private ProgressDialog pd;
    public static Context context;

    private String url = ConnectAPI.conectarAPI();
    private Cliente cliente;
    private RequestQueue queue;
    private JSONObject clienteJson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //verificarAutenticacao();
        this.context = getApplicationContext();

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextSenha = (EditText) findViewById(R.id.editTextSenha);

    }

    public void verificarAutenticacao() {
        VerificarToken verifica = new VerificarToken();
        Secao secao = new Secao(this);

        verifica.request("auth/carrinho", "GET", secao.token());
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
        clienteJson = new JSONObject();
        ManipularAutenticacao request = new ManipularAutenticacao();

        try {
            clienteJson.put("email", cliente.getEmail());
            clienteJson.put("senha", cliente.getSenha());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        request.request("/cliente/autenticar", "POST", clienteJson);
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
