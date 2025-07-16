package org.clotheswarehouse_hsf.utils;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Properties;

@Component
public class EmailUtil {

    private static final String FROM_EMAIL = "bangtxhe163986@fpt.edu.vn";
    private static final String APP_PASSWORD = "bsjd uezf mhsy pzqw";
    private static final String FROM_NAME = "Hệ Thống Quản Lý Kho Hàng";

    public boolean sendOTP(String toEmail, String otp, String name) {
        try {

            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

            Session session = Session.getInstance(props, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(FROM_EMAIL, APP_PASSWORD);
                }
            });

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FROM_EMAIL, FROM_NAME));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("Mã OTP Khôi Phục Mật Khẩu");
            message.setSentDate(new Date());

            String content = buildOTPContent(otp, name);
            message.setContent(content, "text/html; charset=utf-8");

            Transport.send(message);
            System.out.println("Đã gửi OTP đến email: " + toEmail);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Gửi email thất bại: " + e.getMessage());
            return false;
        }
    }

    private String buildOTPContent(String otp, String name) {
        return "<!DOCTYPE html>" +
                "<html><head><meta charset='UTF-8'>" +
                "<style>" +
                "body{font-family:sans-serif;padding:20px;background:#f4f4f4;}" +
                ".box{max-width:600px;margin:auto;background:#fff;padding:20px;border-radius:8px;}" +
                ".otp{font-size:30px;font-weight:bold;color:#007bff;margin:20px 0;}" +
                "</style></head><body>" +
                "<div class='box'>" +
                "<h2>Xin chào " + name + ",</h2>" +
                "<p>Bạn vừa yêu cầu đặt lại mật khẩu.</p>" +
                "<p>Mã OTP của bạn là:</p>" +
                "<div class='otp'>" + otp + "</div>" +
                "<p>Mã này có hiệu lực trong vòng <b>5 phút</b>.</p>" +
                "<hr><p>Vui lòng không chia sẻ mã này cho bất kỳ ai.</p>" +
                "<p>Trân trọng,<br>Hệ Thống Quản Lý Kho Hàng</p>" +
                "</div></body></html>";
    }

    public boolean sendEmail(String toEmail, String subject, String content) {
        try {
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

            Session session = Session.getInstance(props, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(FROM_EMAIL, APP_PASSWORD);
                }
            });

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FROM_EMAIL, FROM_NAME));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);
            message.setSentDate(new Date());

            message.setContent(content, "text/html; charset=utf-8");

            Transport.send(message);
            System.out.println("Đã gửi email đến: " + toEmail);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Gửi email thất bại: " + e.getMessage());
            return false;
        }
    }

}
