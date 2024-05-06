package fr.uge.calc;

// Interface BinOp qui étend Expr
public sealed interface BinOp extends Expr permits Add, Sub, Mul {
    // Méthode pour obtenir le premier opérande
		public Expr left();

    // Méthode pour obtenir le deuxième opérande
    public Expr right();

    @Override
    public int eval();
    
    // Méthode utilitaire par défaut pour la représentation sous forme de chaîne
    default String operationToString(String operator) {
        var sb = new StringBuilder();
        return sb.append("(").append(left()).append(" ").append(operator).append(" ").append(right()).append(")").toString();
    }
}
