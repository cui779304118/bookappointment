package com.cw.bookappointment.entity;

public class Book {

	private Integer bookId;
	private String name;
	private String introdction;
	private Integer number;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public String getIntrodction() {
		return introdction;
	}
	public void setIntrodction(String introdction) {
		this.introdction = introdction;
	}
	public Integer getBookId() {
		return bookId;
	}
	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}
	public String toString(){
		return "bookId:" + bookId + " name:" + name + " introdction:" + introdction + " number:" + number;
	}
}
