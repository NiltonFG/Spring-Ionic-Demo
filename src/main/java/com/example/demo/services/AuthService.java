package com.example.demo.services;

import com.example.demo.domain.Cliente;
import com.example.demo.repositories.ClienteRepository;
import com.example.demo.services.exception.ObjectNotFoundException;
import org.aspectj.weaver.patterns.AndSignaturePattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class AuthService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private EmailService emailService;

    //Gera uma senha de 10 digitos aleatorios que são mandados no email
    public void sendNewPassword(String email){
        Cliente cliente = clienteRepository.findByEmail(email);
        if(cliente == null){
            throw new ObjectNotFoundException("Email não encontrado");
        }
        String newPass = newPassword();
        cliente.setSenha(bCryptPasswordEncoder.encode(newPass));
        clienteRepository.save(cliente);
        emailService.sendNewPassword(cliente,newPass);

    }

    private String newPassword() {
        char[] vet = new char[10];
        for (int i = 0; i < 10; i++) {
            vet[i] = randomChar();
        }
        return new String(vet);
    }

    //Gerando caracter aleatorio pelo opCodes
    private char randomChar() {
        Random rand = new Random();
        int opt = rand.nextInt(3);
        if(opt == 0)
            return (char)(rand.nextInt(10)+48);
        else if(opt == 1)
            return (char)(rand.nextInt(26)+65);
        else
            return (char)(rand.nextInt(26)+97);
    }
}
