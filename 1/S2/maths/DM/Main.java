public class Main {

	public static void main(String[] args) {
		// Matrice A = new Matrice(new long[][] { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 13 } });
		// Matrice B = A.times(A.transpose());
		// Matrice C = B.inverse();
		// System.out.println(C);
		// Matrice D = A.transpose().times(A);
		// try {
		// 	Matrice E = D.inverse();
		// 	System.out.println(E);
		// } catch (ArithmeticException e) {
		// 	System.out.println("D n'a pas d'inverse");
		// }

		// Vecteur b (différences pour atteindre l'investissement équitable)
        Rational[][] differences = {
            {new Rational(30)}, // Alice doit recevoir 30€ pour atteindre 150€
            {new Rational(0)},  // Bob est déjà à 150€, il ne doit ni donner ni recevoir
            {new Rational(-30)} // Charlie doit donner 30€ pour atteindre 150€
        };
        Matrice b = new Matrice(differences);

        // La matrice A dans ce cas simple est juste l'identité, puisque les ajustements sont directs
        Rational[][] identite = {
            {new Rational(1), new Rational(0), new Rational(0)},
            {new Rational(0), new Rational(1), new Rational(0)},
            {new Rational(0), new Rational(0), new Rational(1)}
        };
        Matrice A = new Matrice(identite);

        // Utilisation de la méthode resoud pour obtenir le vecteur de solutions
        try {
            Matrice x = A.resoud(b);
            System.out.println("Solution (x):");
            System.out.println(x);
        } catch (ArithmeticException e) {
            System.out.println("Erreur: " + e.getMessage());
        }
	}
}
