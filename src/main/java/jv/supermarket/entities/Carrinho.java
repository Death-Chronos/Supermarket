package jv.supermarket.entities;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Carrinho {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

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


    public void limparCarrinho(){
        this.items.clear();
    }


    
}
