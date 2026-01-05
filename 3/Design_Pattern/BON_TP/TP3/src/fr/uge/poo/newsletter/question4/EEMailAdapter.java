package fr.uge.poo.newsletter.question4;

import com.evilcorp.eemailer.EEMailer;

public class EEMailAdapter implements MailAdapter {

  private final EEMailer eeMailer = new EEMailer();

  @Override
  public void send(User recipient, String subject, String content) {
      var mail = new EEMailer.Mail(recipient.email(), subject, content);
      eeMailer.send(mail);
  }
}
