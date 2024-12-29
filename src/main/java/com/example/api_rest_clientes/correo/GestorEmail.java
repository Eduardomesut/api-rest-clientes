package com.example.api_rest_clientes.correo;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;

public class GestorEmail {

    private Properties propiedades;
    private Session session;
    private void setPropiedadesServidorSMTP(){
        propiedades = System.getProperties();
        propiedades.put("mail.smtp.auth", "true");
        propiedades.put("mail.smtp.host", "smtp.gmail.com");
        propiedades.put("mail.smtp.port", "587");
        propiedades.put("mail.smtp.starttls.enable", "true");
        session = Session.getInstance(propiedades, null);
    }
    private Transport conectarServidorSMTP(String direccionEmail, String password) throws NoSuchProviderException, MessagingException{
        Transport t = (Transport) session.getTransport("smtp");
        t.connect(propiedades.getProperty("mail.smtp.host"), direccionEmail, password);
        return t;
    }
    private Message crearMensajeTexto(String emisor, String destinatario, String asunto, String textoMensaje) throws MessagingException, AddressException,
            IOException{
        Message mensaje = crearNucleoMensaje(emisor, destinatario, asunto);
        mensaje.setText(textoMensaje);
        return mensaje;

    }

    private Message crearNucleoMensaje(String emisor, String destinatario, String asunto) throws AddressException, MessagingException {
        Message mensaje = new MimeMessage(session);
        mensaje.setFrom(new InternetAddress(emisor));
        mensaje.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
        mensaje.setSubject(asunto);
        return mensaje;
    }
    private Message crearMensajeConAdjunto(String emisor, String destinatario, String asunto, String textoMensaje, String pathFichero)
        throws MessagingException, AddressException, IOException{
        Message mensaje = crearNucleoMensaje(emisor, destinatario, asunto);
        BodyPart bodyPart = new MimeBodyPart();
        bodyPart.setText(textoMensaje);
        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.attachFile(new File(pathFichero));
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(bodyPart);
        multipart.addBodyPart(mimeBodyPart);
        mensaje.setContent(multipart);
        return mensaje;
    }
    public void enviarMensajeTexto(String emisor, String destinatario, String asunto, String textoMensaje, String direccionEmail,
                                   String password) throws AddressException, MessagingException, IOException{
        setPropiedadesServidorSMTP();
        Message mensaje = crearMensajeTexto(emisor, destinatario, asunto, textoMensaje);
        Transport t = conectarServidorSMTP(direccionEmail, password);
        t.sendMessage(mensaje, mensaje.getAllRecipients());
        t.close();
        return;
    }
    public void enviarMensajeConAdjunto(String emisor, String destinatario, String asunto, String textoMensaje, String direccionEmail,
                                        String password, String pathFichero) throws AddressException, MessagingException, IOException{
        setPropiedadesServidorSMTP();
        Message mensaje = crearMensajeConAdjunto(emisor, destinatario, asunto, textoMensaje, pathFichero);
        Transport t = conectarServidorSMTP(direccionEmail, password);
        t.sendMessage(mensaje, mensaje.getAllRecipients());
        t.close();
        return;

    }

    public void mandarCorreo(String direccionCorreo){
        try {

            String emailEmisor = "insertar Mail";

            String passwordEmisor = "Insertar Contraseña de aplicación";

            GestorEmail gestorEmail = new GestorEmail();
            gestorEmail.enviarMensajeTexto(emailEmisor, direccionCorreo, "Bienvenido a NH Hotels",
                    "Este correo es para confirmar tu registro en nuestra plataforma", emailEmisor, passwordEmisor);
            gestorEmail.enviarMensajeConAdjunto(emailEmisor, direccionCorreo, "Descubre el servicio NH Premium",
                    "Ahora tendras numerosas ventajas con el nuevo servicio NH Premium", emailEmisor, passwordEmisor, "WelcomeNH.jpg");
            System.out.println("Correo enviado correctamente");
        }catch (Exception e){
            e.printStackTrace();
        }

    }



}
