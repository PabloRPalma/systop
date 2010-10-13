package datashare;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import junit.framework.TestCase;

public class JavaMailTest extends TestCase {
  public void testSend() throws AddressException, MessagingException {
    String host = "smtp.sina.com"; // 发件人使用发邮件的电子信箱服务器
    String from = "catstiger@sina.com"; // 发邮件的出发地（发件人的信箱）
    String to = "cats_tiger@163.com"; // 发邮件的目的地（收件人信箱）
    Properties props = new Properties();
    // Setup mail server
    props.put("mail.smtp.host", host);
    props.put("mail.smtp.port", "25");
    //这样才能通过验证
    props.put("mail.smtp.auth", "true"); 

    Authenticator auth = new MyAuthenticator("catstiger@sina.com", "love125");
    Session session = Session.getDefaultInstance(props, auth);

    MimeMessage message = new MimeMessage(session);
    
    message.addHeader("X-Mailer", "Sam Java mail");
    message.setFrom(new InternetAddress(from));

    // Set the to address
    message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
    // Set the subject
    message.setSubject("关于应用程序的问题", "UTF-8");
    // Set the content
    message.setText("你好：这是用java写的发送电子邮件的测试程序，请你看看吧！", "UTF-8");
    message.saveChanges();
    Transport.send(message);
  }

  public static class MyAuthenticator extends Authenticator {
    private String user;
    private String password;

    public MyAuthenticator(String user, String password) {
      this.user = user;
      this.password = password;
    }

    protected PasswordAuthentication getPasswordAuthentication() {
      return new PasswordAuthentication(user, password);
    }
  }
}
