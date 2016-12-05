package com.balu.spring.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.dialect.PostgreSQLDialect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.balu.spring.model.Person;

@Repository
@Component
public class PersonDAOImpl implements PersonDAO {

	private static final Logger logger = LoggerFactory.getLogger(PersonDAOImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory ;
	
	
	public void setSessionFactory(SessionFactory sf){
        this.sessionFactory = sf;
    }
	@Override
	public void addPerson(Person p) {
		
		Session session = this.sessionFactory.getCurrentSession();
		
		session.save(p);
		logger.info("Person saved: "+p.toString());

	}

	@Override
	public void updatePerson(Person p) {
		// TODO Auto-generated method stub
		Session session = this.sessionFactory.getCurrentSession();
		session.update(p);

	}

	
	@SuppressWarnings({ "unchecked" })
	@Override
	public List<Person> listPersons() {
		// TODO Auto-generated method stub
		System.out.println("Session Factory : "+ this.sessionFactory);
		Session session = this.sessionFactory.getCurrentSession();
		List<Person> personsList = session.createQuery("from Person").list();
		for(Person p : personsList){
            logger.info("Person List::"+p);
        }
        return personsList;
		
	}
	@Override
    public Person getPersonById(int id) {
        Session session = this.sessionFactory.getCurrentSession();     
        Person p = (Person) session.load(Person.class, new Integer(id));
        logger.info("Person loaded successfully, Person details="+p);
        return p;
    }
 
    @Override
    public void removePerson(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        Person p = (Person) session.load(Person.class, new Integer(id));
        if(null != p){
            session.delete(p);
        }
        logger.info("Person deleted successfully, person details="+p);
    }

}
