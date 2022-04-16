package main.G1HRPS;

/**
 * Custom class to implement a pair of object.
 * Provides basic functions the operate a pair of object.
 */
public class Pair<A, B> {
	/**
	 * Static function to construct new Pair.
	 * 
	 * @param <P> Class of first object.
	 * @param <Q> Class of second object.
	 * @param p First object.
	 * @param q Second object.
	 * @return a new Pair with provided object types.
	 */
	public static <P, Q> Pair<P, Q> makePair(P p, Q q) {
		return new Pair<P, Q>(p, q);
	}
	
	/**
	 * First object to be stored.
	 */
	public final A a;
	
	/**
	 * Second object to be stored.
	 */
	public final B b;

	/**
	 * Constructor for Pair object.
	 * 
	 * @param a First object.
	 * @param b Second object.
	 */
	public Pair(A a, B b) {
		this.a = a;
		this.b = b;
	}

	/**
	 * Calculates hash value.
	 * 
	 * @return Calculated hash value.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((a == null) ? 0 : a.hashCode());
		result = prime * result + ((b == null) ? 0 : b.hashCode());
		return result;
	}

	/**
	 * Compares self with given object.
	 * 
	 * @param obj object to be compared with.
	 * @return true if equals, else false.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		@SuppressWarnings("rawtypes")
		Pair other = (Pair) obj;
		if (a == null) {
			if (other.a != null) {
				return false;
			}
		} else if (!a.equals(other.a)) {
			return false;
		}
		if (b == null) {
			if (other.b != null) {
				return false;
			}
		} else if (!b.equals(other.b)) {
			return false;
		}
		return true;
	}

	/**
	 * Checks if stored pair of objects is instance of another pair of objects.
	 * @param classA Object to be compared with A.
	 * @param classB Object to be compared with B.
	 * @return true if both are same, else false.
	 */
	public boolean isInstance(Class<?> classA, Class<?> classB) {
		return classA.isInstance(a) && classB.isInstance(b);
	}

	/**
	 * Allow casting of unknown Pair to a known Pair.
	 * 
	 * @param <P>		Class of object for A.
	 * @param <Q>		Class of object for B.
	 * @param pair		The unknown pair to be casted.
	 * @param pClass	Object for Pair.A to be casted to.
	 * @param qClass	Object for Pair.B to be casted to.
	 * @return A Pair is casted to known classes.
	 */
	@SuppressWarnings("unchecked")
	public static <P, Q> Pair<P, Q> cast(Pair<?, ?> pair, Class<P> pClass, Class<Q> qClass) {

		if (pair.isInstance(pClass, qClass)) {
			return (Pair<P, Q>) pair;
		}

		throw new ClassCastException();

	}

}