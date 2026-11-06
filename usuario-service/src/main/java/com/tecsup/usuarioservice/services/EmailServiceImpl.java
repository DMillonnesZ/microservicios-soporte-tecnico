package com.tecsup.usuarioservice.services;

import com.tecsup.usuarioservice.exception.EmailSendException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

/**
 * Envia correos por SMTP (Gmail).
 * Si app.mail.enabled = false, NO envia: imprime el codigo en consola (util sin internet).
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Value("${app.mail.enabled:true}")
    private boolean mailEnabled;

    @Value("${app.mail.from:}")
    private String remitente;

    @Value("${app.verificacion.minutos-expiracion:10}")
    private int minutosExpiracion;

    @Override
    public void enviarCodigoVerificacion(String destinatario, String nombre, String codigo) {
        if (!mailEnabled) {
            log.warn("[MAIL DESACTIVADO] Codigo de verificacion para {}: {}", destinatario, codigo);
            return;
        }
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(remitente, "Soporte Tecnico");
            helper.setTo(destinatario);
            helper.setSubject("Codigo de verificacion - Soporte Tecnico");
            helper.setText(plantilla(nombre, codigo), true);
            mailSender.send(message);
            log.info("Codigo de verificacion enviado a {}", destinatario);
        } catch (MailException | MessagingException | java.io.UnsupportedEncodingException ex) {
            log.error("No se pudo enviar el correo a {}", destinatario, ex);
            throw new EmailSendException("No se pudo enviar el correo de verificacion a " + destinatario);
        }
    }

    private String plantilla(String nombre, String codigo) {
        return """
                <div style="font-family:Arial,sans-serif;max-width:420px;margin:auto;padding:24px;
                            border:1px solid #e5e7eb;border-radius:12px">
                  <h2 style="color:#1e3a8a;margin-top:0">Sistema de Soporte Tecnico</h2>
                  <p>Hola <b>%s</b>, usa este codigo para verificar tu email:</p>
                  <p style="font-size:32px;letter-spacing:8px;font-weight:bold;text-align:center;
                            background:#eff6ff;padding:12px;border-radius:8px">%s</p>
                  <p style="color:#6b7280;font-size:13px">El codigo vence en %d minutos.
                     Si no solicitaste este registro, ignora este mensaje.</p>
                </div>
                """.formatted(nombre, codigo, minutosExpiracion);
    }
}
