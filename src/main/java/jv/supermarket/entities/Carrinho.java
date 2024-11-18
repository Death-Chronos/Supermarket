package jv.supermarket.entities;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Carrinho {

    @Id
    private Long id;

    @JsonIgnore
    @OneToOne
    @MapsId
    @JoinColumn(name="user_id")
    private Usuario user;

    @OneToMany(mappedBy = "carrinho", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CarrinhoItem> items = new HashSet<CarrinhoItem>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<CarrinhoItem> getItems() {
        return items;
    }

    public void adicionarItem(CarrinhoItem item) {
        items.add(item);
        item.setCarrinho(this);
    }

    public void removerItem(CarrinhoItem item) {
        items.remove(item);
        item.setCarrinho(null);
    }

    public BigDecimal getValorTotal() {
        return this.items.stream().map(CarrinhoItem::getPrecoTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    public void limparCarrinho(){
        this.items.clear();
    }


    
}
