package edu.umsl.quiz.manageBookstore;

import edu.umsl.quiz.dto.BookStore;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.util.Scanner;



public class ManageBookstore {
	
	private static SessionFactory factory;
	
public static void main(String[] args){
		
		//Read data from file or take data from input
		System.out.println("What is the name of the store?");
	    Scanner sc = new Scanner(System.in);
	    String name = sc.next();
	    
	    System.out.println("What is the address of the store?");
	    sc = new Scanner(System.in);
	    String address = sc.next();
	    
	    System.out.println("What is the phone number of the store?");
	    sc = new Scanner(System.in);
	    String phone = sc.next();
	    
	    System.out.println("What is the website of the store?");
	    sc = new Scanner(System.in);
	    String website = sc.next();
	    

	    //Hibernate Code
		
		Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
		
		StandardServiceRegistryBuilder serviceRegistryBuilder = new StandardServiceRegistryBuilder();
		serviceRegistryBuilder.applySettings(configuration.getProperties());
		
		ServiceRegistry serviceRegistry = serviceRegistryBuilder.build();
		
		factory = new Configuration().configure().buildSessionFactory(serviceRegistry);
		
		//Hibernate Code

		
		long bookStoreID = addBookStore(name, address, phone, website);
		System.out.println("Bookstore id = "+ bookStoreID);

		BookStore bookStore = getBookStore(bookStoreID);
	    System.out.println("Bookstore["+bookStoreID+"] name ="+bookStore.getId());
		System.out.println("Bookstore["+bookStoreID+"] name ="+bookStore.getName());
	    System.out.println("Bookstore["+bookStoreID+"] address ="+bookStore.getAddress());
		System.out.println("Bookstore["+bookStoreID+"] phone ="+bookStore.getPhone());
		System.out.println("Bookstore["+bookStoreID+"] website ="+bookStore.getWebsite());

		bookStore.setName("Hello");
		updateBookStore(bookStore);
		bookStore = getBookStore(bookStoreID);
		System.out.println("Bookstore["+bookStoreID+"] name ="+bookStore.getId());
		System.out.println("Bookstore["+bookStoreID+"] name ="+bookStore.getName());
		System.out.println("Bookstore["+bookStoreID+"] address ="+bookStore.getAddress());
		System.out.println("Bookstore["+bookStoreID+"] phone ="+bookStore.getPhone());
		System.out.println("Bookstore["+bookStoreID+"] website ="+bookStore.getWebsite());

		deleteBookStore(bookStore);
		bookStore = getBookStore(bookStoreID);
		System.out.println("Bookstore["+bookStoreID+"] name ="+bookStore.getId());
		System.out.println("Bookstore["+bookStoreID+"] name ="+bookStore.getName());
		System.out.println("Bookstore["+bookStoreID+"] address ="+bookStore.getAddress());
		System.out.println("Bookstore["+bookStoreID+"] phone ="+bookStore.getPhone());
		System.out.println("Bookstore["+bookStoreID+"] website ="+bookStore.getWebsite());
	     
	}

public static long addBookStore(String name, String address, String phone, String website){
    Session session = factory.openSession();
    Transaction tx = null;
    long bookStoreID = 0;
    try{
       tx = session.beginTransaction();
       BookStore bookStore = new BookStore(name, address, phone, website);
       bookStoreID = (long)session.save(bookStore); 
       tx.commit();
    }catch (HibernateException e) {
       if (tx!=null) tx.rollback();
       e.printStackTrace(); 
    }finally {
       session.close(); 
    }
    return bookStoreID;
 }

	public static BookStore getBookStore(long id){
		Session session = factory.openSession();
		Transaction tx = null;
		BookStore bookStore = null;
		long bookStoreID = 0;
		try{
			tx = session.beginTransaction();
			bookStore = (BookStore)session.get(BookStore.class, id);
			tx.commit();
		}catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace();
		}finally {
			session.close();
		}
		return bookStore;
	}

	public static void updateBookStore(BookStore bookStore){
		Session session = factory.openSession();
		Transaction tx = null;


		try{
			tx = session.beginTransaction();
			session.update(bookStore);
			tx.commit();
		}catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace();
		}finally {
			session.close();
		}

	}

	public static void deleteBookStore(BookStore bookStore){
		Session session = factory.openSession();
		Transaction tx = null;


		try{
			tx = session.beginTransaction();
			session.delete(bookStore);
			tx.commit();
		}catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace();
		}finally {
			session.close();
		}

	}

}
