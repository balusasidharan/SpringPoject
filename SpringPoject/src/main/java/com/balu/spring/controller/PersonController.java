package com.balu.spring.controller;

import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.balu.spring.aspects.TimingAspect;
import com.balu.spring.dao.PersonDAO;
import com.balu.spring.model.Person;
import com.balu.spring.service.PersonService;

@Controller

public class PersonController {
     
	@Autowired
	private PersonService personService;
	
//	@Autowired
//	private TimingAspect timingAspect;
//	
    
	@Autowired
    @Qualifier(value="personService")
	public void setPersonService(PersonService ps){
		this.personService = ps;
	}
	
	@RequestMapping(value ="/persons",method=RequestMethod.GET)
	public String listPersons(Model model){
		System.out.println("personService" + personService);
		model.addAttribute("person" ,new Person());
		model.addAttribute("listPersons", this.personService.listPersons());
		
		System.out.println("Testinh hot deploy");
		return "person";
		
	}
	
	//For add and update person both
    @RequestMapping(value= "/person/add", method = RequestMethod.POST)
    public String addPerson(@ModelAttribute("person") Person p){
         
    	System.out.println("Person id : "+p.toString());
        if(p.getId() == 0){
            //new person, add it
        	String name = p.getName();
        	System.out.println("Escaped String =" + StringEscapeUtils.escapeHtml(name));
        	p.setName(StringEscapeUtils.escapeHtml(name));
            this.personService.addPerson(p);
            
        }else{
            //existing person, call update
        	
            this.personService.updatePerson(p);
        }
         
        return "redirect:/persons";
         
    }
    
    
    @RequestMapping("/remove/{id}")
    public String removePerson(@PathVariable("id") int id){
         
        this.personService.removePerson(id);
        return "redirect:/persons";
    }
  
    @RequestMapping("/edit/{id}")
    public String editPerson(@PathVariable("id") int id, Model model){
        model.addAttribute("person", this.personService.getPersonById(id));
        model.addAttribute("listPersons", this.personService.listPersons());
        return "person";
    }
	
   @RequestMapping(value ="/", method = RequestMethod.GET)
   public String defaultView(){
	   return "redirect:/persons";
   }
}
