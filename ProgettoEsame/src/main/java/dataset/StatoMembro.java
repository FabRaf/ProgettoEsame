package dataset;

/**
 * 
 * @author Fabio Raffaeli
 *
 */

public class StatoMembro {
	private String memberState;
	private String year;
	private String fund;
	private double totalPaid;
	private double advance;
	private double interim;
	private double absorption;
	
	/**
	 * Inizializza i campi ai valori che vengono passati come parametri
	 * 
	 * @param memberState campo Member State del dataset
	 * @param year campo Year del dataset
	 * @param fund campo Fund del dataset
	 * @param totalPaid campo Total Paid del dataset
	 * @param advance campo Advance del dataset
	 * @param interim campo Interim del dataset
	 * @param absorption campo Absorption del dataset
	 */
	public StatoMembro(String memberState, String year, String fund, double totalPaid, double advance, double interim, double absorption) {
		this.memberState = memberState;
		this.year = year;
		this.fund = fund;
		this.totalPaid = totalPaid;
		this.advance = advance;
		this.interim = interim;
		this.absorption = absorption;
	}
	
	/**
	 * 
	 * @return il campo Member State
	 */
	public String getMemberState() {
		return memberState;
	}
	/**
	 * 
	 * @return il campo Year
	 */
	public String getYear() {
		return year;
	}

	/**
	 * 
	 * @return il campo Fund
	 */
	public String getFund() {
		return fund;
	}
	
	/**
	 * 
	 * @return il campo Total Paid
	 */
	public double getTotalPaid() {
		return totalPaid;
	}
	
	/**
	 * 
	 * @return il campo Advance
	 */
	public double getAdvance() {
		return advance;
	}
	
	/**
	 * 
	 * @return il campo Interim
	 */
	public double getInterim() {
		return interim;
	}
	
	/**
	 * 
	 * @return il campo Absorption
	 */
	public double getAbsorption() {
		return absorption;
	}
}

