package main.G1HRPS;

/**
 * Enumset for the genders available.
 * 
 * @author Kim Sang Hyeon
 *
 */
public enum Gender {
	Female(0), Male(1), Other(2);

	private final int value;

	private Gender(int value) {
		this.value = value;
	}

	public int GetValue() {
		return value;
	}
}