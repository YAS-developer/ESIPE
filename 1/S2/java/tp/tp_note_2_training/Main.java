import java.util.ArrayList;
import java.util.Comparator;
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


        var ageSortedUsers = userList.stream()
                                  .sorted(Comparator.comparingInt(User::age))
                                  .collect(Collectors.toList());
        ageSortedUsers.forEach(System.out::println);


        // var solvableUsers = userList.stream()
        //                             .filter(User::solvable)
        //                             .toList();
        // solvableUsers.forEach(System.out::println);     
        
        
        



    }

}   


// map : Transforme chaque élément du stream.

// stream.map(e -> e.toString());


// mapToInt / mapToDouble : Transforme chaque élément en int / double.


// stream.mapToInt(e -> e.length());


// filter : Garde les éléments qui satisfont un prédicat.

// stream.filter(e -> e.isActive());




// stream.sorted();



// Comparator.comparing(Account::name);


// reduce : Combine les éléments pour produire une seule valeur.

// stream.reduce(0, Integer::sum);



// collect : Accumule les éléments dans une collection ou un autre type de résultat.



// groupingBy : Regroupe les éléments selon une clé.

// counting : Compte le nombre d'éléments.

// Collectors.counting();


// averagingDouble : Calcule la moyenne des éléments.

// Collectors.averagingDouble(e -> e.getValue());






/*
 * Méthodes des Streams
1. Transformation et Filtrage

    map(Function<? super T, ? extends R> mapper) : Transforme chaque élément du stream.

    java

stream.map(e -> e.toString());

mapToInt(ToIntFunction<? super T> mapper) / mapToDouble(ToDoubleFunction<? super T> mapper) / mapToLong(ToLongFunction<? super T> mapper) : Transforme chaque élément en int, double ou long.

java

stream.mapToInt(e -> e.length());

filter(Predicate<? super T> predicate) : Garde les éléments qui satisfont un prédicat.

java

stream.filter(e -> e.isActive());

distinct() : Élimine les doublons (basé sur equals).

java

    stream.distinct();

2. Tri et Limitation

    sorted() : Trie les éléments selon l'ordre naturel.

    java

stream.sorted();

sorted(Comparator<? super T> comparator) : Trie les éléments avec un comparateur.

java

stream.sorted(Comparator.comparing(e -> e.getName()));

limit(long maxSize) : Tronque le stream à une taille maximale.

java

stream.limit(10);

skip(long n) : Ignore les n premiers éléments.

java

    stream.skip(5);

3. Recherche et Correspondance

    findFirst() : Retourne un Optional décrivant le premier élément du stream.

    java

stream.findFirst();

findAny() : Retourne un Optional décrivant un élément du stream.

java

stream.findAny();

allMatch(Predicate<? super T> predicate) : Vérifie si tous les éléments correspondent au prédicat.

java

stream.allMatch(e -> e.isActive());

anyMatch(Predicate<? super T> predicate) : Vérifie si au moins un élément correspond au prédicat.

java

stream.anyMatch(e -> e.isActive());

noneMatch(Predicate<? super T> predicate) : Vérifie si aucun élément ne correspond au prédicat.

java

    stream.noneMatch(e -> e.isActive());

4. Agrégation

    count() : Retourne le nombre d'éléments dans le stream.

    java

stream.count();

max(Comparator<? super T> comparator) : Retourne le maximum selon le comparateur.

java

stream.max(Comparator.comparing(e -> e.getValue()));

min(Comparator<? super T> comparator) : Retourne le minimum selon le comparateur.

java

stream.min(Comparator.comparing(e -> e.getValue()));

reduce(BinaryOperator<T> accumulator) : Effectue une réduction sur les éléments du stream.

java

    stream.reduce((a, b) -> a + b);

5. Collecte

    collect(Collector<? super T, A, R> collector) : Accumule les éléments dans une collection ou un autre type de résultat.

    java

stream.collect(Collectors.toList());

collect(Supplier<R> supplier, BiConsumer<R, ? super T> accumulator, BiConsumer<R, R> combiner) : Effectue une réduction mutable.

java

    stream.collect(ArrayList::new, List::add, List::addAll);

6. Flux Infini et Construction

    generate(Supplier<T> s) : Retourne un stream infini généré par le fournisseur.

    java

Stream.generate(Math::random);

iterate(T seed, UnaryOperator<T> f) : Retourne un stream infini produit par l'application itérative d'une fonction.

java

Stream.iterate(0, n -> n + 1);

builder() : Retourne un builder pour créer un stream.

java

Stream.builder().add(1).add(2).build();

concat(Stream<? extends T> a, Stream<? extends T> b) : Concatène deux streams.

java

Stream.concat(stream1, stream2);

empty() : Retourne un stream vide.

java

Stream.empty();

of(T... values) : Retourne un stream contenant les valeurs spécifiées.

java

    Stream.of(1, 2, 3);

7. Autres Utilitaires

    forEach(Consumer<? super T> action) : Exécute une action pour chaque élément.

    java

stream.forEach(System.out::println);

forEachOrdered(Consumer<? super T> action) : Exécute une action pour chaque élément, en respectant l'ordre de rencontre.

java

stream.forEachOrdered(System.out::println);

peek(Consumer<? super T> action) : Exécute une action pour chaque élément et retourne un nouveau stream.

java

stream.peek(System.out::println);

flatMap(Function<? super T, ? extends Stream<? extends R>> mapper) : Transforme chaque élément en un stream, puis aplatit ces streams en un seul.

java

stream.flatMap(e -> Stream.of(e.getSubElements()));

flatMapToDouble(Function<? super T, ? extends DoubleStream> mapper) / flatMapToInt(Function<? super T, ? extends IntStream> mapper) / flatMapToLong(Function<? super T, ? extends LongStream> mapper) : Transforme chaque élément en un DoubleStream, IntStream ou LongStream, puis aplatit ces streams en un seul.

java

stream.flatMapToInt(e -> IntStream.of(e.getNumbers()));

toArray() : Retourne un tableau contenant les éléments du stream.

java

Object[] array = stream.toArray();

toArray(IntFunction<A[]> generator) : Retourne un tableau, dont le type est spécifié par le générateur, contenant les éléments du stream.

java

    String[] array = stream.toArray(String[]::new);

Utilisation des Collectors
Collectors

    toList() : Collecte les éléments dans une liste.

    java

stream.collect(Collectors.toList());

toSet() : Collecte les éléments dans un ensemble.

java

stream.collect(Collectors.toSet());

toMap(Function<? super T, ? extends K> keyMapper, Function<? super T, ? extends U> valueMapper) : Collecte les éléments dans une carte.

java

stream.collect(Collectors.toMap(e -> e.getId(), e -> e));

joining(CharSequence delimiter) : Concatène les éléments sous forme de chaîne de caractères.

java

stream.collect(Collectors.joining(", "));

groupingBy(Function<? super T, ? extends K> classifier) : Regroupe les éléments selon une clé.

java

stream.collect(Collectors.groupingBy(e -> e.getType()));

counting() : Compte le nombre d'éléments.

java

stream.collect(Collectors.counting());

averagingDouble(ToDoubleFunction<? super T> mapper) : Calcule la moyenne des éléments.

java

    stream.collect(Collectors.averagingDouble(e -> e.getValue()));

Avec ces explications, vous disposez d'une vue d'ensemble complète des méthodes et des collecteurs utilisés avec les streams en Java, vous permettant de manipuler les collections de manière efficace et élégante.

 * 
 * 
*/