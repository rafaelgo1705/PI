package com.example.rr147.projetointegrador.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rr147.projetointegrador.R;
import com.example.rr147.projetointegrador.domain.Produto;

import java.util.ArrayList;

public class ProdutoAdapter extends ArrayAdapter<Produto> {
    private ArrayList<Produto> produtoAdapter;
    private Context context;

    public ProdutoAdapter(Context c, ArrayList<Produto> objects) {
        super(c, 0, objects);

        this.context = c;
        this.produtoAdapter = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = null;

        if (produtoAdapter != null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            view = inflater.inflate(R.layout.layout_lista_produto, parent, false);

            ImageView imageView = (ImageView)view.findViewById(R.id.imageViewFoto);
            TextView textViewDescricao = (TextView) view.findViewById(R.id.textViewDescricao);
            TextView textViewPreco = (TextView) view.findViewById(R.id.textViewPreco);

            textViewDescricao.setMaxLines(3);

            Produto produto = produtoAdapter.get(position);

            textViewDescricao.setText(produto.getNome());
            textViewPreco.setText(String.valueOf(produto.getPreco()));
        }
        return view;
    }
}
