
public class Rational {
	private final long p; /* numérateur */
	private final long q; /* dénominateur */
	public final static Rational ZERO = new Rational(0);
	public final static Rational ONE = new Rational(1);

	/**
	 * Calcul du PGCD de a et b (le résultat prend le signe de b)
	 * 
	 * @param a
	 * @param b
	 * @return plus grand diviseur commun à a et à b
	 */
	private static long PGCD(long a, long b) {
		if (a < 0) {
			return PGCD(-a, b);
		} else if (b < 0) {
			return -PGCD(a, -b);
		} else if (a < b) {
			return PGCD(b, a);
		} else if (b == 0) {
			return a;
		} else {
			return PGCD(b, a % b);
		}
	}

	/**
	 * Création du rationnel a/b (sous forme irréductible)
	 * 
	 * @param a numérateur
	 * @param b dénominateur
	 */
	private Rational(long a, long b) {
		long d = PGCD(a, b);
		p = a / d;
		q = b / d;
	}

	/**
	 * Création du rationnel n
	 * 
	 * @param n numérateur
	 */
	public Rational(long n) {
		this(n, 1);
	}

	@Override
	public String toString() {
		return p + ((q == 1) ? "" : "/" + q);
	}

	/**
	 * Calcul du rationnel this + r
	 * 
	 * @param r rationnel à ajouter
	 * @return somme this + r
	 */
	public Rational plus(Rational r) {
		return new Rational(p * r.q + q * r.p, q * r.q);
	}

	/**
	 * Calcul du rationnel - r
	 * 
	 * @return opposé -this
	 */
	public Rational minus() {
		return new Rational(-p, q);
	}

	/**
	 * Calcul du rationnel this - r
	 * 
	 * @param r rationnel à soustraire
	 * @return somme this - r
	 */
	public Rational minus(Rational r) {
		return new Rational(p * r.q - q * r.p, q * r.q);
	}

	/**
	 * Calcul du rationnel this * r
	 * 
	 * @param r rationnel à multiplier
	 * @return somme this * r
	 */
	public Rational times(Rational r) {
		return new Rational(p * r.p, q * r.q);
	}

	/**
	 * Calcul du rationnel 1 / this (si this est non nul)
	 * 
	 * @return inverse 1 / this
	 */
	public Rational inverse() {
		if (p == 0) {
			throw new ArithmeticException("Division par zéro");
		}
		return new Rational(q, p);
	}

	/**
	 * Calcul du rationnel this / r (si r est non nul)
	 * 
	 * @param r rationnel par lequel on divise this
	 * @return rationnel this / r
	 */
	public Rational divide(Rational r) {
		if (r.p == 0) {
			throw new ArithmeticException("Division par zéro");
		}
		return new Rational(p * r.q, q * r.p);
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Rational) {
			Rational r = (Rational) o;
			return p == r.p && q == r.q;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return 7373 * Long.hashCode(p) + 65537 * Long.hashCode(q);
	}

}
