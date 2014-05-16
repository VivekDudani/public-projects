package com.google.sharecompare.dataobject;

public class Company implements Comparable<Company> {

	private String name;
	private String month;
	private String year;
	private Float price;

	/**
	 * Constructor
	 * 
	 * @param name
	 * @param month
	 * @param year
	 * @param price
	 */
	public Company(String name, String month, String year, Float price) {
		this.name = name;
		this.month = month;
		this.year = year;
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	@Override
	public int compareTo(Company o) {
		return this.price.compareTo(o.getPrice());
	}

	@Override
	public String toString() {
		return "Company " + this.name + " - Max Price: " + this.price + " in " + this.month + "," + this.year;
	}

}
