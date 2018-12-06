package com.example.rr147.projetointegrador.domain;

import java.util.Date;
import java.util.List;

public class Venda {
    private int id;
    private List<Produto> produtos;
    private Cliente cliente;
    private Date data;
    private double valor;
}
