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
		if (this.lignes != M.lignes || this.colonnes != M.colonnes) {
			throw new IllegalArgumentException("Les dimensions des matrices ne correspondent pas.");
		}
		long[][] somme = new long[this.lignes][this.colonnes];
		for (int i = 0; i < this.lignes; i++) {
			for (int j = 0; j < this.colonnes; j++) {
				somme[i][j] = this.elements[i][j] + M.elements[i][j];
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
					prod[i][j] = prod[i][j].plus(elements[i][k].times(M.elements[k][j]));
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
				trans[j][i] = elements[i][j];
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
		Rational[] temp = elements[i];
		elements[i] = elements[j];
		elements[j] = temp;
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
			elements[j][k] = elements[j][k].plus(elements[i][k].times(a));
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
			elements[i][k] = elements[i][k].times(a);
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
		/** Remplir ici le code manquant */
		/** On suggère très fortement d'utiliser l'algorithme du pivot de Gauss */
		/** 
                 *  S'il s'avère que la matrice this n'a pas d'inverse :
		 *    throw new ArithmeticException("Division par zéro");
                 */
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
		if (m != b.m) {
			throw new IllegalArgumentException("Dimensions incorrectes");
		}
		Rational[][] a = new Rational[n][1];
		/** Remplir ici le code manquant */
		/** 
                 *  S'il s'avère que l'équation n'a pas de solution :
		 *    throw new ArithmeticException("Pas de solution");
		 *  Si elle a plusieurs solutions :
		 *    on peut renvoyer n'importe quelle solution.
                 */
		return new Matrice(a);
	}

	@Override
	public String toString() {
		return Arrays.deepToString(coeff);
	}

}
