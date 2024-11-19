package jv.supermarket.services;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import jv.supermarket.DTOs.PedidoDTO;
import jv.supermarket.DTOs.PedidoItemDTO;
import jv.supermarket.entities.Carrinho;
import jv.supermarket.entities.CarrinhoItem;
import jv.supermarket.entities.Pedido;
import jv.supermarket.entities.PedidoItem;
import jv.supermarket.entities.Produto;
import jv.supermarket.entities.enums.PedidoStatus;
import jv.supermarket.exceptions.OutOfStockException;
import jv.supermarket.exceptions.ResourceNotFoundException;
import jv.supermarket.repositories.PedidoItemRepository;
import jv.supermarket.repositories.PedidoRepository;
import jv.supermarket.repositories.ProdutoRepository;

@Service
public class PedidoService {

    @Autowired
    CarrinhoService carrinhoService;

    @Autowired
    PedidoItemRepository itemRepo;

    @Autowired
    PedidoRepository pedidoRepo;

    @Autowired
    UsuarioService userService;

    @Autowired
    ProdutoRepository produtoRepo;

    @Autowired
    ProdutoService produtoService;

    @Transactional
    public Pedido createPedido(Long UserId) {
        Carrinho carrinho = carrinhoService.getById(UserId);
        if (carrinho.getItens().isEmpty()) {
            throw new ResourceNotFoundException(
                    "Carrinho vazio. Adicione itens a ele primeiro antes de tentar realizar um pedido");
        }

        verificarEstoqueCarrinho(carrinho);
        Pedido pedido = new Pedido();

        Set<PedidoItem> itens = carrinhoItemsToPedidoItems(carrinho.getItens());

        for (PedidoItem pedidoItem : itens) {
            pedido.addItem(pedidoItem);
        }
        pedido.setUser(userService.getById(UserId));
        pedido.setStatus(PedidoStatus.ESPERANDO_PAGAMENTO);
        pedido.setData(LocalDateTime.now());

        carrinhoService.limparCarrinho(carrinho.getId());

        return pedidoRepo.save(pedido);

    }

    public PedidoDTO getPedido(Long id) {
        Pedido pedido = getById(id);
        PedidoDTO dto = convertPedidoToDTO(pedido);
        return dto;
    }

    public Set<PedidoDTO> getPedidosByUsuario(Long userId) {
        Set<Pedido> pedidos = pedidoRepo.findByUserId(userId);
        return pedidos.stream()
                .map(pedido -> convertPedidoToDTO(pedido))
                .collect(Collectors.toSet());
    }

    public Pedido getById(Long id) {
        return pedidoRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado com o Id: " + id));
    }

    public Pedido cancelarPedido(Long pedidoId) {
        Pedido pedido = getById(pedidoId);
        pedido.setStatus(PedidoStatus.CANCELADO);
        return pedidoRepo.save(pedido);
    }

    private void verificarEstoqueCarrinho(Carrinho carrinho) {
        for (CarrinhoItem item : carrinho.getItens()) {
            Produto produto = item.getProduto();
            int estoqueDisponivel = produto.getEstoque();
            if (!isEstoqueSuficiente(item.getQuantidade(), estoqueDisponivel)) {
                int quantidadeExcedente = item.getQuantidade() - estoqueDisponivel;
                throw new OutOfStockException(
                        "O pedido do produto " + produto.getNome() + " da marca: " + produto.getMarca()
                                + " excede em " + quantidadeExcedente
                                + " o estoque disponível, por favor, diminua a quantidade para finalizar a compra");
            }
        }

    }

    private Set<PedidoItem> carrinhoItemsToPedidoItems(Set<CarrinhoItem> CarItens) {
        Set<PedidoItem> itens = new HashSet<PedidoItem>();

        for (CarrinhoItem carItem : CarItens) {
            PedidoItem item = new PedidoItem();
            Produto produto = carItem.getProduto();
            item.setProduto(produto);
            item.setQuantidade(carItem.getQuantidade());

            produto.setEstoque(produto.getEstoque() - item.getQuantidade());

            produtoRepo.save(produto);

            itens.add(item);
        }

        return itens;
    }

    private boolean isEstoqueSuficiente(int quantidade, int produtoEstoque) {
        return quantidade <= produtoEstoque;
    }

    private PedidoItemDTO convertItemToDTO(PedidoItem item) {
        PedidoItemDTO dto = new PedidoItemDTO();

        dto.setProduto(produtoService.converterParaDTO(item.getProduto()));
        dto.setQuantidade(item.getQuantidade());
        dto.setPrecoTotal(item.getPrecoTotal());

        return dto;
    }

    private PedidoDTO convertPedidoToDTO(Pedido pedido) {
        PedidoDTO dto = new PedidoDTO();

        dto.setData(pedido.getData());
        dto.setStatus(pedido.getStatus());
        dto.setPrecoTotal(pedido.getPrecoTotal());
        dto.setItems(pedido.getItens().stream()
                .map(item -> convertItemToDTO(item))
                .collect(Collectors.toSet()));

        return dto;
    }
}
