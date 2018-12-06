package com.example.rr147.projetointegrador.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.rr147.projetointegrador.R;
import com.example.rr147.projetointegrador.adapter.ProdutoAdapter;
import com.example.rr147.projetointegrador.dao.ProdutoDAO;
import com.example.rr147.projetointegrador.domain.Produto;

import java.util.ArrayList;
import java.util.List;

public class ProdutosActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private ListView listViewProdutos;
    private DrawerLayout drawer;
    private NavigationView navigationView;

    private ArrayAdapter<Produto> produtoArrayAdapter;
    private ArrayList<Produto> produtoArrayList;

    private List<Produto> produtoList;
    private ProdutoDAO produtoDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produtos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listViewProdutos = (ListView)findViewById(R.id.listViewProdutos);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        produtoArrayList = new ArrayList<>();

        produtoArrayAdapter = new ProdutoAdapter(this, produtoArrayList);
        listViewProdutos.setAdapter(produtoArrayAdapter);

        addProdutos();
    }

    public void addProdutos(){
        Produto produto1 = new Produto("AK 1001 Bombril Dissipadora cartucho para 50 balas 10 tiros por segundo mata mesmo", 2546.45, 5);
        produtoArrayList.add(produto1);

        Produto produto2 = new Produto("Submetralhadora", 20652.45, 5);
        produtoArrayList.add(produto2);

        Produto produto3 = new Produto("Canhão Full", 220000.00, 2);
        produtoArrayList.add(produto3);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.produtos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_conta) {

        } else if (id == R.id.nav_carrinho) {

        } else if (id == R.id.nav_sair) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
