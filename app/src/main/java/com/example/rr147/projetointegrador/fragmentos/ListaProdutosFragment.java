package com.example.rr147.projetointegrador.fragmentos;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.rr147.projetointegrador.R;
import com.example.rr147.projetointegrador.activity.MainActivity;
import com.example.rr147.projetointegrador.adapter.ProdutoAdapter;
import com.example.rr147.projetointegrador.dao.ProdutoDAO;
import com.example.rr147.projetointegrador.database.ConnectAPI;
import com.example.rr147.projetointegrador.domain.Categoria;
import com.example.rr147.projetointegrador.domain.Produto;
import com.example.rr147.projetointegrador.domain.Secao;
import com.example.rr147.projetointegrador.helper.BuscarCategoria;
import com.example.rr147.projetointegrador.helper.BuscarProdutos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListaProdutosFragment extends Fragment {

    public ListView listViewProdutos;
    public ArrayAdapter<Produto> produtoArrayAdapter;
    public ArrayList<Produto> produtoArrayList;

    public static Context context;

    private Produto produto;
    private BuscarProdutos buscarProdutos;
    private Secao secao;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private ProgressDialog pd;

    private OnFragmentInteractionListener mListener;

    public ListaProdutosFragment() {
    }

    public static ListaProdutosFragment newInstance(String param1, String param2) {
        ListaProdutosFragment fragment = new ListaProdutosFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        secao = new Secao(getContext());
        produtoArrayList = new ArrayList<>();

        addProdutos();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lista_produtos, container, false);

        listViewProdutos = (ListView)view.findViewById(R.id.listViewProdutos);

        produtoArrayAdapter = new ProdutoAdapter(view.getContext(), produtoArrayList);
        listViewProdutos.setAdapter(produtoArrayAdapter);

        getActivity().setTitle("Produtos");
        return view;
    }

    public void addProdutos(){

    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        BuscarProdutos buscarProdutos = new BuscarProdutos();
        buscarProdutos.request("/produto/listar", "GET");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
