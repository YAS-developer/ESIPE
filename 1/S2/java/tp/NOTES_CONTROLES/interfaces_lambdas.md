Voici un résumé des différentes interfaces fonctionnelles couramment utilisées avec des lambdas dans l'API des flux (streams) en Java :

    Consumer<T>
        Prend en argument : Un objet de type T.
        Renvoie : Rien (void).
        Exemple : Consumer<String> printer = s -> System.out.println(s);

    Supplier<T>
        Prend en argument : Rien.
        Renvoie : Un objet de type T.
        Exemple : Supplier<Integer> randomSupplier = () -> new Random().nextInt();

    Function<T, R>
        Prend en argument : Un objet de type T.
        Renvoie : Un objet de type R.
        Exemple : Function<String, Integer> lengthFunction = s -> s.length();

    Predicate<T>
        Prend en argument : Un objet de type T.
        Renvoie : Un booléen (boolean).
        Exemple : Predicate<Integer> isEven = n -> n % 2 == 0;

    BiConsumer<T, U>
        Prend en argument : Deux objets de types T et U.
        Renvoie : Rien (void).
        Exemple : BiConsumer<String, Integer> printer = (s, i) -> System.out.println(s + i);

    BiFunction<T, U, R>
        Prend en argument : Deux objets de types T et U.
        Renvoie : Un objet de type R.
        Exemple : BiFunction<String, String, Integer> concatLength = (s1, s2) -> (s1 + s2).length();

    UnaryOperator<T>
        Prend en argument : Un objet de type T.
        Renvoie : Un objet de type T.
        Exemple : UnaryOperator<Integer> square = x -> x * x;

    BinaryOperator<T>
        Prend en argument : Deux objets de type T.
        Renvoie : Un objet de type T.
        Exemple : BinaryOperator<Integer> sum = (a, b) -> a + b;

Ces interfaces permettent d'utiliser des lambdas pour des opérations courantes sur des flux de données, offrant une manière concise et expressive de manipuler les collections en Java.