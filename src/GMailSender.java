package be.hexter.hexter.other;

import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class GMailSender {

    private String username;// change accordingly
    private String password;

    private static GMailSender mailSender;

    private GMailSender(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public static GMailSender authenticate(String username, String password) {
        if (mailSender == null || (mailSender.username != username || mailSender.password != password)) {
            mailSender = new GMailSender(username, password);
        }
        return mailSender;
    }

    private final Properties PROPS;
    {
        PROPS = new Properties();
        PROPS.put("mail.smtp.auth", "true");
        PROPS.put("mail.smtp.starttls.enable", "true");
        PROPS.put("mail.smtp.host", "smtp.gmail.com");
        PROPS.put("mail.smtp.port", "587");
    }

    private final Session session;
    {
        session = Session.getInstance(PROPS, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
    }

    private final Message message;
    {
        message = new MimeMessage(session);
    }

    public void send(List<String> recipients, String subject, String msg)
            throws MessagingException, AddressException, MessagingException {
        message.setFrom(new InternetAddress(username));
        for (String recipient : recipients) {
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
        }
        message.setSubject(subject);
        message.setText(msg);
        Transport.send(message);
    }
}
