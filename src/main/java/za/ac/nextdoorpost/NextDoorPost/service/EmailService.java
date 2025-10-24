package za.ac.nextdoorpost.NextDoorPost.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import za.ac.nextdoorpost.NextDoorPost.model.ContactMessage;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Autowired
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendContactMessage(ContactMessage contactMessage) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("shalompeacemasina@gmail.com"); // where you want to receive messages
        message.setSubject(contactMessage.getSubject());
        message.setText(
                "Name: " + contactMessage.getName() + "\n" +
                        "Email: " + contactMessage.getEmail() + "\n\n" +
                        "Message:\n" + contactMessage.getMessage()
        );

        mailSender.send(message);
    }
}
