package com.example.rr147.projetointegrador.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rr147.projetointegrador.R;
import com.example.rr147.projetointegrador.domain.Categoria;
import com.example.rr147.projetointegrador.domain.ItemCarrinho;
import com.example.rr147.projetointegrador.domain.Produto;
import com.example.rr147.projetointegrador.domain.Secao;
import com.example.rr147.projetointegrador.helper.BuscarCarrinho;
import com.example.rr147.projetointegrador.helper.BuscarCategoria;
import com.example.rr147.projetointegrador.helper.BuscarProdutos;

import java.util.ArrayList;
import java.util.List;

public class ProdutosActivity extends AppCompatActivity {
    private Spinner spinner;
    public static TextView textView;

    private BuscarCarrinho buscaCarrinho;
    private Secao secao;
    public static List<Categoria> listCategoria = new ArrayList<>();
    public static List<Produto> listProdutos = new ArrayList<>();
    public static ArrayAdapter<Categoria> adapter;
    public static List<ItemCarrinho> listItemCarrinho = new ArrayList<>();
    public static ListView listViewProdutos;
    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produtos);


        secao = new Secao(this);
        LoginActivity.activity.finish();
        if (CarrinhoActivity.activity != null){
            CarrinhoActivity.activity.finish();
        }

        this.context = getApplicationContext();

        textView = (TextView) findViewById(R.id.textViewValor);

        buscaCarrinho = new BuscarCarrinho(this, textView);

        listViewProdutos = (ListView) findViewById(R.id.listaProdutos);
        spinner = findViewById(R.id.spinner);


        adapter = new ArrayAdapter<Categoria>(this, android.R.layout.simple_spinner_item, listCategoria);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Categoria categoria = (Categoria) parent.getSelectedItem();
                displaySelectedCategory(categoria);

                BuscarProdutos busca_produtos = new BuscarProdutos();
                busca_produtos.request("categoria/" + categoria.getId(), "GET");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        listViewProdutos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Produto prod = (Produto) parent.getItemAtPosition(position);
                ItemCarrinho item = new ItemCarrinho();
                for (int i = 0; i < listItemCarrinho.size(); i++) {
                    if (listItemCarrinho.get(i).getProduto().getIdProduto() == prod.getIdProduto()) {
                        item = listItemCarrinho.get(i);
                        break;
                    }
                }
            }
        });

        upDate_view();
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (spinner.getSelectedItem() == null) {
            BuscarCategoria buscarCategoria = new BuscarCategoria();
            buscarCategoria.request("categoriainfo", "GET");
        }
    }

    public void displaySelectedCategory(Categoria categoria) {

        int id = categoria.getId();
        String nome = categoria.getNome();

        String cat_info = "ID: " + id + "\n" + "Nome: " + nome;

        Toast.makeText(this, cat_info, Toast.LENGTH_LONG).show();


    }

    public void upDate_view() {
        buscaCarrinho.request("auth/carrinho", "GET", secao.token());
    }
}
