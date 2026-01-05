package fr.uge.poo.uberclientformatters.question2;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
import java.util.function.UnaryOperator;

public class UberClient {

  public static ToDoubleFunction<List<Integer>> AVG_FUN = list -> list.stream().mapToDouble(integer -> integer).average().orElseThrow(() -> new AssertionError("Client are meant to have at least one grade"));
  public static UnaryOperator<List<String>> EMAILS_ORG = strings -> strings;


  public static class Builder {

    public static final Supplier<Long> RANDOM_LONG = () -> ThreadLocalRandom.current().nextLong(0,Long.MAX_VALUE);

    private String firstName;
    private String lastName;
    private long uid = Long.MIN_VALUE;
    private final List<Integer> grades = new ArrayList<>();
    private final List<String> emails = new ArrayList<>();
    private final List<String> phoneNumbers = new ArrayList<>();

    public Builder firstName(String firstName) {
      this.firstName = Objects.requireNonNull(firstName);
      return this;
    }

    public Builder lastName(String lastName) {
      this.lastName = Objects.requireNonNull(lastName);
      return this;
    }

    public Builder uid(long uid) {
      if (uid < 0) {
        throw new IllegalArgumentException("uid must be positive");
      }
      this.uid = uid;
      return this;
    }

    public Builder uid(Supplier<Long> func) {
      var tmpUid = func.get();
      return uid(tmpUid);
    }

    public Builder randomUID(){
      return uid(RANDOM_LONG);
    }

    public Builder grade(int grade) {
      if (grade < 0 || grade > 5) {
        throw new IllegalArgumentException("0 <= grade <= 5");
      }
      grades.add(grade);
      return this;
    }

    public Builder grades(List<Integer> grades) {
      for(var val : Objects.requireNonNull(grades)) {
        if (val < 0 || val > 5) {
          throw new IllegalArgumentException("0 <= grade <= 5");
        }
      }
      this.grades.addAll(grades);
      return this;
    }

    public Builder email(String mail) {
      emails.add(Objects.requireNonNull(mail));
      return this;
    }

    public Builder emails(List<String> emails) {
      this.emails.addAll(Objects.requireNonNull(emails));
      return this;
    }

    public Builder phoneNumber(String phoneNumber) {
      phoneNumbers.add(Objects.requireNonNull(phoneNumber));
      return this;
    }

    public Builder phoneNumbers(List<String> phoneNumbers) {
      this.phoneNumbers.addAll(Objects.requireNonNull(phoneNumbers));
      return this;
    }

    public UberClient build() {
      Objects.requireNonNull(firstName);
      Objects.requireNonNull(lastName);

      if (uid == Long.MIN_VALUE) {
        throw new IllegalArgumentException("UID not specified");
      }

      if (uid < 0) {
        throw new IllegalArgumentException("UID must be positive");
      }

      if (grades.isEmpty()){
        throw new IllegalArgumentException("A client must have at least one grade");
      }

      if (emails.isEmpty() && phoneNumbers.isEmpty()) {
        throw new IllegalArgumentException("A client must have at least an email or a phoneNumber");
      }

      return new UberClient(this);
    }

  }

  public record DTO(String firstName, String lastName, double avgGrades, List<String> emails) {

    public static DTO of(UberClient uberClient, ToDoubleFunction<List<Integer>> avgFun, UnaryOperator<List<String>> emailsFormat) {
      return new DTO(uberClient.firstName, uberClient.lastName, avgFun.applyAsDouble(uberClient.grades), emailsFormat.apply(uberClient.emails));
    }
  }

  public String format(UberClientFormater formater) {
    return formater.format(DTO.of(this, AVG_FUN, EMAILS_ORG));
  }

  public String format(UberClientFormater formater, ToDoubleFunction<List<Integer>> avgFunc) {
    return formater.format(DTO.of(this, avgFunc, EMAILS_ORG));
  }

  public String format(UberClientFormater formater, ToDoubleFunction<List<Integer>> avgFunc, UnaryOperator<List<String>> emailsFormat) {
    return formater.format(DTO.of(this, avgFunc, emailsFormat));
  }

  public static Builder with() {
    return new Builder();
  }

   private final String firstName;
   private final String lastName;
   private final long uid;
   private final List<Integer> grades;
   private final List<String> emails;
   private final List<String> phoneNumbers;


   public UberClient(Builder builder) {
     firstName = builder.firstName;
     lastName = builder.lastName;
     uid = builder.uid;
     grades = List.copyOf(builder.grades);
     emails = List.copyOf(builder.emails);
     phoneNumbers = List.copyOf(builder.phoneNumbers);
   }
   
   public static void main(String[] args) {
     var arnaud = UberClient.with()
             .firstName("Arnaud")
             .lastName("Carayol")
             .uid(1)
             .grades(List.of(1,2,5,2,5,1,1,1))
             .emails(List.of("arnaud.carayol@univ-eiffel.fr","arnaud.carayol@u-pem.fr"))
             .phoneNumbers(List.of("07070707070707"))
             .build();

     var youssef = UberClient.with()
             .firstName("Youssef")
             .lastName("Bergeron")
             .uid(Builder.RANDOM_LONG)
             .grade(5)
             .email("youssefbergeron@outlook.fr")
             .phoneNumbers(List.of())
             .build();

     ToDoubleFunction<List<Integer>> lat7AvgFunc = list -> list.stream().limit(7).mapToDouble(integer -> integer).average().orElseThrow(() -> new AssertionError("Client are meant to have at least one grade"));
     ToDoubleFunction<List<Integer>> lat5AvgFunc = list -> list.stream().limit(5).mapToDouble(integer -> integer).average().orElseThrow(() -> new AssertionError("Client are meant to have at least one grade"));

     UnaryOperator<List<String>> hideEmail = strings -> strings.stream().map(s -> {
       var split = s.split("@");
       return split[0].replaceAll("\\w", "*") + "@" + split[1];
     }).toList();

     System.out.println(arnaud.format(UberClientFormater.toHTML));
     System.out.println(arnaud.format(UberClientFormater.toHTMLSimple));
     System.out.println(arnaud.format(UberClientFormater.toHTML, lat7AvgFunc));
     System.out.println(arnaud.format(UberClientFormater.toHtmlWithEmails));
     System.out.println(arnaud.format(UberClientFormater.toHtmlWithEmails, lat5AvgFunc));
     System.out.println(arnaud.format(UberClientFormater.toHtmlWithEmails, lat5AvgFunc, hideEmail));

    }

}
