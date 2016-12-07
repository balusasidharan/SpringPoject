package com.balu.spring.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.balu.spring.dao.PersonDAO;
import com.balu.spring.messaging.MessageSender;
import com.balu.spring.model.Person;



@Component
@Service
@Qualifier("personService")
public class PersonServiceImpl implements PersonService {
	static final Logger LOG = LoggerFactory.getLogger(PersonServiceImpl.class);
	
	@Autowired
    private PersonDAO personDAO;
    @Autowired
    private MessageSender sender;
    public void setPersonDAO(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }
 
    @Override
    @Transactional
    public void addPerson(Person p) {
    	System.out.println(p);
    	System.out.println("Order to queue");
    	sender.sendMessage(p);
        this.personDAO.addPerson(p);
    }
 
    @Override
    @Transactional
    public void updatePerson(Person p) {
        this.personDAO.updatePerson(p);
    }
 
    @Override
    @Transactional
    public List<Person> listPersons() {
        return this.personDAO.listPersons();
    }
 
    @Override
    @Transactional
    public Person getPersonById(int id) {
        return this.personDAO.getPersonById(id);
    }
 
    @Override
    @Transactional
    public void removePerson(int id) {
        this.personDAO.removePerson(id);
    }
 
}