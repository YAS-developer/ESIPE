package fr.uge.poo.newsletter.q1;

public class Main {
    void main(){
        var arnaud = new User("Arnaud", "arnaud.carayol@univ-eiffel.fr", 25, User.Nationality.FRENCH);
        var youssef = new User("Youssef", "youssef@gmail.com", 22, User.Nationality.BRITISH);
        var potter = new Newsletter("Potter 4 ever", user -> user.age() > 18 && user.nationality() == User.Nationality.BRITISH);
        var java = new Newsletter("Java", user -> user.age() > 21 && (user.nationality() == User.Nationality.FRENCH || user.nationality() == User.Nationality.BRITISH));
        var why = new Newsletter("Why me!", user -> user.email().endsWith("@univ-eiffel.fr") && user.age() % 2 == 0);
        java.subscribe(arnaud);
        java.subscribe(youssef);
        java.sendMessage("Hello", "Content1");
        java.unsubscribe(youssef);
        java.sendMessage("Hi", "Content2");
    }
}
