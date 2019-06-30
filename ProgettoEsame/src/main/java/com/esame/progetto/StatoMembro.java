package com.esame.progetto;

public class StatoMembro {
	private String memberState;
	private String year;
	private String fund;
	private int totalPaid;
	private int advance;
	private int interim;
	private int absorption;
	
	public StatoMembro(String memberState, String year, String fund, int totalPaid, int advance,
			int interim, int absorption) {
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

	public int getTotalPaid() {
		return totalPaid;
	}

	public int getAdvance() {
		return advance;
	}

	public int getInterim() {
		return interim;
	}

	public int getAbsorption() {
		return absorption;
	}
	
}
