package springmvc.controller;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import springmvc.dao.EmpDao;
import springmvc.model.Emp;

@Controller
public class EmpController {
	
	@Autowired
	EmpDao dao;
	
	private static final String UPLOAD_DIRECTORY ="/images";  
	
	@RequestMapping("/index")
	public String indexPage() {
		return "index";
	}
	
	@RequestMapping("/empform")
	public String showForm(Model m) {
		m.addAttribute("command" , new Emp());
		return "empform";
	}
	
	@RequestMapping(value = "/save" , method=RequestMethod.POST)
	public String save(@RequestParam CommonsMultipartFile file,HttpSession session , 
			@ModelAttribute("emp")Emp emp) throws Exception{
	
       
        ServletContext context = session.getServletContext();  
        String path = context.getRealPath(UPLOAD_DIRECTORY);  
        String filename = file.getOriginalFilename();  
      
        String filePath = path+"/"+filename;
        System.out.println(path+" "+filename);        
      
        byte[] bytes = file.getBytes();  
        
        BufferedOutputStream stream = new BufferedOutputStream(  
                new FileOutputStream(path+"/"+filename));  
        
        stream.write(bytes);  
        stream.flush();  
        stream.close();  
        
        emp.setImage(filePath);
        dao.save(emp);
       
        
	   return "redirect:/viewemp";
	}
	
	@RequestMapping("/viewemp")
	public String viewemp(Model m) {
		List<Emp> list = dao.getEmployees();
		m.addAttribute("list" , list);
		 return "viewemp";    
	}

	@RequestMapping(value = "/editemp/{id}")  
	public String edit(@PathVariable int id , Model m) {
		Emp emp = dao.getEmpById(id);
		m.addAttribute("command",emp);  
        return "empeditform";  
	}
	
    @RequestMapping(value="/editsave",method = RequestMethod.POST)    
    public String editsave(@ModelAttribute("emp") Emp emp){    
        dao.update(emp);    
        return "redirect:/viewemp";    
    }    
    
    
    @RequestMapping(value="/deleteemp/{id}",method = RequestMethod.GET)    
    public String delete(@PathVariable int id){    
        dao.delete(id);    
        return "redirect:/viewemp";    
    }     
    
    
    
    @RequestMapping(value="/viewemppage/{pageid}")    
    public String viewByPage(@PathVariable int pageid,Model m){    
        int total=5;    
        if(pageid==1){}    
        else{    
            pageid=(pageid-1)*total+1;    
        }    
        System.out.println(pageid);  
        List<Emp> list=dao.getEmployeesByPage(pageid,total);    
          m.addAttribute("list", list);  
        return "viewemppage";    
    }  
    
    
   
	
}
