import java.util.Objects;

public record User(String name, int age, double sold, boolean solvable) {
    public User(String name, int age, double sold) {
        this(
            Objects.requireNonNull(name, "Le nom ne peut pas être null."),
            validateAge(age),
            sold,
            sold >= 0
        );
    }

    private static int validateAge(int age) {
        if (age < 0) {
            throw new IllegalArgumentException("L'âge ne peut pas être négatif.");
        }
        return age;
    }

    @Override
    public String toString() {
        var solv = "solvable.";
        if (!solvable) {
            solv = "non solvable.";
        }

        return name() + " agee de " + age() + " est " + solv +" Solde: "+sold();
    }
}

