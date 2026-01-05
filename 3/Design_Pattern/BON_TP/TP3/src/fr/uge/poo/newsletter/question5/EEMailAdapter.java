package fr.uge.poo.newsletter.question5;

import com.evilcorp.eemailer.EEMailer;

public class EEMailAdapter implements MailAdapter {

  private final EEMailer eeMailer = new EEMailer();

  @Override
  public void send(String recipient, String subject, String content) {
      var mail = new EEMailer.Mail(recipient, subject, content);
      eeMailer.send(mail);
  }
}
