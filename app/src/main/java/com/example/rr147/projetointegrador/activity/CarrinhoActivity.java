package com.example.rr147.projetointegrador.activity;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.rr147.projetointegrador.R;
import com.example.rr147.projetointegrador.domain.ItemCarrinho;
import com.example.rr147.projetointegrador.domain.Produto;
import com.example.rr147.projetointegrador.domain.Secao;
import com.example.rr147.projetointegrador.helper.FinalizarCompra;
import com.example.rr147.projetointegrador.helper.MostrarCarrinho;

import java.util.ArrayList;
import java.util.List;

public class CarrinhoActivity extends AppCompatActivity {
    private MostrarCarrinho mostrarCarrinho;
    private Secao secao;
    private TextView valorCarrinho;
    public static List<Produto> listProdutos = new ArrayList<>();
    public static ArrayAdapter<Produto> adapter;
    public static List<ItemCarrinho> listItemCarrinho = new ArrayList<>();
    public static ListView listView;
    public static Context context;
    public static Activity activity;
    private Button buttonComprar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrinho);

        secao = new Secao(this);
        activity = this;

        listView = (ListView) findViewById(R.id.listaCarrinho);
        buttonComprar = (Button) findViewById(R.id.buttonComprar);

        buttonComprar.setText("Valor Total: " + ProdutosActivity.textView.getText());

        mostrarCarrinho = new MostrarCarrinho(this, valorCarrinho);

        mostrarCarrinho.request("auth/carrinho", "GET", secao.token());

    }

    public void comprarTudo(View view) {
        FinalizarCompra finalizarCompra = new FinalizarCompra();
        finalizarCompra.request("auth/compra", "POST", secao.token());
    }
}
