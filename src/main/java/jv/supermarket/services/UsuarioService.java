package jv.supermarket.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import jv.supermarket.entities.Carrinho;
import jv.supermarket.entities.Usuario;
import jv.supermarket.exceptions.AlreadyExistException;
import jv.supermarket.exceptions.ResourceNotFoundException;
import jv.supermarket.repositories.UsuarioRepository;

@Service
public class UsuarioService {
    
    @Autowired
    private UsuarioRepository userRepo;

    @Autowired
    CarrinhoService carrinhoService;
    
    @Transactional
    public Usuario saveUsuario(Usuario usuario) {
        if (existsByEmail(usuario.getEmail())) {
            throw new AlreadyExistException("Já existe um usuário com este email");
        }
        System.out.println(usuario.getEmail());
        usuario = userRepo.save(usuario);

        if (usuario.getCarrinho() == null) {
            Carrinho carrinho = new Carrinho();
            carrinho.setUser(usuario);

            carrinhoService.saveCarrinho(carrinho);
        }
        return usuario;
    }
    
    public Usuario getById(Long userId) {
        return userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com o id: " + userId));
    }

    public Usuario getByEmail(String email) {
        return Optional.ofNullable(userRepo.findByEmail(email))
                    .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com o email: " + email));
               
    }

    public Usuario updateUsuario(Usuario usuario, Long userId) {
        if (existById(userId)) {
            Usuario user = getById(userId);
            
            user.setNome(usuario.getNome());
            user.setEmail(usuario.getEmail());
            user.setPassword(usuario.getPassword());

            return userRepo.save(user);
        }else{
            throw new ResourceNotFoundException("Não foi encontrado nenhum usuário com o id: "+userId);
        }
    }

    public void deleteUsuario(Long userId){
        if(existById(userId)){
            userRepo.deleteById(userId);
        }else{
            throw new ResourceNotFoundException("Não foi encontrado nenhum usuário com o id: "+userId);
        }
    }

    private boolean existsByEmail(String email){
        return userRepo.existsByEmail(email);
    }

    private boolean existById(Long id){
        return userRepo.existsById(id);
    }

}
