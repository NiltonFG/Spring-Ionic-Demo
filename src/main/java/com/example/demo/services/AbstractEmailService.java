package com.example.demo.services;

import com.example.demo.domain.Cliente;
import com.example.demo.domain.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;


public abstract class AbstractEmailService implements EmailService {

    @Value("${default.sender}")
    private String sender;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private JavaMailSender javaMailSender;

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

    protected String htmlFromTemplatePedido(Pedido obj){
        Context context = new Context();
        context.setVariable("pedido",obj);
        return templateEngine.process("Email/confirmacaoPedido",context);
    }

    @Override
    public void sendOrderConfirmationHtmlEmail(Pedido obj){
        MimeMessage msg;
        try {
            msg = prepareMimeMailMenssageFromPedido(obj);
            sendHtmlEmail(msg);
        } catch (MessagingException e) {
            sendOrderConfirmationEmail(obj);
        }
    }

    protected MimeMessage prepareMimeMailMenssageFromPedido(Pedido obj) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mmh = new MimeMessageHelper(mimeMessage,true);
        mmh.setTo(obj.getCliente().getEmail());
        mmh.setFrom(sender);
        mmh.setSubject("Envio confirmado! Código:" + obj.getId());
        mmh.setSentDate(new Date(System.currentTimeMillis()));
        mmh.setText(htmlFromTemplatePedido(obj),true);
        return mimeMessage;
    }

    @Override
    public void sendNewPassword(Cliente cliente, String newPass) {
        SimpleMailMessage msg;
        msg = prepareSimpleMailMenssageFromNewPassword(cliente,newPass);
        sendEmail(msg);
    }

    protected SimpleMailMessage prepareSimpleMailMenssageFromNewPassword(Cliente cliente, String newPass) {
        SimpleMailMessage sm = new SimpleMailMessage();
        sm.setTo(cliente.getEmail());
        sm.setFrom(sender);
        sm.setSubject("Solicitação de nova senha");
        sm.setSentDate(new Date(System.currentTimeMillis()));
        sm.setText("nova senha:" + newPass);
        return sm;
    }
}
