package service;

import exception.UnexpectedErrorException;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import security.PrivateDecrypt;

/**
 * Contains the method meant to send email.
 *
 * @author Aitor Fidalgo
 */
public class EmailService {

    private static final Logger LOGGER = Logger.getLogger(EmailService.class.getName());

    // Server mail user & pass account
    private static String user = null;
    private static String password = null;
    // DNS Host + SMTP Port
    private static String smtp_host = null;
    private static int smtp_port = 0;
    //Properties file containing the data avobe.
    private static ResourceBundle propertiesFile = null;

    /**
     * Sends an email with the specified content to the specified reciever.
     *
     * @param receiver The reciever of the email.
     * @param subject The subject of the email.
     * @param text The text of the email.
     * @throws MessagingException If any messaging error occurres.
     * @throws exception.UnexpectedErrorException If anything goes wrong.
     */
    public static void sendMail(String receiver, String subject, String text)
            throws MessagingException, UnexpectedErrorException {
        LOGGER.log(Level.INFO, "Starting method sendEmail on {0}", EmailService.class.getName());

        //Getting email properties from properties file...
        propertiesFile = ResourceBundle.getBundle("properties.properties");
        smtp_host = propertiesFile.getString("smtpHost");
        smtp_port = Integer.valueOf(propertiesFile.getString("smtpPort"));
        //Getting user and password...
        String emailCredentials = PrivateDecrypt.decodeEmailCredentials();
        user = emailCredentials.substring(0, emailCredentials.indexOf("?"));
        password = emailCredentials.substring(emailCredentials.indexOf("?") + 1);

        //Setting email properties...
        LOGGER.log(Level.INFO, "Setting email properties on {0}", EmailService.class.getName());
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
                return new PasswordAuthentication(user, password);
            }
        });

        // MIME message to be sent
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(user));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiver)); // Ej: receptor@gmail.com
        message.setSubject(subject);

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
}
