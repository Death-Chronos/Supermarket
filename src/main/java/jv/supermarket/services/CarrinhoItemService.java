package jv.supermarket.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import jv.supermarket.entities.Carrinho;
import jv.supermarket.entities.CarrinhoItem;
import jv.supermarket.entities.Produto;
import jv.supermarket.exceptions.ResourceNotFoundException;
import jv.supermarket.repositories.CarrinhoItemRepository;

@Service
public class CarrinhoItemService {

    @Autowired
    CarrinhoItemRepository carrinhoItemRepository;

    @Autowired
    ProdutoService produtoService;

    @Autowired
    CarrinhoService carrinhoService;

    @Transactional
    public void adicionarItemNoCarrinho(Long produtoId, int quantity, Long carrinhoId) {
        Produto produto = produtoService.getProdutoById(produtoId);

        if (carrinhoService.existById(carrinhoId)) {
            Carrinho carrinho = carrinhoService.getById(carrinhoId);

            CarrinhoItem carrinhoItem = carrinho.getItems().stream()
                    .filter(item -> item.getProduto().getId().equals(produtoId))
                    .findFirst().orElse(new CarrinhoItem());

            if (carrinhoItem.getId() == null) {
                carrinhoItem.setCarrinho(carrinho);
                carrinhoItem.setProduto(produto);
                carrinhoItem.setQuantidade(quantity);

                carrinhoItem.setCarrinho(carrinho);

                carrinhoItemRepository.save(carrinhoItem);

                carrinho.adicionarItem(carrinhoItem);

                carrinhoService.saveCarrinho(carrinho);
            } else {
                carrinhoItem.setQuantidade(carrinhoItem.getQuantidade() + quantity);
                carrinhoItemRepository.save(carrinhoItem);
            }

        } else {
            throw new ResourceNotFoundException("Carrinho não encontrado com o id: " + carrinhoId);
        }

    }

    @Transactional
    public void removerItemDoCarrinho(Long carrinhoId, Long produtoId) {
        CarrinhoItem item = getCarrinhoItem(carrinhoId, produtoId);
        Carrinho carrinho = carrinhoService.getById(carrinhoId);

        carrinho.removerItem(item);

        carrinhoService.saveCarrinho(carrinho);

    }

    @Transactional
    public void updateItemQuantidade(Long carrinhoId, Long produtoId, int quantidade) {
            CarrinhoItem item = getCarrinhoItem(carrinhoId, produtoId);
            item.setQuantidade(quantidade);
            carrinhoItemRepository.save(item);

    }

    private CarrinhoItem getCarrinhoItem(Long carrinhoId, Long produtoId) {
        Carrinho carrinho = carrinhoService.getById(carrinhoId);

        return carrinho.getItems().stream().filter(itens -> itens.getProduto().getId().equals(produtoId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Não existe nenhum produto com Id: " + produtoId + " No carrinho com Id: " + carrinhoId));
    }

    

}
