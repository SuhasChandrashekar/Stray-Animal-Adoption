package com.suhas.myapp.controller;

import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.suhas.myapp.dao.AnimalDAO;
import com.suhas.myapp.dao.RescueRecordDAO;
import com.suhas.myapp.dao.TreatmentRecordDAO;
import com.suhas.myapp.dao.UserDAO;
import com.suhas.myapp.pojo.Animal;
import com.suhas.myapp.pojo.Employee;
import com.suhas.myapp.pojo.RescueRecord;
import com.suhas.myapp.pojo.TreatmentRecord;

@Controller
public class TreatmentController {
	@Autowired
	@Qualifier("animalDAO")
	AnimalDAO animalDAO;
	
	@Autowired
	@Qualifier("userDAO")
	UserDAO userDAO;
	
	@Autowired
	@Qualifier("rescueRecordDAO")
	RescueRecordDAO rescueRecordDAO;
	
	@Autowired
	@Qualifier("treatmentRecordDAO")
	TreatmentRecordDAO treatmentRecordDAO;
	
	@RequestMapping(value="/veterinarian/newTreat",method = RequestMethod.GET)
	public ModelAndView getTreat( HttpServletRequest request, 
									@RequestParam ("animalSelected") long animalid) 
						throws Exception{
		ModelAndView mv = new ModelAndView();
		HttpSession session = (HttpSession) request.getSession();
		try{
			mv.setViewName("vet_treatment");
			Animal animal = animalDAO.getAnimalById(animalid);
			System.out.println("getTreat: " + animal.getAnimalId());
			session.setAttribute("animalTreated", animal);
			mv.addObject("animalTreated", animal);
		}
		catch(Exception e) {
			System.out.println(e);
		}
		return mv;
	}
	
	@RequestMapping(value="/veterinarian/newTreat", method=RequestMethod.POST)
	public ModelAndView postTreat( HttpServletRequest request )
						throws Exception{
		ModelAndView mv = new ModelAndView();
		HttpSession session = (HttpSession) request.getSession();
		try{
			TreatmentRecord tr = new TreatmentRecord();
			Employee e = (Employee) session.getAttribute("user");
			Animal a = (Animal) session.getAttribute("animalTreated");
			tr.setVeterinarian(e);
			tr.setAnimal(a);
			
			Calendar c = Calendar.getInstance();
	        int year = c.get(Calendar.YEAR); 
	        int month = c.get(Calendar.MONTH); 
	        int date = c.get(Calendar.DATE);
			tr.setDate(month+1, date, year);
			
			tr.setAnimalid(a.getAnimalId());
			tr.setVeterinarianid(e.getEmployeeId());
			animalDAO.updateAnimal(a.getAnimalId(), "treated");
			treatmentRecordDAO.addTreatmentRecord(tr);
			List<RescueRecord> recordList = rescueRecordDAO.getRescueRecordList();
			for(RescueRecord rr: recordList) {
				rr.setAnimal(animalDAO.getAnimalById(rr.getAnimalid()));
				rr.setRegistrator(userDAO.getEmployeeByID(rr.getRegistratorid()));
			}
			session.setAttribute("rescueRecordList", recordList);
			mv.setViewName("vet_dashboard");
			
			return mv;
		}
		catch (Exception e)	{
			System.out.println(e);
		}
		return mv;
		
	}
}
