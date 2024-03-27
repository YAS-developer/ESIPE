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




		// Initialisation de la matrice A avec des données d'exemple
        long[][] donneesExperiences = {
            {1, 2, 3, 4},
            {2, 3, 4, 5},
            {3, 4, 5, 6}
        };
        Matrice A = new Matrice(donneesExperiences);

        // Calcul de B comme étant A * A^T pour représenter nos contraintes
        Matrice B = A.times(A.transpose());

        // Définition du vecteur b, représentant nos objectifs ou contraintes désirées
        Rational[] objectifs = {new Rational(1), new Rational(1), new Rational(1)};
        Rational[][] donneesObjectifs = new Rational[objectifs.length][1];
        for (int i = 0; i < objectifs.length; i++) {
            donneesObjectifs[i][0] = objectifs[i];
        }
        Matrice b = new Matrice(donneesObjectifs);

        // Résolution pour x dans Bx = b
        try {
            Matrice x = B.resoud(b);
            System.out.println("Vecteur de coefficients x :");
            System.out.println(x);
        } catch (ArithmeticException e) {
            System.out.println("Le système n'a pas de solution unique ou est indéterminé.");
        }
	}

}
