package jv.supermarket.services;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import jv.supermarket.DTOs.CarrinhoDTO;
import jv.supermarket.DTOs.CarrinhoItemDTO;
import jv.supermarket.entities.Carrinho;
import jv.supermarket.entities.CarrinhoItem;
import jv.supermarket.exceptions.ResourceNotFoundException;
import jv.supermarket.repositories.CarrinhoItemRepository;
import jv.supermarket.repositories.CarrinhoRepository;

@Service
public class CarrinhoService {

    @Autowired
    private CarrinhoRepository carrinhoRepo;

    @Autowired
    private CarrinhoItemRepository itemRepo;

    @Autowired
    private ProdutoService produtoService;

    public Carrinho saveCarrinho(Carrinho carrinho) {
        return carrinhoRepo.save(carrinho);
    }

    public Carrinho getCarrinhoById(Long carrinhoId) {
        return carrinhoRepo.findById(carrinhoId)
                .orElseThrow(() -> new ResourceNotFoundException("Carrinho não encontrado com o Id:" + carrinhoId));
    }

    public CarrinhoDTO getCarrinho(Long carrinhoId){
        Carrinho carrinho = carrinhoRepo.findById(carrinhoId).orElseThrow(() -> new ResourceNotFoundException("Carrinho não encontrado com o Id:" + carrinhoId+". O usuário deve ser um Cliente para possuir um carrinho"));
        return convertToDTO(carrinho);
    }

    @Transactional
    public void clearCarrinho(Long carrinhoId){
        Carrinho carrinho = carrinhoRepo.findById(carrinhoId).orElseThrow(() -> new ResourceNotFoundException("Carrinho não encontrado com o Id:" + carrinhoId));
        itemRepo.deleteAllByCarrinhoId(carrinhoId);
        carrinho.limparCarrinho();
    }

    public boolean existsById(Long carrinhoId) {
        return carrinhoRepo.existsById(carrinhoId);
    }

    public CarrinhoItemDTO convertItemToDTO(CarrinhoItem item){
        CarrinhoItemDTO dto = new CarrinhoItemDTO();

        dto.setProduto(produtoService.convertToDTO(item.getProduto()));
        dto.setPrecoTotal(item.getPrecoTotal());
        dto.setQuantidade(item.getQuantidade());

        return dto;
    }

    public CarrinhoDTO convertToDTO(Carrinho carrinho){
        CarrinhoDTO dto = new CarrinhoDTO();
        dto.setPrecoTotal(carrinho.getValorTotal());
        dto.setItems(carrinho.getItens().stream()
                        .map(item -> convertItemToDTO(item))   
                        .collect(Collectors.toSet())
                    );
        return dto;
    }
}