package com.example.rr147.projetointegrador.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import com.example.rr147.projetointegrador.R;
import com.example.rr147.projetointegrador.domain.Cliente;

public class VerContaClienteActivity extends AppCompatActivity {
    private EditText editTextNome;
    private EditText editTextEmail;
    private EditText editTextTelefone;
    private EditText editTextDataNascimento;
    private RadioButton radioButtonMasculino;
    private RadioButton radioButtonFeminino;

    private Cliente cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_conta_cliente);

        editTextNome = (EditText) findViewById(R.id.editTextNome);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextTelefone = (EditText) findViewById(R.id.editTextTelefone);
        editTextDataNascimento = (EditText) findViewById(R.id.editTextDataNascimento);
        radioButtonMasculino = (RadioButton) findViewById(R.id.radioButtonMasculino);
        radioButtonFeminino = (RadioButton) findViewById(R.id.radioButtonFeminino);
    }

    private void carregarDadosCliente(){
        editTextNome.setText(cliente.getNome());
        editTextEmail.setText(cliente.getEmail());
        editTextTelefone.setText(cliente.getTelefone());
        editTextDataNascimento.setText(cliente.getDataNascimento());
        if(cliente.getSexo().trim().equals("Masculino")){
            radioButtonMasculino.isChecked();
        }else if(cliente.getSexo().trim().equals("Feminino")){
            radioButtonFeminino.isChecked();
        }
    }

    public void apagarConta(View view){

    }
}
