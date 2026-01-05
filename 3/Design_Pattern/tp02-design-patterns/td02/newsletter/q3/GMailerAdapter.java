package fr.uge.poo.newsletter.q3;

import java.util.List;

public final class GMailerAdapter implements MailSender{
    private final GMailer gMailer = new GMailer();

    @Override
    public void send(String recipient, String subject, String content) {
        gMailer.send(recipient, new GMailer.Mail(subject, content));
    }

    @Override
    public void sendBulk(List<String> recipients, String subject, String content) {
        gMailer.sendBulk(recipients, new GMailer.Mail(subject, content));
    }
}