package jv.supermarket.DTOs;

import java.math.BigDecimal;

public class ProdutoDTO {
    private String nome;
    private String marca;
    private BigDecimal preco;
    private String descricao;

    public ProdutoDTO() {
    }

    public ProdutoDTO(String nome, String marca, BigDecimal preco, String descricao) {
        this.nome = nome;
        this.marca = marca;
        this.preco = preco;
        this.descricao = descricao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    
    
    
}
