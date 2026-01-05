package fr.uge.poo.newsletter.q2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    private Newsletter(NewsletterBuilder newsletterBuilder){
        Objects.requireNonNull(newsletterBuilder);
        this.name = newsletterBuilder.name;
        this.predicate = user ->
                (newsletterBuilder.nationalities.contains(user.nationality()) || newsletterBuilder.nationalities.isEmpty())
                        && user.age() > newsletterBuilder.ageAbove;
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
        users.remove(Objects.requireNonNull(user));
    }

    public void sendMessage(String title, String content){
        Objects.requireNonNull(title);
        Objects.requireNonNull(content);
        users.forEach((_,email) -> mailer.send(new EEMailer.Mail(email, title, content)));
    }

    public static class NewsletterBuilder{
        private String name;
        private int ageAbove;
        private final List<User.Nationality> nationalities = new ArrayList<>();

        public NewsletterBuilder name(String name){
            this.name = Objects.requireNonNull(name);
            return this;
        }
        public NewsletterBuilder nationality(User.Nationality nationality){
            nationalities.add(Objects.requireNonNull(nationality));
            return this;
        }
        public NewsletterBuilder nationality(List<User.Nationality> nationalities){
            this.nationalities.addAll(Objects.requireNonNull(nationalities));
            return this;
        }
        public NewsletterBuilder ageAbove(int ageAbove){
            this.ageAbove = ageAbove;
            return this;
        }
        public Newsletter build(){
            Objects.requireNonNull(name);
            return new Newsletter(this);
        }
    }
}