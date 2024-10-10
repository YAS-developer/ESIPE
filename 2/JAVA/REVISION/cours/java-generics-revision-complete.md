# Fiche de révision : Génériques, méthodes statiques et casts en Java

## 1. Bases des génériques

- Introduits en Java 5 pour améliorer la sécurité du type et réduire les casts
- Syntaxe : `Class<T>`, `Method<T>`
- Exemple : `List<String>`, `Map<K,V>`

## 2. Limitations des génériques avec les méthodes statiques

- Variables de type non accessibles dans un contexte statique
- Exemple incorrect :
  ```java
  class Foo<E> {
      static E e; // Ne compile pas
      static E bar(E e) { return e; } // Ne compile pas
  }
  ```

## 3. Effacement de type (Type Erasure)

- Informations de type générique effacées à l'exécution
- Conséquences :
  - `instanceof T` impossible
  - `new T()` impossible
  - `new T[]` impossible

## 4. Casts dans les génériques

### Casts nécessaires
1. Implémentation de collections génériques :
   ```java
   public class GenericStack<E> {
       private E[] elements;
       @SuppressWarnings("unchecked")
       public GenericStack(int capacity) {
           elements = (E[]) new Object[capacity]; // Cast nécessaire
       }
   }
   ```

2. Pattern Class Token :
   ```java
   public static <T> T deserialize(byte[] data, Class<T> clazz) {
       Object obj = deserializeObject(data);
       return clazz.cast(obj); // Cast nécessaire et sûr
   }
   ```

### Mauvais casts (à éviter)
1. Cast non vérifié sur collection de type brut :
   ```java
   List list = getUntypedList();
   List<String> stringList = (List<String>) list; // Mauvais cast
   ```

2. Contournement du système de type :
   ```java
   public <T> T unsafeConvert(Object obj) {
       return (T) obj; // Très mauvaise pratique
   }
   ```

## 5. Utilisation de @SuppressWarnings

- Supprime les avertissements du compilateur pour les casts non sûrs
- À utiliser avec précaution :
  ```java
  @SuppressWarnings("unchecked")
  E[] elements = (E[]) new Object[16];
  ```

## 6. Pattern Class Token

- Solution sûre pour gérer les types à l'exécution :
  ```java
  static <T> T safeCast(Object o, Class<T> type) {
      return type.cast(o);
  }
  ```

## 7. Bonnes pratiques

1. Utilisation de bornes génériques :
   ```java
   public static <T extends Comparable<? super T>> T max(List<? extends T> list) {
       // ...
   }
   ```

2. Class Token pour création d'instances :
   ```java
   public static <T> T createInstance(Class<T> clazz) throws Exception {
       return clazz.getDeclaredConstructor().newInstance();
   }
   ```

3. Wildcards pour plus de flexibilité :
   ```java
   public static void printAll(List<?> list) {
       for (Object item : list) {
           System.out.println(item);
       }
   }
   ```

4. PECS (Producer Extends, Consumer Super) :
   ```java
   public static <T> void copy(List<? super T> dest, List<? extends T> src) {
       for (T item : src) {
           dest.add(item);
       }
   }
   ```

## 8. Points clés à retenir

- Utilisez les génériques pour la sécurité des types à la compilation
- Évitez les casts non nécessaires
- Isolez et documentez les casts nécessaires
- Préférez les méthodes génériques et wildcards aux casts manuels
- Utilisez le pattern Class Token pour les opérations dépendantes du type à l'exécution
- Appliquez PECS pour maximiser la flexibilité des API génériques
- Les génériques avec méthodes statiques peuvent nécessiter des casts dans certains cas particuliers
- La sécurité de type doit toujours être une priorité

