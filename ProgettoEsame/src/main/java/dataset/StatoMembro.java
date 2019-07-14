package dataset;

public class StatoMembro {
	private String memberState;
	private String year;
	private String fund;
	private double totalPaid;
	private double advance;
	private double interim;
	private double absorption;
	
	public StatoMembro(String memberState, String year, String fund, double totalPaid, double advance, double interim, double absorption) {
		this.memberState = memberState;
		this.year = year;
		this.fund = fund;
		this.totalPaid = totalPaid;
		this.advance = advance;
		this.interim = interim;
		this.absorption = absorption;
	}

	public String getMemberState() {
		return memberState;
	}

	public String getYear() {
		return year;
	}

	public String getFund() {
		return fund;
	}

	public double getTotalPaid() {
		return totalPaid;
	}

	public double getAdvance() {
		return advance;
	}

	public double getInterim() {
		return interim;
	}

	public double getAbsorption() {
		return absorption;
	}
}

