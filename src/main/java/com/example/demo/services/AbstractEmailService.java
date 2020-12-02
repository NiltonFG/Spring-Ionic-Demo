package com.example.demo.services;

import com.example.demo.domain.Pedido;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import java.util.Date;


public abstract class AbstractEmailService implements EmailService {

    @Value("${default.sender}")
    private String sender;

    @Override
    public void sendOrderConfirmationEmail(Pedido obj){
        SimpleMailMessage msg;
        msg = prepareSimpleMailMenssageFromPedido(obj);
        sendEmail(msg);
    }

    private SimpleMailMessage prepareSimpleMailMenssageFromPedido(Pedido pedido){
        SimpleMailMessage sm = new SimpleMailMessage();
        sm.setTo(pedido.getCliente().getEmail());
        sm.setFrom(sender);
        sm.setSubject("Pedido Confirmado! código: " + pedido.getId());
        sm.setSentDate(new Date(System.currentTimeMillis()));
        sm.setText(pedido.toString());
        return sm;
    }
}
