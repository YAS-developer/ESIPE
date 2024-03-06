import java.util.Scanner;

public class Calc {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int value = getIntFromUser(scanner);
        int value2 = getIntFromUser(scanner);

        if (value2 > value) {
            System.out.println(
                "Somme: " + (value + value2) + "\n" +
                "Différence: " + (value2 - value) + "\n" +
                "Produit: " + (value * value2) + "\n" +
                "Quotient: " + (value / value2) + "\n" +
                "Reste: " + (value % value2) + "\n"
            );
        } else {
            System.out.println(
                "Somme: " + (value + value2) + "\n" +
                "Différence: " + (value - value2) + "\n" +
                "Produit: " + (value * value2) + "\n" +
                "Quotient: " + (value / value2) + "\n" +
                "Reste: " + (value % value2) + "\n"
            );
        }
    }

    private static int getIntFromUser(Scanner scanner) {
        int number;
        while (true) {
            System.out.println("Veuillez entrer un nombre :");
            String input = scanner.nextLine();
            try {
                number = Integer.parseInt(input);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Erreur : vous devez entrer un nombre valide.");
            }
        }
        return number;
    }
}
