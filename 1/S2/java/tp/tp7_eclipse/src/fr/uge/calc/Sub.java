package fr.uge.calc;

public record Sub(Expr left,  Expr right) implements BinOp {
   
		@Override
		public Expr left() {
			// TODO Auto-generated method stub
			return left;
		}
	
		@Override
		public Expr right() {
			// TODO Auto-generated method stub
			return right;
		}

		
		 @Override
	    public int eval() {
	        return left.eval() - right.eval();
	    }

    

    @Override
    public String toString() {
        return operationToString("-");
    }
}
