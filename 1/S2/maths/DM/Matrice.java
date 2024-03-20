import java.util.Arrays;
import java.util.Optional;

public class Matrice {

	private final int n; /* nombre de lignes */
	private final int m; /* nombre de colonnes */
	private final Rational[][] coeff; /* liste des coefficients */

	/**
	 * Création d'une matrice
	 * 
	 * @param coeff coefficients de la matrice
	 */
	public Matrice(Rational[][] coeff) {
		n = coeff.length;
		m = coeff[0].length;
		this.coeff = coeff;
	}

	/**
	 * Création d'une matrice
	 * 
	 * @param coeff coefficients de la matrice, donnés comme long
	 */
	public Matrice(long[][] coeff) {
		n = coeff.length;
		m = coeff[0].length;
		this.coeff = new Rational[n][m];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				this.coeff[i][j] = new Rational(coeff[i][j]);
			}
		}
	}

	/**
	 * Calcul de la somme matricielle this + M (si les dimensions de this et M
	 * l'autorisent)
	 * 
	 * @param M matrice à ajouter : tableau n x m
	 * @return somme this + M : tableau n x m
	 */
	public Matrice plus(Matrice M) {
		if (this.n != M.n || this.m != M.m) {
        	throw new IllegalArgumentException("Les dimensions des matrices ne correspondent pas.");
        }

        Rational[][] somme = new Rational[this.n][this.m];
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.m; j++) {
                somme[i][j] = this.coeff[i][j].plus(M.coeff[i][j]);
            }
        }
        return new Matrice(somme);
	}

	/**
	 * Calcul du produit matriciel this M (si les dimensions de this et M
	 * l'autorisent)
	 * 
	 * @param M matrice à multiplier : tableau m x p
	 * @return produit this M : tableau n x p
	 */
	public Matrice times(Matrice M) {
		if (m != M.n) {
			throw new IllegalArgumentException("Dimensions incorrectes pour la multiplication");
		}
		int p = M.m;

		Rational[][] prod = new Rational[n][p];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < p; j++) {
				prod[i][j] = Rational.ZERO; // Initialiser chaque élément du produit à 0 (rationnel)
				for (int k = 0; k < m; k++) {
					// Accumuler la somme des produits des éléments correspondants
					prod[i][j] = prod[i][j].plus(coeff[i][k].times(M.coeff[k][j]));
				}
			}
		}
		return new Matrice(prod);
	}


	/**
	 * Calcul de la transposée de this
	 * 
	 * @return transposée de this : tableau m x n
	 */
	public Matrice transpose() {
		Rational[][] trans = new Rational[m][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				trans[j][i] = coeff[i][j];
			}
		}
		return new Matrice(trans);
	}


	/**
	 * Échange les lignes i et j de la matrice
	 * 
	 * @param i première ligne à échanger
	 * @param j deuxième ligne à échanger
	 */
	private void swapRows(int i, int j) {
		if (i < 0 || i >= n || j < 0 || j >= n) {
        	throw new IllegalArgumentException("Indices de ligne hors limites");
		}
		Rational[] temp = coeff[i];
		coeff[i] = coeff[j];
		coeff[j] = temp;
	}

	/**
	 * Ajoute a fois la ligne i de this à sa ligne j
	 * 
	 * @param i ligne à ajouter (multiplée par a)
	 * @param j ligne à laquelle on ajoute a fois la ligne j
	 * @param a scalaire par lequel on multiplie la ligne i quand on l'ajoute
	 */
	public void transvection(int i, int j, Rational a) {
		if (i < 0 || i >= n || j < 0 || j >= n) {
			throw new IndexOutOfBoundsException("Indices i ou j hors limites");
		}
		for (int k = 0; k < m; k++) {
			// Pour chaque colonne k, ajouter à l'élément de la ligne j, 'a' fois l'élément correspondant de la ligne i
			coeff[j][k] = coeff[j][k].plus(coeff[i][k].times(a));
		}
	}


	/**
	 * Mutiplie par a la ligne i de this
	 * 
	 * @param i ligne à multiplier par a
	 * @param a scalaire par lequel on multiplie la ligne i
	 */
	public void multiplyRow(int i, Rational a) {
		if (i < 0 || i >= n) {
			throw new IndexOutOfBoundsException("L'indice i est hors limites");
		}
		for (int k = 0; k < m; k++) {
			// Multiplier chaque élément de la ligne i par 'a'
			coeff[i][k] = coeff[i][k].times(a);
		}
	}


	/**
	 * Calcul de la matrice identité de mêmes dimensions que this (si les dimensions
	 * de this l'autorisent)
	 * 
	 * @return matrice identité : tableau n x n
	 */
	public Matrice identity() {
		if (m != n) {
			throw new IllegalArgumentException("Dimensions incorrectes");
		}
		Rational[][] id = new Rational[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (i == j) {
					id[i][j] = Rational.ONE;
				} else {
					id[i][j] = Rational.ZERO;
				}
			}
		}
		return new Matrice(id);
	}

	/**
	 * Calcul d'une copie de this
	 * 
	 * @return copie de this : tableau n x m
	 */
	public Matrice clone() {
		Rational[][] clone = new Rational[n][m];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				clone[i][j] = coeff[i][j];
			}
		}
		return new Matrice(clone);
	}

	/**
	 * Calcul de l'inverse de this
	 * 
	 * @return inverse de this : tableau n x n
	 */
	public Matrice inverse() {
		if (m != n) {
        	throw new IllegalArgumentException("Dimensions incorrectes");	
		}
		Matrice clone = clone();
		Matrice id = identity();

		// Appliquer l'algorithme du pivot de Gauss à la copie de la matrice d'origine

		// Assurez-vous que chaque ligne de la matrice échelonnée réduite a un élément non nul sur sa diagonale
		// Sinon, lancer une exception
		for (int i = 0; i < n; i++) {
			if (clone.coeff[i][i].equals(Rational.ZERO)) {
				throw new ArithmeticException("Matrice non inversible");
			}
		}

		// Utiliser les opérations de ligne pour transformer la matrice d'identité en l'inverse de la matrice d'origine

		// Retourner la matrice inverse
		return id;
	}

	/**
	 * Résoud une équation linéaire matricielle
	 * 
	 * @param this est une matrice m x n
	 * @param b vecteur m x 1 que l'on veut obtenir
	 * 
	 * @return vecteur colonne a tel que this * a = b : tableau n x 1
	 */
	public Matrice resoud(Matrice b) {
		if (m != b.n) {
			throw new IllegalArgumentException("Dimensions incorrectes");
		}
		Rational[][] a = new Rational[n][1];
		Matrice augmente = new Matrice(new Rational[n][m + 1]); // Matrice augmentée [this | b]

		// Remplir la matrice augmentée
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				augmente.coeff[i][j] = coeff[i][j];
			}
			augmente.coeff[i][m] = b.coeff[i][0]; // La colonne supplémentaire contient les valeurs de b
		}

		// Appliquer l'algorithme de Gauss-Jordan à la matrice augmentée

		// Vérifiez s'il y a une solution unique en examinant la forme échelonnée réduite
		for (int i = 0; i < n; i++) {
			boolean ligneNulle = true;
			for (int j = 0; j < m; j++) {
				if (!augmente.coeff[i][j].equals(Rational.ZERO)) {
					ligneNulle = false;
					break;
				}
			}
			if (ligneNulle && !augmente.coeff[i][m].equals(Rational.ZERO)) {
				throw new ArithmeticException("Pas de solution");
			}
		}

		// Extrayez les solutions de la matrice résultante
		for (int i = 0; i < n; i++) {
			a[i][0] = augmente.coeff[i][m];
		}

		return new Matrice(a);
	}

	@Override
	public String toString() {
		return Arrays.deepToString(coeff);
	}

}
