package jv.supermarket.DTOs;

import java.math.BigDecimal;

public class CarrinhoItemDTO {

    private ProdutoDTO produto;

    private int quantidade;

    private BigDecimal precoTotal;

    public CarrinhoItemDTO() {
    }

    public CarrinhoItemDTO(ProdutoDTO produto, int quantidade, BigDecimal precoTotal) {
        this.produto = produto;
        this.quantidade = quantidade;
        this.precoTotal = precoTotal;
    }

    public ProdutoDTO getProduto() {
        return produto;
    }

    public void setProduto(ProdutoDTO produto) {
        this.produto = produto;
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
