package edu.umsl.quiz.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by genebrowder on 1/28/16.
 */
@Entity
@Table(name="BOOKSTORE")
public class BookStore {
    //private Book book;
    
	private static final long serialVersionUID = 336542947443965225L;
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	@Id
	private long id;
	
	@Column(name="PHONE")
	private String phone;
    
	@Column(name="WEBSITE")
	private String website;
    
	@Column(name="NAME")
	private String name;
    
	@Column(name="ADDRESS")
	private String address;
    //private Employee employee;

//    public Book getBook() {
//        return book;
//    }
//
//    public void setBook(Book book) {
//        this.book = book;
//    }
	
	public BookStore(String name, String address, String phone, String website){
		this.name=name;
		this.address = address;
		this.phone=phone;
		this.website=website;
	}

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

//    public Employee getEmployee() {
//        return employee;
//    }
//
//    public void setEmployee(Employee employee) {
//        this.employee = employee;
//    }







}
