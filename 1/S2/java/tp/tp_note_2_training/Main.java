import java.util.ArrayList;
import java.util.stream.Collectors;

public class Main{

    public static void main(String[] args){
        var user1 = new User("toto", 10, 10000);
        var user2 = new User("titi", 20, -5000);
        
        var userList = new ArrayList<User>();

        // Ajout de plusieurs utilisateurs à la liste
        userList.add(new User("Alice", 30, 1000.0));
        userList.add(new User("Bob", 25, -100.0));
        userList.add(new User("Charlie", 22, 500.0));
        userList.add(new User("Diana", 35, 0.0));
        userList.add(new User("Edward", 28, 750.0));
        userList.add(new User("Fiona", 40, -50.0));
        userList.add(new User("George", 18, 200.0));
        userList.add(new User("Hannah", 27, 300.0));
        userList.add(new User("Ivan", 33, -150.0));
        userList.add(new User("Jane", 45, 1200.0));




        // var youngUser = userList.stream()
        //                         .filter(user -> user.age() < 28)
        //                         .toList();

        
        // for (var user : youngUser) {
        //     System.out.println(user);
        // }


        // var youngUserName = userList.stream()
        //                             .collect(Collectors.joining(", "));

        // System.out.println(youngUserName);


        //  var soldSortedUsers = userList.stream()
        //                           .sorted((u1, u2) -> Double.compare(u1.sold(), u2.sold()))
        //                           .collect(Collectors.toList());
        // soldSortedUsers.forEach(System.out::println);


        // var ageSortedUsers = userList.stream()
        //                           .sorted((u1, u2) -> Integer.compare(u1.age(), u2.age()))
        //                           .collect(Collectors.toList());
        // ageSortedUsers.forEach(System.out::println);


        var solvableUsers = userList.stream()
                                    .filter(User::solvable)
                                    .toList();
        solvableUsers.forEach(System.out::println);                



    }

}   

