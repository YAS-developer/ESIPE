package fr.uge.poo.newsletter.q2;

import java.util.List;

public class Main {
    void main(){
        var arnaud = new User("Arnaud", "arnaud.carayol@univ-eiffel.fr", 25, User.Nationality.FRENCH);
        var youssef = new User("Youssef", "youssef@gmail.com", 22, User.Nationality.BRITISH);
        var potter = new Newsletter.NewsletterBuilder()
                .name("Potter 4ever")
                .nationality(User.Nationality.BRITISH)
                .ageAbove(18)
                .build();
        var java = new Newsletter.NewsletterBuilder()
                .name("Java")
                .nationality(List.of(User.Nationality.FRENCH, User.Nationality.BRITISH))
                .ageAbove(21)
                .build();
        var why = new Newsletter("Why me!", user -> user.email().endsWith("@univ-eiffel.fr") && user.age() % 2 == 0);
        java.subscribe(arnaud);
        java.subscribe(youssef);
        java.sendMessage("Hello", "Content1");
        java.unsubscribe(youssef);
        java.sendMessage("Hi", "Content2");
    }
}