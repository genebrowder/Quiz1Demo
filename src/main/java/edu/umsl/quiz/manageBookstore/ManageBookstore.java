package edu.umsl.quiz.manageBookstore;

import edu.umsl.quiz.dto.BookStore;
import org.hibernate.*;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Scanner;


public class ManageBookstore {
	
	private static SessionFactory factory;
	
public static void main(String[] args){
        int appChoice=0;

        LinkedHashSet <BookStore> bookStoreSet = new LinkedHashSet<>();

        int choice=0;

        //Hibernate Code

        Configuration configuration = new Configuration().configure("hibernate.cfg.xml");

        StandardServiceRegistryBuilder serviceRegistryBuilder = new StandardServiceRegistryBuilder();
        serviceRegistryBuilder.applySettings(configuration.getProperties());

        ServiceRegistry serviceRegistry = serviceRegistryBuilder.build();

        factory = new Configuration().configure().buildSessionFactory(serviceRegistry);

        //Hibernate Code

        while(appChoice != 99){
            System.out.println("What would you like to do?");
            System.out.println("1) Read data from a file to ADD");
            System.out.println("2) Enter Bookstore data from a keyboard to ADD");
            System.out.println("3) Modify a bookstore");
            System.out.println("4) Delete a bookstore");
            System.out.println("5) See all Bookstores");
            System.out.println("99) EXIT");
            System.out.println("");

            Scanner appIn = new Scanner(System.in);

            String appInput = appIn.next();

            appChoice = Integer.parseInt(appInput);

            Scanner sc;
            String id ;
            long bookStoreID ;



            switch(appChoice){
                case 1:

                    break;


                case 2:
                    while(choice != 99)  {


                        BookStore bookStore = new BookStore();

                        //Read data from file or take data from input
                        System.out.println("What is the name of the store?");
                        sc = new Scanner(System.in);
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

                    bookStoreSet = addBookStore(bookStoreSet) ;

                    showAllBookStores();

                    break;


                case 3:


                    showAllBookStores();

                    System.out.println("Enter the id of the book store you would like to modify");
                    sc = new Scanner(System.in);
                    id = sc.next();
                    bookStoreID = Integer.parseInt(id);

                    BookStore bookStore = new BookStore();

                    bookStore = getBookStore(bookStoreID);

                    System.out.println("Bookstore name ="+bookStore.getName());
                    sc = new Scanner(System.in);
                    String name = sc.next();
                    if (!(name.equals(""))) {
                       bookStore.setName(name);
                    }
                    System.out.println("Bookstore address ="+bookStore.getAddress());
                    sc = new Scanner(System.in);
                    String address = sc.next();
                    if (!(address.equals(""))) {
                        bookStore.setAddress(address);
                    }
                    System.out.println("Bookstore phone ="+bookStore.getPhone());
                    sc = new Scanner(System.in);
                    String phone = sc.next();
                    if (!(phone.equals(""))) {
                        bookStore.setPhone(phone);
                    }
                    System.out.println("Bookstore website ="+bookStore.getWebsite());
                    sc = new Scanner(System.in);
                    String website = sc.next();
                    if (!(website.equals(""))) {
                        bookStore.setWebsite(website);
                    }

                    updateBookStore(bookStore);

                    showAllBookStores();

                    sc.close();


                    break;


                case 4:
                    showAllBookStores();

                    System.out.println("Enter the id of the book store you would like to delete") ;
                    sc = new Scanner(System.in);
                    id = sc.next();
                    bookStoreID = Integer.parseInt(id);

                    bookStore = new BookStore();

                    bookStore = getBookStore(bookStoreID);

                    deleteBookStore(bookStore);

                    showAllBookStores();

                    sc.close();

                    break;


                case 5:

                    showAllBookStores();

                    break;

                default:

                    break;
            }


            System.out.println("appChoice = "+appChoice);
            //appIn.close();

        }







	     
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
