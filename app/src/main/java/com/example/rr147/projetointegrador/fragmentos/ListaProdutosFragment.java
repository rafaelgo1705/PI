package com.example.rr147.projetointegrador.fragmentos;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.rr147.projetointegrador.R;
import com.example.rr147.projetointegrador.adapter.ProdutoAdapter;
import com.example.rr147.projetointegrador.dao.ProdutoDAO;
import com.example.rr147.projetointegrador.database.ConnectAPI;
import com.example.rr147.projetointegrador.domain.Produto;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListaProdutosFragment extends Fragment {

    private ListView listViewProdutos;
    private ArrayAdapter<Produto> produtoArrayAdapter;
    private ArrayList<Produto> produtoArrayList;

    private ProdutoDAO produtoDAO;
    private Produto produto;
    private String url = ConnectAPI.conectarAPI();

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private ProgressDialog pd;
    private RequestQueue mRequestQueue;

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
        mRequestQueue =  Volley.newRequestQueue(getContext());
        pd = ProgressDialog.show(getContext(),"Aguarde...","Aguarde...");
        try{
            Thread.sleep(2000);
        }catch(Exception e){

        }
        JsonObjectRequest jr = new JsonObjectRequest(Request.Method.GET,url+"/produto/listar/",null,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //Log.i(TAG,response.toString());
                Log.i("erroResponse", "Sucesso: "+response.toString());
                //parseJSON(response);
                produtoArrayAdapter.notifyDataSetChanged();
                pd.dismiss();
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Log.i(TAG,error.getMessage());
                Log.i("erroResponse", "Erro: "+error.toString());
                pd.dismiss();
            }
        });
        mRequestQueue.add(jr);

        /*Produto produto1 = new Produto("AK 1001 Bombril Dissipadora cartucho para 50 balas 10 tiros por segundo mata mesmo", 2546.45, 5);
        produtoArrayList.add(produto1);

        Produto produto2 = new Produto("Submetralhadora", 20652.45, 5);
        produtoArrayList.add(produto2);

        Produto produto3 = new Produto("Canhão Full", 220000.00, 2);
        produtoArrayList.add(produto3);*/
    }

    private void parseJSON(JSONObject json){
        try{
            JSONObject value = json.getJSONObject("value");
            JSONArray items = value.getJSONArray("items");
            for(int i=0;i<items.length();i++){

                JSONObject item = items.getJSONObject(i);
                produto = new Produto();
                produto.setNome(item.optString("nome"));
                produto.setPreco(item.getDouble("preco"));
                Log.i("verArray", String.valueOf(produto));
                produtoArrayList.add(produto);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }


    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
