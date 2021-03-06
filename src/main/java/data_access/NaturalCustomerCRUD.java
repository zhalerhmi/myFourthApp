package data_access;

import data_access.entity.NaturalCustomer;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import util.HibernateUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DOTIN SCHOOL 4 on 9/28/2016.
 */
public class NaturalCustomerCRUD {
    private static ArrayList<NaturalCustomer> naturalCustomers = null;

    public static void insertIntoNaturalCustomerTable(NaturalCustomer naturalCustomer) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(naturalCustomer);
        int id = naturalCustomer.getId();
        naturalCustomer.setCustomerId(id);
        session.save(naturalCustomer);
        transaction.commit();
        session.close();
        System.out.println("successfully inserted into table");
    }

    public static NaturalCustomer updateNaturalCustomerInTable(NaturalCustomer naturalCustomer) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.update(naturalCustomer);
        transaction.commit();
        session.close();
        System.out.println("successfully updated record" + naturalCustomer.toString());
        return naturalCustomer;
    }

    public static void deleteFromNaturalCustomerTable(int customerId) throws SQLException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        NaturalCustomer naturalCustomer = session.get(NaturalCustomer.class, customerId);
        session.delete(naturalCustomer);
        transaction.commit();
        session.close();
        System.out.println("successfully deleted record");
    }

    public static List<NaturalCustomer> retrieveCustomerById(String customerId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("From NaturalCustomer naturalCustomer WHERE naturalCustomer.customerId = :customerId ");
        query.setParameter("customerId", Integer.parseInt(customerId));
        List naturalCustomer = query.list();
        transaction.commit();
        session.close();
        System.out.println("successfully Read from table" + naturalCustomer);
        return naturalCustomer;
    }


    public static ArrayList<NaturalCustomer> search(NaturalCustomer naturalCustomer) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        naturalCustomers = (ArrayList<NaturalCustomer>) searchCriteria(session, naturalCustomer).list();
        transaction.commit();
        session.close();
        System.out.println("successfully Read from table" + naturalCustomers);
        return naturalCustomers;
    }

    private static Criteria searchCriteria(Session session, NaturalCustomer naturalCustomer) {

        Criteria criteria = session.createCriteria(NaturalCustomer.class);

        if ((naturalCustomer.getCustomerId() != null)) {
            criteria.add(Restrictions.eq("customerId", naturalCustomer.getCustomerId()));
        }
        if (naturalCustomer.getNationalCode() != null) {
            criteria.add(Restrictions.eq("nationalCode", naturalCustomer.getNationalCode()));
        }
        if (naturalCustomer.getFirstName() != null) {
            criteria.add(Restrictions.eq("firstName", naturalCustomer.getFirstName()));
        }
        if (naturalCustomer.getLastName() != null) {
            criteria.add(Restrictions.eq("lastName", naturalCustomer.getLastName()));
        }
        return criteria;
    }

    public static List<NaturalCustomer> findCustomerByNationalCode(String nationalCode) {
        List<NaturalCustomer> naturalCustomers = new ArrayList<NaturalCustomer>();
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query query = session.createQuery("from NaturalCustomer  R where R.nationalCode = :nationalCode");
        query.setParameter("nationalCode", nationalCode);
        naturalCustomers = query.list();
        session.close();
        return naturalCustomers;
    }

    public static void main(String[] args) {

        retrieveCustomerById("2");
    }


}
