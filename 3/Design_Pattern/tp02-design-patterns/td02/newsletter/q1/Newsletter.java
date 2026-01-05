package fr.uge.poo.newsletter.q1;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

public class Newsletter {
    private final String name;
    private final Map<User, String> users = new HashMap<>();
    private final EEMailer mailer = new EEMailer();
    private final Predicate<User> predicate;

    public Newsletter(String name, Predicate<User> predicate){
        this.name = Objects.requireNonNull(name);
        this.predicate = Objects.requireNonNull(predicate);
    }

    public void subscribe(User user){
        Objects.requireNonNull(user);
        if(users.containsValue(user.email())){
            throw new IllegalArgumentException("This user is already subscribed");
        }
        if(!predicate.test(user)){
            throw new IllegalArgumentException("This user does not meet the criteria");
        }
        users.put(user, user.email());
    }

    public void unsubscribe(User user){
        Objects.requireNonNull(user);
        users.remove(user);
    }

    public void sendMessage(String title, String content){
        Objects.requireNonNull(title);
        Objects.requireNonNull(content);
        users.forEach((_,email) -> mailer.send(new EEMailer.Mail(email, title, content)));
    }
}