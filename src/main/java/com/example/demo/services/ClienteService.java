package com.example.demo.services;

import com.example.demo.domain.Cidade;
import com.example.demo.domain.Cliente;
import com.example.demo.domain.Endereco;
import com.example.demo.domain.dto.ClienteDTO;
import com.example.demo.domain.dto.ClienteNewDTO;
import com.example.demo.domain.enums.Perfil;
import com.example.demo.domain.enums.TipoCLiente;
import com.example.demo.repositories.ClienteRepository;
import com.example.demo.repositories.EnderecoRepository;
import com.example.demo.security.UserSS;
import com.example.demo.services.exception.AuthorizationException;
import com.example.demo.services.exception.DataIntegrityException;
import com.example.demo.services.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public Cliente find(Integer id) {
        UserSS user = UserService.authenticated();
        if(user == null || user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())){
            throw new AuthorizationException("Acesso negado");
        }
        Optional<Cliente> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
    }

    public Cliente update(Cliente obj){
        Cliente newObj = find(obj.getId());
        updateData(newObj, obj);
        return repository.save(newObj);
    }

    private void updateData(Cliente newObj,Cliente obj){
        newObj.setEmail(obj.getEmail());
        newObj.setNome(obj.getNome());
    }

    public List<Cliente> findAll(){
        return repository.findAll();
    }

    public void delete(Integer id){
        find(id);
        try {
            repository.deleteById(id);
        }catch (DataIntegrityViolationException e){
            throw new DataIntegrityException("Não é possível excluir uma Cliente que possui produtos");
        }
    }

    public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction),orderBy);
        return repository.findAll(pageRequest);
    }

   public Cliente fromDTO(ClienteDTO objDTO){
        return new Cliente(objDTO.getId(), objDTO.getNome(), objDTO.getEmail(), null,null,null);
    }

    public Cliente fromDTO(ClienteNewDTO objDTO){
        Cliente cli = new Cliente(null, objDTO.getNome(), objDTO.getEmail(), objDTO.getCpfOuCnpj(), TipoCLiente.toEnum(objDTO.getTipo()),bCryptPasswordEncoder.encode(objDTO.getSenha()));
        Cidade ci = new Cidade(objDTO.getCidadeId(),null ,null);
        Endereco endereco = new Endereco(null,objDTO.getLogradouro(), objDTO.getNumero(),objDTO.getComplemento(),objDTO.getBairro(), objDTO.getCep(),ci,cli);
        cli.getEnderecos().add(endereco);
        cli.getTelefones().add(objDTO.getTelefone1());

        if(objDTO.getTelefone2() != null)
            cli.getTelefones().add(objDTO.getTelefone2());
        if(objDTO.getTelefone3() != null)
            cli.getTelefones().add(objDTO.getTelefone3());
        return cli;
    }

    @Transactional
    public Cliente insert(Cliente obj){
        obj.setId(null);
        obj = repository.save(obj);
        enderecoRepository.saveAll(obj.getEnderecos());
        return obj;
    }
}