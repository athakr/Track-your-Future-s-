package net.TrackYourFuture.backend.controller;

import java.io.Console;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import net.TrackYourFuture.backend.Budget;
import net.TrackYourFuture.backend.model.Profile;
import net.TrackYourFuture.backend.repository.UserRepository;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("users")
    public List<Profile> getUsers(){
        return this.userRepository.findAll();
    }

    @RequestMapping(method = RequestMethod.POST, path = "/{id}")    
    public void add(HttpServletRequest request, HttpServletResponse response){
        Profile p = new Profile();
        ObjectMapper mapper = new ObjectMapper();
        try{
            p = mapper.readValue(request.getInputStream(), Profile.class);
        }catch(Exception e){
            System.out.println("Exception with request reader.");
        }
        //System.out.println(request.getHeader("budget").toString());
        this.userRepository.save(p);
    }
    @RequestMapping(method = RequestMethod.PUT, path = "/{id}")
    public void update(HttpServletRequest  request, HttpServletRequest response){
        String id = "-1";
        try{
            id = (request.getReader().lines().collect(Collectors.joining(System.lineSeparator()))).replaceAll("\\D", "");
            if (!id.equals("-1")){
                Double salary = Double.parseDouble(request.getHeader("budgetsalary").toString());
                Double spending = Double.parseDouble(request.getHeader("budgetspending").toString()); 
                Profile p = userRepository.findById(Long.parseLong(id)).get();
                Budget b = p.getBudget();
                b.setSalary(salary);
                b.setSpend(spending);
                p.setBudget(b);
                userRepository.saveAndFlush(p);
            }
        }catch(Exception e){
            System.out.println("Exception with request reader.");
        }

    }   

    @RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
    public void delete(@RequestBody String request){
        userRepository.deleteById(Long.parseLong(request));
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public Profile retrieve(@PathVariable Long id){
        return userRepository.findById(id).get();
    }
}