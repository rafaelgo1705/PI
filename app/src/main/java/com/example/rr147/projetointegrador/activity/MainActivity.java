package com.example.rr147.projetointegrador.activity;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.rr147.projetointegrador.fragmentos.CarrinhoFragment;
import com.example.rr147.projetointegrador.fragmentos.ContaFragment;
import com.example.rr147.projetointegrador.R;
import com.example.rr147.projetointegrador.fragmentos.ListaProdutosFragment;
import com.example.rr147.projetointegrador.dao.ProdutoDAO;
import com.example.rr147.projetointegrador.domain.Produto;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private View view;
    private Drawable drawable;

    private TextView email;
    private TextView usuario;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        view = navigationView.getHeaderView(0);

        email = (TextView) view.findViewById(R.id.textViewEmail);
        usuario = (TextView) view.findViewById(R.id.textViewUsuario);
        imageView = (ImageView) view.findViewById(R.id.imageView);

        email.setText("r@gmail.com");
        usuario.setText("Rafael Gomes");

        drawable = getResources().getDrawable(R.drawable.baseline_account_circle_24);
        imageView.setImageDrawable(drawable);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_content,new ListaProdutosFragment()).commit();

        navigationView.setCheckedItem(R.id.nav_produtos);

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
        Fragment fragment = null;

        if (id == R.id.nav_produtos) {
            fragment = new ListaProdutosFragment();

            displaySelectedFragment(fragment);

        } else if (id == R.id.nav_conta) {

            fragment = new ContaFragment();

            displaySelectedFragment(fragment);

        } else if (id == R.id.nav_carrinho) {

            fragment = new CarrinhoFragment();

            displaySelectedFragment(fragment);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void displaySelectedFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_content, fragment);
        fragmentTransaction.commit();
    }
}
