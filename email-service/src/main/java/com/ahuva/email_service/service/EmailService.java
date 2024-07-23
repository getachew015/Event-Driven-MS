package com.ahuva.email_service.service;

import com.ahuva.base_domain.dto.Item;
import com.ahuva.base_domain.dto.OrderEvent;
import com.sun.mail.smtp.SMTPTransport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Base64;
import java.util.List;
import java.util.Properties;

import static com.ahuva.email_service.constant.EmailConstants.*;
import static javax.mail.Message.RecipientType.CC;
import static javax.mail.Message.RecipientType.TO;

@Service
@Slf4j
public class EmailService {

    private OrderEvent orderEvent;

    public void sendMessage(OrderEvent orderEvent) throws MessagingException {
        this.setOrderEvent(orderEvent);
        Message message = createEmail();
        SMTPTransport smtpTransport = (SMTPTransport) getEmailSession().getTransport(SIMPLE_MAIL_TRANSFER_PROTOCOL);
        smtpTransport.connect(GMAIL_SMTP_SERVER, USERNAME, getCredential(PASSWORD));
        smtpTransport.sendMessage(message, message.getAllRecipients());
        smtpTransport.close();
        log.info("Email message was sent successfully ....");
    }

    private Message createEmail() throws MessagingException {
        Message message = new MimeMessage(getEmailSession());
        message.setFrom(new InternetAddress(FROM_EMAIL));
        message.setRecipients(TO, InternetAddress.parse(TO_EMAIL, false));
        message.setRecipients(CC, InternetAddress.parse(CC_EMAIL, false));
        message.setSubject(EMAIL_SUBJECT);
        message.setContent(getEmailBody(), "text/html");
        return message;
    }

    private Session getEmailSession() {
        Properties properties = System.getProperties();
        properties.put(SMTP_HOST, GMAIL_SMTP_SERVER);
        properties.put(SMTP_AUTH, true);
        properties.put(SMTP_PORT, DEFAULT_PORT);
        properties.put(SMTP_STARTTLS_ENABLE, true);
        properties.put(SMTP_STARTTLS_REQUIRED, true);
        return Session.getInstance(properties, null);
    }

    private String getEmailBody() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(
                        "<body>" +
                        "<P> Dear Sales Team,<br><br>This is to inform you that a new sales order has been placed.<br>" +
                        "Please find below the details : " +
                        "<h3>Item List</h3>" +
                        "<table>" +
                        "<tr>" +
                        "    <th>Item Id</th>" +
                        "    <th>Item Name</th>" +
                        "    <th>Quantity</th>" +
                        "    <th>Price</th>" +
                        "</tr>");
        getItemList().forEach(item -> {
            stringBuilder.append("<tr>" +
                    "<td>" + item.getItemId() + "</td>" +
                    "<td>" + item.getItemName() + "</td>" +
                    "<td>" + item.getQuantity() + "</td>" +
                    "<td>" + item.getPrice() + "</td>" +
                    "</tr>");
        });
        stringBuilder.append("</table> </body>");
        stringBuilder.append("<br><br><b><i>Best Regards<br>Ahuva Traders Retail Team<br>Dallas, TX USA<br>Email: support@ahuvatraders.com</i></b> <P>");
        return stringBuilder.toString();
    }

    private List<Item> getItemList() {
        return this.orderEvent.getOrder().getItemList();
    }

    private void setOrderEvent(OrderEvent orderEvent) {
        this.orderEvent = orderEvent;
    }

    private String getCredential(String pwrdString) {
        byte[] decodedBytes = Base64.getDecoder().decode(pwrdString);
        return new String(decodedBytes);
    }
}
