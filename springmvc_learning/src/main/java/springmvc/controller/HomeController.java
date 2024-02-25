package springmvc.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

	@RequestMapping("/home")
	public String home() {
		System.out.println("This is home url");
		return "index";
	}

	@RequestMapping("/about")
	public String about() {
		System.out.println("This is about url");
		return "about";
	}

	@RequestMapping("/hello")
	public String redirect() {
		return "viewpage";
	}

	@RequestMapping("/helloagain")
	public String display() {
		return "final";
	}

	@RequestMapping("/form")
	public String form() {
		return "basicForm";
	}
	
	
	@RequestMapping("/display")
	public String display2(HttpServletRequest req , Model m) {
		
		String name = req.getParameter("name");
		String pass = req.getParameter("pass");
		
        if(pass.equals("admin"))  
        {  
            String msg="Hello "+ name;  
            //add a message to the model  
            m.addAttribute("message", msg);  
            return "resultpage";  
        }  
        else  
        {  
            String msg="Sorry "+ name+". You entered an incorrect password";  
            m.addAttribute("message", msg);  
            return "errorpage";  
        }   
		
	}
	
	
    @RequestMapping("/helloParam")  
    public String display3(@RequestParam("name") String name,@RequestParam("pass") String pass,Model m)  
    {  
        if(pass.equals("admin"))  
        {  
            String msg="Hello "+ name;  
            //add a message to the model  
            m.addAttribute("message", msg);
            
            List<String> friends = new ArrayList<String>();
            
            friends.add("Anik");
            friends.add("Shuvo");
            friends.add("Rahul");
            friends.add("Mamun");
            friends.add("Fahim");
            
            m.addAttribute("f", friends);
            
         
            
            return "resultpage";  
        }  
        else  
        {  
            String msg="Sorry "+ name+". You entered an incorrect password";  
            m.addAttribute("message", msg);  
            return "errorpage";  
        }     
    } 
	
	
	
}
