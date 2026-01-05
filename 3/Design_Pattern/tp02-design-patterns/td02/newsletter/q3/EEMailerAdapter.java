package fr.uge.poo.newsletter.q3;

public final class EEMailerAdapter implements MailSender{
    private final EEMailer eeMailer = new EEMailer();

    @Override
    public void send(String recipient, String subject, String content) {
        eeMailer.send(new EEMailer.Mail(recipient, subject, content));
    }
}