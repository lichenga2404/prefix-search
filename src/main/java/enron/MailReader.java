package enron;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.util.Properties;

public class MailReader {
    
    public String load(File mailpath) throws MessagingException, IOException {
        Session s = Session.getDefaultInstance(new Properties());
        InputStream is = new FileInputStream(mailpath);
        MimeMessage message = new MimeMessage(s, is);
        //System.out.println("SUBJECT:" + message.getSubject());
        //System.out.println("BODY:"  + (String)message.getContent());

        return (String) message.getContent() + " " + message.getSubject();
    }
    
}
