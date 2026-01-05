package fr.uge.poo.newsletter.question5;

import java.util.List;

public interface Observer {
  void onSubscribeSuccess(String newsLetterName, String userMail, int totalSubscribed);
  void onSubscribeError(String newsLetterName, String userName);
  void onNewsLetterSend(String newsLetterName, String mailingList, List<String> emails);
  void onSubscribeListUpdate(String mailingList, List<String> users);
}
