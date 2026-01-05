package fr.uge.poo.uberclient.q1;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class UberClient {
    private String firstName;
    private String lastName;
    private long uid;
    private List<Integer> grades;
    private List<String> emails;
    private List<String> phoneNumbers;

    private UberClient(UberClientBuilder uberClientBuilder){
        this.firstName = uberClientBuilder.firstName;
        this.lastName = uberClientBuilder.lastName;
        this.uid = uberClientBuilder.uid;
        this.grades = uberClientBuilder.grades;
        this.emails = uberClientBuilder.emails;
        this.phoneNumbers = uberClientBuilder.phoneNumbers;
    }

    public static class UberClientBuilder{
        private String firstName;
        private String lastName;
        private long uid;
        private List<Integer> grades;
        private List<String> emails;
        private List<String> phoneNumbers;

        public UberClientBuilder firstName(String firstName){
            this.firstName = Objects.requireNonNull(firstName);
            return this;
        }
        public UberClientBuilder lastName(String lastName){
            this.lastName = Objects.requireNonNull(lastName);
            return this;
        }
        public UberClientBuilder uid(long uid){
            if (uid < 0) {
                throw new IllegalArgumentException("UID must be positive");
            }
            this.uid = uid;
            return this;
        }
        public UberClientBuilder uid(){
            this.uid = ThreadLocalRandom.current().nextLong(1, 6);
            return this;
        }
        public UberClientBuilder grades(List<Integer> grades){
            this.grades = List.copyOf(grades);
            if (grades.isEmpty()){
                throw new IllegalArgumentException("A client must have at least one grade");
            }
            for(var grade : grades){
                if (grade < 1 || grade > 5) {
                    throw new IllegalArgumentException("All grades must be between 1 and 5");
                }
            }
            return this;
        }
        public UberClientBuilder emails(List<String> emails){
            this.emails = List.copyOf(emails);
            return this;
        }
        public UberClientBuilder phoneNumbers(List<String> phoneNumbers){
            this.phoneNumbers = List.copyOf(phoneNumbers);
            return this;
        }
        public UberClient build(){
            Objects.requireNonNull(firstName);
            Objects.requireNonNull(lastName);
            if (emails.isEmpty() && phoneNumbers.isEmpty()) {
                throw new IllegalArgumentException("A client must have at least an email or a phoneNumber");
            }
            return new UberClient(this);
        }
    }
}