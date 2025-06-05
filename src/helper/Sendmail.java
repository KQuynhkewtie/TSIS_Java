package helper;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class Sendmail {
    public static void sendOTP(String toEmail, String otpCode) throws MessagingException {
        final String fromEmail = "Nhathuoc21623@gmail.com";
        final String password = "bvrqsgxdiahupvsa";

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail, "TSIS Pharmacy"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("Mã OTP xác nhận đặt lại mật khẩu");
            message.setText("Mã xác nhận của bạn là: " + otpCode);
            Transport.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}