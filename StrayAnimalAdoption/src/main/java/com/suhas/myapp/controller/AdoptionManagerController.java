package com.suhas.myapp.controller;

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

import com.suhas.myapp.dao.AdoptionRecordDAO;
import com.suhas.myapp.dao.AnimalDAO;
import com.suhas.myapp.dao.UserDAO;
import com.suhas.myapp.pojo.Adopter;
import com.suhas.myapp.pojo.AdoptionRecord;
import com.suhas.myapp.pojo.Animal;

@Controller
public class AdoptionManagerController {
	@Autowired
	@Qualifier("adoptionRecordDAO")
	AdoptionRecordDAO adoptionDAO;
	
	@Autowired
	@Qualifier("animalDAO")
	AnimalDAO animalDAO;
	
	@Autowired
	@Qualifier("userDAO")
	UserDAO userDAO;
	
	@Autowired
	@Qualifier("adoptionRecordDAO")
	AdoptionRecordDAO adoptionRecordDAO;
	
	@RequestMapping(value="/adoptionSupervise/adoptionDashboard",method = RequestMethod.GET)
	public ModelAndView getAdoptionDashboard( HttpServletRequest request ) 
															throws Exception{
		ModelAndView mv = new ModelAndView();
		HttpSession session = (HttpSession) request.getSession();
		try{
			mv.setViewName("adoptionManager_dashboard");
			List<AdoptionRecord> recordList = adoptionRecordDAO.getAdoptionRecordListPending();
			for(AdoptionRecord r: recordList) {
				r.setAnimal(animalDAO.getAnimalById(r.getAnimalId()));
				r.setAdopter(userDAO.getAdopterByEmail(r.getAdopterEmail()));
			}
			session.setAttribute("records", recordList);
		}
		catch(Exception e){
			System.out.println("AdoptionSuperviserController - getAdoptionDashboard");
			mv.setViewName("ERROR");
		}
		return mv;
	}
	
	@RequestMapping(value="/adoptionSupervise/superviseNew", method = RequestMethod.GET)
	public ModelAndView postAdoptionDashboard( HttpServletRequest request,
												@RequestParam ("recordSelected") long recordid) 
															throws Exception{
		ModelAndView mv = new ModelAndView();
		HttpSession session = (HttpSession) request.getSession();
		try{
			mv.setViewName("adoptionManager_evaluation");
			System.out.println("step 1");
			
			AdoptionRecord record = (AdoptionRecord) adoptionDAO.getAdoptionRecordByID(recordid);
			Animal animal = animalDAO.getAnimalById(record.getAnimalId());
		
			
			
			Adopter adopter = (Adopter) userDAO.getAdopterByEmail(record.getAdopterEmail());
			session.setAttribute("recordSelected", record);
			session.setAttribute("animalSupervised", animal);
			session.setAttribute("adopterSupervised", adopter);
		}
		catch(Exception e){
			System.out.println("AdoptionSuperviserController - getAdoptionDashboard");
			mv.setViewName("error");
		}
		return mv;
	}
	
	
	@RequestMapping(value="/adoptionSupervise/superviseNew", method=RequestMethod.POST)
	public ModelAndView postAdoptionApproved( HttpServletRequest request,
												@RequestParam ("statusSelected") String statusSelected)
						throws Exception{
		ModelAndView mv = new ModelAndView();
		HttpSession session = (HttpSession) request.getSession();
		System.out.println("inside postAdoptionApproved");
		try{
			AdoptionRecord ar = (AdoptionRecord)session.getAttribute("recordSelected");
			ar.setStatus(statusSelected);
			adoptionDAO.updateAdoptionRecord(ar.getId(), statusSelected);
			
			Animal animal = (Animal)session.getAttribute("animalSupervised");
			if(statusSelected.equals("approved")) {
				animal.setStatus("adopted");
				animalDAO.updateAnimal(animal.getAnimalId(), "adopted");
			}
			else {
				animal.setStatus("treated");
				animalDAO.updateAnimal(animal.getAnimalId(), "treated");
			}
			mv.setViewName("adoptionManager_dashboard");
			session.setAttribute("records", adoptionDAO.getAdoptionRecordListPending());
			boolean isEmpty = false;
			if( adoptionDAO.getAdoptionRecordListPending().size() == 0 ) {
				isEmpty = true;
			}
			mv.addObject("isEmpty", isEmpty);
			
			return mv;
		}
		catch (Exception e)	{
			System.out.println(e);
			mv.setViewName("error");
		}
		return mv;
		
	}
	
}
