package utils;

import java.security.MessageDigest;
import java.util.ArrayList;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.cfg.Configuration;
import org.hibernate.mapping.List;


public class DatabaseHandler {

    Configuration configuration = new Configuration();
    SessionFactory sessionFactory;

    public DatabaseHandler(){
        configuration.setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy());
        configuration.configure();
        configuration.addAnnotatedClass(Result.class);
        configuration.addAnnotatedClass(User.class);
        sessionFactory = configuration.buildSessionFactory();
    }

    public ArrayList<Result> load(){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        
        Query q = session.createQuery("from Result", Result.class);
        ArrayList<Result> list = (ArrayList<Result>) q.getResultList();
        session.getTransaction().commit();
        return list;
    }

    public void persist (Result result){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        
        session.persist(result);
        session.getTransaction().commit();
    }

    public void clear(){
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        String hql = "delete from Result";
        Query q = session.createQuery(hql);
        q.executeUpdate();

        session.getTransaction().commit();
    }

    public boolean isRegUserExists(String login, String password) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
        
            Query q = session.createQuery("from User where login = :login", User.class);
            q.setParameter("login", login);
    
            java.util.List<User> list = q.getResultList();

            session.getTransaction().commit();
            return list != null && list.size() == 1;
        }
    }

    public boolean isUserExists(String login, String password) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
        
            Query q = session.createQuery("from User where login = :login and password = :password", User.class);
            System.out.println(login);
            System.out.println(password);
            System.out.println(q.toString());
            q.setParameter("login", login);
            q.setParameter("password", hashString(password));
    
            java.util.List<User> list = q.getResultList();
            System.out.println(list.size());
            session.getTransaction().commit();
            return list != null && list.size() == 1;
        }
    }

    public void addUser (User user){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        user.setPassword(hashString(user.getPassword()));
        session.persist(user);
        session.getTransaction().commit();
    }


    public String hashString(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(input.getBytes());

            StringBuilder hexString = new StringBuilder();
            for (byte b : encodedHash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString().substring(0, 20);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

