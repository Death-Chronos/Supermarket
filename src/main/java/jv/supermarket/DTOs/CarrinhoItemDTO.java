package jv.supermarket.DTOs;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class CarrinhoItemDTO {

    private ProdutoDTO produto;

    @JsonIgnore
    private CarrinhoDTO carrinho;

    private int quantidade;

    private BigDecimal precoTotal;

    public CarrinhoItemDTO() {
    }

    public CarrinhoItemDTO(ProdutoDTO produto, CarrinhoDTO carrinho, int quantidade, BigDecimal precoTotal) {
        this.produto = produto;
        this.carrinho = carrinho;
        this.quantidade = quantidade;
        this.precoTotal = precoTotal;
    }

    public ProdutoDTO getProduto() {
        return produto;
    }

    public void setProduto(ProdutoDTO produto) {
        this.produto = produto;
    }

    public CarrinhoDTO getCarrinho() {
        return carrinho;
    }

    public void setCarrinho(CarrinhoDTO carrinho) {
        this.carrinho = carrinho;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getPrecoTotal() {
        return precoTotal;
    }

    public void setPrecoTotal(BigDecimal precoTotal) {
        this.precoTotal = precoTotal;
    }

}
