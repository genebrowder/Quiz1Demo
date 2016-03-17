package edu.umsl.quiz.manageBookstore;

import edu.umsl.quiz.dto.BookStore;
import org.hibernate.*;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.util.*;


public class ManageBookstore {
	
	private static SessionFactory factory;
	
public static void main(String[] args){

        LinkedHashSet <BookStore> bookStoreSet = new LinkedHashSet<>();

        int choice=0;

        while(choice != 99)  {


		BookStore bookStore = new BookStore();

        //Read data from file or take data from input
		System.out.println("What is the name of the store?");
	    Scanner sc = new Scanner(System.in);
	    String name = sc.next();
        bookStore.setName(name);
	    
	    System.out.println("What is the address of the store?");
	    sc = new Scanner(System.in);
	    String address = sc.next();
        bookStore.setAddress(address);
	    
	    System.out.println("What is the phone number of the store?");
	    sc = new Scanner(System.in);
	    String phone = sc.next();
        bookStore.setPhone(phone);
	    
	    System.out.println("What is the website of the store?");
	    sc = new Scanner(System.in);
	    String webSite = sc.next();
        bookStore.setWebsite(webSite);

        bookStoreSet.add(bookStore);

        System.out.println("What you like to enter another book store? (Y or N):");
        sc = new Scanner(System.in);
        String decision = sc.next();
        if(decision.equals("N") || decision.equals("n")) {
            choice=99;
        }


        }
	    //Hibernate Code
		
		Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
		
		StandardServiceRegistryBuilder serviceRegistryBuilder = new StandardServiceRegistryBuilder();
		serviceRegistryBuilder.applySettings(configuration.getProperties());
		
		ServiceRegistry serviceRegistry = serviceRegistryBuilder.build();
		
		factory = new Configuration().configure().buildSessionFactory(serviceRegistry);
		
		//Hibernate Code

        showAllBookStores();

        //long bookStoreID = addBookStore(name, address, phone, website);
		//System.out.println("Bookstore id = "+ bookStoreID);

        bookStoreSet = addBookStore(bookStoreSet) ;

        showAllBookStores();

		/*BookStore bookStore = getBookStore(bookStoreID);
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
		System.out.println("Bookstore["+bookStoreID+"] website ="+bookStore.getWebsite());   */
	     
	}

public static long addBookStore(String name, String address, String phone, String website){
    Session session = factory.openSession();
    Transaction tx = null;
    long bookStoreID = 0;
    try{
       tx = session.beginTransaction();
       BookStore bookStore = new BookStore(name, address, phone, website);
       bookStoreID = (long)session.save(bookStore);
        System.out.println("Bookstore["+bookStoreID+"] name ="+bookStore.getName());
        System.out.println("Bookstore["+bookStoreID+"] address ="+bookStore.getAddress());
        System.out.println("Bookstore["+bookStoreID+"] phone ="+bookStore.getPhone());
        System.out.println("Bookstore["+bookStoreID+"] website ="+bookStore.getWebsite());
        System.out.println() ;
       tx.commit();
    }catch (HibernateException e) {
       if (tx!=null) tx.rollback();
       e.printStackTrace(); 
    }finally {
       session.close(); 
    }
    return bookStoreID;
 }
    public static long addBookStore(BookStore bookStore){
        Session session = factory.openSession();
        Transaction tx = null;
        long bookStoreID = 0;
        try{
            tx = session.beginTransaction();
            bookStoreID = (long)session.save(bookStore);
            System.out.println("Bookstore["+bookStoreID+"] name ="+bookStore.getName());
            System.out.println("Bookstore["+bookStoreID+"] address ="+bookStore.getAddress());
            System.out.println("Bookstore["+bookStoreID+"] phone ="+bookStore.getPhone());
            System.out.println("Bookstore["+bookStoreID+"] website ="+bookStore.getWebsite());
            System.out.println() ;
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
        return bookStoreID;
    }
    public static LinkedHashSet<BookStore>  addBookStore(LinkedHashSet<BookStore> bookStoreSet){

        for(BookStore bookStore: bookStoreSet) {
            long bookStoreID = (long) addBookStore(bookStore);
            bookStore.setId(bookStoreID);
        }
        return bookStoreSet;
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

    public static List showAllBookStores(){
        Session session = factory.openSession();
        Transaction tx = null;

        List allBookStores = new ArrayList();;
        try{
            tx = session.beginTransaction();

            Query queryResult = session.createQuery("from BookStore");


            allBookStores = queryResult.list();
            for (int i = 0; i < allBookStores.size(); i++) {
                BookStore bookStore = (BookStore) allBookStores.get(i);
                System.out.println("Bookstore["+bookStore.getId()+"] name ="+bookStore.getName());
                System.out.println("Bookstore["+bookStore.getId()+"] address ="+bookStore.getAddress());
                System.out.println("Bookstore["+bookStore.getId()+"] phone ="+bookStore.getPhone());
                System.out.println("Bookstore["+bookStore.getId()+"] website ="+bookStore.getWebsite());

//                System.out.println("name ="+bookStore.getName());
//                System.out.println("address ="+bookStore.getAddress());
//                System.out.println("phone ="+bookStore.getPhone());
//                System.out.println("website ="+bookStore.getWebsite());
//                System.out.println() ;
            }


            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }

        return allBookStores;
    }
}
