package fr.uge.poo.newsletter.question5;

import java.util.List;

public class EmptyObserver implements Observer{

  @Override
  public void onSubscribeSuccess(String newsLetterName, String userMail, int totalSubscribed) {

  }

  @Override
  public void onSubscribeError(String newsLetterName, String userName) {

  }

  @Override
  public void onNewsLetterSend(String newsLetterName, String mailingList, List<String> emails) {

  }

  @Override
  public void onSubscribeListUpdate(String mailingList, List<String> users) {

  }
}
