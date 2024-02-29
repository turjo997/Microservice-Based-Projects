package springmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import springmvc.model.Reservation;

@RequestMapping("/reservation")  
@Controller
public class ReservationController {
	
	@RequestMapping("/request")
	public String reservationRequest() {
		return "reservation-request";
	}
	
	@RequestMapping("/bookingForm") 
	public String bookingForm(Model model) {
		Reservation res = new Reservation();
		model.addAttribute("reservation" , res);
		return "reservation-page";  
	}
	
	
	@RequestMapping("/submitForm")
	public String submitForm(@ModelAttribute("reservation")Reservation res) {
		 return "confirmation-page";
	}
	

	
	@RequestMapping("/radioRequest")
	public String radioRequest() {
		return "radio-request";
	}
	
	@RequestMapping("/radioForm") 
	public String radioForm(Model model) {
		Reservation res = new Reservation();
		model.addAttribute("command" , res);
		return "radio-page";  
	}
	

}
