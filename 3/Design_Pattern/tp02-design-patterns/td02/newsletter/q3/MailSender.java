package fr.uge.poo.newsletter.q3;

import java.util.List;

public sealed interface MailSender permits GMailerAdapter, EEMailerAdapter{
    void send(String recipient, String subject, String content);
    default void sendBulk(List<String> recipients, String subject, String content){
        recipients.forEach(recipient -> send(recipient, subject, content));
    }
}