package service;

import java.util.Properties;
import java.util.ResourceBundle;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author aitor
 */
public class EmailService {

    // Server mail user & pass account
    private static String user = null;
    private static String pass = null;

    // DNS Host + SMTP Port
    private static String smtp_host = null;
    private static int smtp_port = 0;
    
    //Properties file containing the data avobe.
    private static ResourceBundle propertiesFile = null;

    public static void sendMail(String receiver, String subject, String text) throws MessagingException {
        //Getting email properties from properties file...
        propertiesFile = ResourceBundle.getBundle("properties.properties");
        user = propertiesFile.getString("emailUser");
        pass = propertiesFile.getString("emailPassword");
        smtp_host = propertiesFile.getString("smtpHost");
        smtp_port = Integer.valueOf(propertiesFile.getString("smtpPort"));
        
        //Setting email properties...
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", smtp_host);
        properties.put("mail.smtp.port", smtp_port);
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.ssl.trust", smtp_host);
        properties.put("mail.imap.partialfetch", false);

        // Authenticator knows how to obtain authentication for a network connection.
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, pass);
            }
        });

        // MIME message to be sent
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(user));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiver)); // Ej: receptor@gmail.com
        message.setSubject(subject); // Asunto del mensaje

        // A mail can have several parts
        Multipart multipart = new MimeMultipart();

        // A message part (the message, but can be also a File, etc...)
        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(text, "text/html");
        multipart.addBodyPart(mimeBodyPart);

        // Adding up the parts to the MIME message
        message.setContent(multipart);

        // And here it goes...
        Transport.send(message);
    }

    public static void main(String[] args) {
        try {
            EmailService.sendMail("aitorfidalgo@gmail.com", "Mensaje de prueba", 
                    "Correo de vital importancia");
            System.out.println("Ok, mail sent!");
        } catch (MessagingException e) {
            System.out.println("Doh! " + e.getMessage());
        }
    }
}
