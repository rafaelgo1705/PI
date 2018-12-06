package com.example.rr147.projetointegrador.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;

import com.example.rr147.projetointegrador.dao.ClienteDAO;
import com.example.rr147.projetointegrador.R;
import com.example.rr147.projetointegrador.domain.Cliente;

import java.util.Calendar;

public class CadastroClienteActivity extends AppCompatActivity {
    private EditText editTextNome;
    private EditText editTextEmail;
    private EditText editTextTelefone;
    private EditText editTextDataNascimento;
    private EditText editTextSenha;
    private EditText editTextConfirmarSenha;
    private RadioButton radioButtonMasculino;
    private RadioButton radioButtonFeminino;

    private Cliente cliente;
    private ClienteDAO clienteDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_cliente);

        editTextNome = (EditText) findViewById(R.id.editTextNome);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextTelefone = (EditText) findViewById(R.id.editTextTelefone);
        editTextDataNascimento = (EditText) findViewById(R.id.editTextDataNascimento);
        editTextSenha = (EditText) findViewById(R.id.editTextSenha);
        editTextConfirmarSenha = (EditText) findViewById(R.id.editTextConfirmarSenha);
        radioButtonMasculino = (RadioButton) findViewById(R.id.radioButtonMasculino);
        radioButtonFeminino = (RadioButton) findViewById(R.id.radioButtonFeminino);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Cadastrar");

        editTextDataNascimento.setKeyListener(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_cadastro_cliente, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == android.R.id.home){
            sair();

        }else if(id == R.id.cadastroClienteCadastrar){
            cadastrar();

        }else if(id == R.id.cadastroClienteCancelar){
            sair();

        }

        return super.onOptionsItemSelected(item);
    }

    private void cadastrar(){
        if(validarCampos()){
            cliente = new Cliente();
            cliente.setNome(editTextNome.getText().toString());
            cliente.setEmail(editTextEmail.getText().toString());
            cliente.setTelefone(editTextTelefone.getText().toString());
            cliente.setDataNascimento(editTextDataNascimento.getText().toString());
            cliente.setSenha(editTextSenha.getText().toString());
            if(radioButtonFeminino.isChecked()) {
                cliente.setSexo("Feminino");
            }else if(radioButtonMasculino.isChecked()){
                cliente.setSexo("Masculino");
            }

            clienteDAO = new ClienteDAO();
            clienteDAO.salvar(cliente, this);
            sair();
        }
    }

    @Override
    public void onBackPressed() {
        sair();
        super.onBackPressed();
    }

    private void sair(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private boolean validarCampos(){
        String erro = "";

        if(editTextNome.getText().toString().trim().equals("")){
            erro += "O campo nome está vazio. Preencha-o.\n";

        }
        if(editTextEmail.getText().toString().trim().equals("")){
            erro += "O campo email está vazio. Preencha-o.\n";

        }
        if(editTextTelefone.getText().toString().trim().equals("")){
            erro += "O campo telefone está vazio. Preencha-o.\n";

        }
        if(editTextDataNascimento.getText().toString().trim().equals("")){
            erro += "O campo data de nascimento está vazio. Preencha-o.\n";

        }
        if(editTextSenha.getText().toString().trim().equals("")){
            erro += "O campo senha está vazio. Preencha-o.\n";

        }
        if(editTextConfirmarSenha.getText().toString().trim().equals("")){
            erro += "O campo confirmar senha está vazio. Preencha-o.\n";

        }
        if(!radioButtonMasculino.isChecked() && !radioButtonFeminino.isChecked()) {
            erro += "Por favor, defina um sexo.\n";

        }
        if(!editTextSenha.getText().toString().equals(editTextConfirmarSenha.getText().toString())){
            erro += "As senhas digitadas não conferem.\n";
        }

        if(erro.length() > 0 ){
            AlertDialog.Builder alert = new AlertDialog.Builder(CadastroClienteActivity.this);
            alert.setTitle("Erro");
            alert.setMessage(erro);
            alert.setNeutralButton("OK", null);
            alert.show();
            return false;
        }else{
            return true;
        }
    }

    public void inserirData(View view){
        Calendar calendar = Calendar.getInstance();
        int dia = calendar.get(Calendar.DAY_OF_MONTH);
        int mes = calendar.get(Calendar.MONTH);
        int ano = calendar.get(Calendar.YEAR);

        DatePickerDialog mDatePickerDialog = new DatePickerDialog(CadastroClienteActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                editTextDataNascimento.setText(dayOfMonth+"/"+(month+1)+"/"+year);
            }
        },ano, mes, dia);
        mDatePickerDialog.show();
    }
}
