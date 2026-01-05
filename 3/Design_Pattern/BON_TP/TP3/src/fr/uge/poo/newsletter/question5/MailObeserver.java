package fr.uge.poo.newsletter.question5;

import java.util.List;

public class MailObeserver implements Observer {

  private final MailAdapter mailAdapter;

  public MailObeserver(MailAdapter mailAdapter) {
    this.mailAdapter = mailAdapter;
  }

  @Override
  public void onSubscribeSuccess(String newsLetterName, String userMail, int totalSubscribed) {
    var msg = "Welcome to " + newsLetterName;
    mailAdapter.send(userMail, "Welcome", msg);
  }

  @Override
  public void onSubscribeError(String newsLetterName, String userName) {
    mailAdapter.send("support@goodcorp.com", "ERROR with " + newsLetterName, userName);
  }

  @Override
  public void onNewsLetterSend(String newsLetterName, String mailingList, List<String> emails) {
    if (emails.stream().anyMatch(s -> s.endsWith("etud.univ-eiffel.fr"))) {
      mailAdapter.send("spy@nsa.org", "SPY: " + newsLetterName + " " + mailingList, "NewLetter: " + newsLetterName + " mailingList: " + mailingList);
    }
  }

  @Override
  public void onSubscribeListUpdate(String mailingList, List<String> users) {
    if (users.size() > 100) {
      mailAdapter.send("sales@goodcorp.com", "Size of " + mailingList + " > 100", users.toString());
    }
  }
}
