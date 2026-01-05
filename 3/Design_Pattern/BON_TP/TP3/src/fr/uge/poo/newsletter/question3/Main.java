package fr.uge.poo.newsletter.question3;

import java.util.List;

public class Main {

  static void main() {
    UserCondition potterCondition = u -> u.age() >= 18 && u.nationality() == User.Nationality.BRITISH;
    UserCondition javaCondition = u -> u.age() > 21 && (u.nationality() == User.Nationality.FRENCH || u.nationality() == User.Nationality.BRITISH);
    UserCondition univCondition = u -> u.email().endsWith("@univ-eiffel.fr") && u.age() % 2 == 0;


    var u1  = new User("Alice Dupont", "alice@univ-eiffel.fr", 17, User.Nationality.FRENCH);
    var u2  = new User("Bob Potter", "bob@hogwarts.uk", 18, User.Nationality.BRITISH);
    var u3  = new User("Charlie Martin", "charlie@univ-eiffel.fr", 22, User.Nationality.FRENCH);
    var u4  = new User("Daphne Grey", "daphne@example.com", 23, User.Nationality.BRITISH);
    var u5  = new User("Ethan Brown", "ethan@hogwarts.uk", 30, User.Nationality.BRITISH);
    var u6  = new User("Fiona Laurent", "fiona@school.com", 20, User.Nationality.FRENCH);
    var u7  = new User("George Lefevre", "george@company.com", 24, User.Nationality.FRENCH);
    var u8  = new User("Helen Carter", "helen@univ-eiffel.fr", 26, User.Nationality.BRITISH);
    var u9  = new User("Ian Summers", "ian@random.com", 19, User.Nationality.BRITISH);
    var u10 = new User("Julia Moreau", "julia@univ-eiffel.fr", 28, User.Nationality.FRENCH);
    var u11 = new User("Moreau julia", "julia@univ-eiffel.fr", 27, User.Nationality.SPANISH);

    List<User> users = List.of(u1, u2, u3, u4, u5, u6, u7, u8, u9, u10, u11);

    var potterUsers = users.stream().filter(potterCondition::test).toList();
    var poterNewsletter = Newsletter.with()
            .name("Potter 4ever")
            .subscribe(potterUsers)
            .allowedNationality(User.Nationality.BRITISH)
            .minAge(18)
            .build();

    var javaUsers = users.stream().filter(javaCondition::test).toList();
    var javaNewsletter = Newsletter.with()
            .name("Java 4ever")
            .subscribe(javaUsers)
            .allowedNationality(User.Nationality.BRITISH, User.Nationality.FRENCH)
            .minAge(21)
            .build();


    var univUsers = users.stream().filter(univCondition::test).toList();
    var univNewsletter = Newsletter.with()
            .name("Java 4ever")
            .subscribe(univUsers)
            .condition(u -> u.email().endsWith("@univ-eiffel.fr") && u.age() % 2 == 0)
            .build();

//    univNewsletter.subscribe(u2);
  }

}
