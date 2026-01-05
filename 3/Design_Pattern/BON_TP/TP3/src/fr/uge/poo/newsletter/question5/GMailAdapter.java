package fr.uge.poo.newsletter.question5;

import com.goodcorp.gmailer.GMailer;

import java.util.List;

public class GMailAdapter implements MailAdapter {

  private final GMailer gMailer = new GMailer();

  @Override
  public void send(String userMail, String subject, String content) {
    var mail = new GMailer.Mail(subject, content);
    gMailer.send(userMail, mail);
  }

  @Override
  public void bulkSend(List<User> recipients, String subject, String content) {
    var mail = new GMailer.Mail(subject, content);
    var emails = recipients.stream().map(User::email).toList();
    gMailer.sendBulk(emails, mail);
  }
}
