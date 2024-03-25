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
        throw new IllegalArgumentException("Matrice doit être carrée pour inverser.");
		}

		Matrice inverse = identity(); // La matrice qui deviendra l'inverse
		Matrice clone = clone(); // Copie de la matrice d'origine pour les opérations

		// Transformation en forme échelonnée
		for (int i = 0; i < n; i++) {
			// Trouver le pivot
			if (clone.coeff[i][i].equals(Rational.ZERO)) {
				boolean changed = false;
				for (int j = i + 1; j < n; j++) {
					if (!clone.coeff[j][i].equals(Rational.ZERO)) {
						clone.swapRows(i, j);
						inverse.swapRows(i, j);
						changed = true;
						break;
					}
				}
				if (!changed) {
					throw new ArithmeticException("Matrice non inversible");
				}
			}

			// Normaliser le pivot
			Rational pivot = clone.coeff[i][i];
			for (int j = 0; j < n; j++) {
				clone.coeff[i][j] = clone.coeff[i][j].divide(pivot);
				inverse.coeff[i][j] = inverse.coeff[i][j].divide(pivot);
			}

			// Annuler les autres éléments de la colonne
			for (int j = 0; j < n; j++) {
				if (j != i && !clone.coeff[j][i].equals(Rational.ZERO)) {
					Rational factor = clone.coeff[j][i].minus();
					for (int k = 0; k < n; k++) {
						clone.coeff[j][k] = clone.coeff[j][k].plus(clone.coeff[i][k].times(factor));
						inverse.coeff[j][k] = inverse.coeff[j][k].plus(inverse.coeff[i][k].times(factor));
					}
				}
			}
		}

    	return inverse;
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
		if (this.m != b.n) {
			throw new IllegalArgumentException("Dimensions incorrectes pour résoudre le système.");
		}
		// Création de la matrice augmentée
		Rational[][] augmente = new Rational[this.n][this.m + 1];
		for (int i = 0; i < this.n; i++) {
			System.arraycopy(this.coeff[i], 0, augmente[i], 0, this.m);
			augmente[i][this.m] = b.coeff[i][0];
		}

		// Application de la méthode de Gauss-Jordan
		for (int i = 0; i < this.n; i++) {
			// Recherche du pivot, ici on vérifie simplement s'il n'est pas nul
			if (augmente[i][i].equals(Rational.ZERO)) {
				boolean found = false;
				for (int k = i + 1; k < this.n; k++) {
					if (!augmente[k][i].equals(Rational.ZERO)) {
						Rational[] temp = augmente[i];
						augmente[i] = augmente[k];
						augmente[k] = temp;
						found = true;
						break;
					}
				}
				if (!found) {
					throw new ArithmeticException("Pas de solution unique ou système indéterminé.");
				}
			}

			// Normalisation de la ligne pivot pour que le pivot soit 1
			Rational pivot = augmente[i][i];
			for (int j = i; j < this.m + 1; j++) {
				augmente[i][j] = augmente[i][j].divide(pivot);
			}

			// Élimination des autres éléments de la colonne i
			for (int k = 0; k < this.n; k++) {
				if (k != i) {
					Rational factor = augmente[k][i];
					for (int j = i; j < this.m + 1; j++) {
						augmente[k][j] = augmente[k][j].minus(augmente[i][j].times(factor));
					}
				}
			}
		}

		// Extraction de la solution
		Rational[][] solution = new Rational[this.n][1];
		for (int i = 0; i < this.n; i++) {
			solution[i][0] = augmente[i][this.m];
		}

		return new Matrice(solution);
	}

	@Override
	public String toString() {
		return Arrays.deepToString(coeff);
	}

}
