package com.ecommerce.entity;

public class Product {
	private int id;
	private String name;
	private double price;
	private String description;
	private Category category;
	private Account account;
	private boolean isDeleted;
	private int amount;
	private byte[] image;
	private String base64Image;
	
	public Product() {
		
	}
	
	public Product(int id, String name, String base64Image, double price, String description, Category category, Account account, boolean isDeleted, int amount) {
		this.id = id;
		this.name = name;
		this.base64Image = base64Image;
		this.price = price;
		this.description = description;
		this.category = category;
		this.account = account;
		this.isDeleted = isDeleted;
		this.account = amount;
	}
}
