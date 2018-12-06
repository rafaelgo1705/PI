package com.example.rr147.projetointegrador.Fragmentos;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.rr147.projetointegrador.R;
import com.example.rr147.projetointegrador.adapter.ProdutoAdapter;
import com.example.rr147.projetointegrador.domain.Produto;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ListaProdutosFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ListaProdutosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListaProdutosFragment extends Fragment {

    private ListView listViewProdutos;
    private ArrayAdapter<Produto> produtoArrayAdapter;
    private ArrayList<Produto> produtoArrayList;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ListaProdutosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListaProdutosFragment.
     */
    // TODO: Rename and change types and number of parameters
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lista_produtos, container, false);

        listViewProdutos = (ListView)view.findViewById(R.id.listViewProdutos);


        produtoArrayAdapter = new ProdutoAdapter(view.getContext(), produtoArrayList);
        listViewProdutos.setAdapter(produtoArrayAdapter);

        getActivity().setTitle("Produtos");
        return view;
    }

    public void addProdutos(){
        Produto produto1 = new Produto("AK 1001 Bombril Dissipadora cartucho para 50 balas 10 tiros por segundo mata mesmo", 2546.45, 5);
        produtoArrayList.add(produto1);

        Produto produto2 = new Produto("Submetralhadora", 20652.45, 5);
        produtoArrayList.add(produto2);

        Produto produto3 = new Produto("Canhão Full", 220000.00, 2);
        produtoArrayList.add(produto3);
    }



    // TODO: Rename method, update argument and hook method into UI event
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
