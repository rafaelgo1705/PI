package com.example.rr147.projetointegrador.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rr147.projetointegrador.R;
import com.example.rr147.projetointegrador.domain.ItemCarrinho;

import java.util.List;

public class CarrinhoAdapter extends ArrayAdapter<ItemCarrinho> {
    private List<ItemCarrinho> carrinhoArrayList;
    private Context context;

    public CarrinhoAdapter(Context c, List<ItemCarrinho> objects) {
        super(c, 0, objects);

        this.context = c;
        this.carrinhoArrayList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = null;

        if (carrinhoArrayList != null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            view = inflater.inflate(R.layout.layout_lista_produto, parent, false);

            ImageView imageView = (ImageView)view.findViewById(R.id.imageViewFoto);
            TextView textViewDescricao = (TextView) view.findViewById(R.id.textViewDescricao);
            TextView textViewPreco = (TextView) view.findViewById(R.id.textViewPreco);

            textViewDescricao.setMaxLines(3);

            ItemCarrinho carrinho = carrinhoArrayList.get(position);

            /*textViewDescricao.setText(carrinho.getCliente());
            textViewPreco.setText(String.valueOf(produto.getPreco()));*/
        }
        return view;
    }
}
