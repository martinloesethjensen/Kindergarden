package com.example.kindergarden.controllers;

import com.example.kindergarden.base.Employee;
import com.example.kindergarden.FileHandler;
import com.example.kindergarden.services.ServiceEmployee;
import com.example.kindergarden.services.ServiceSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class EmployeeController {
    ServiceEmployee serviceEmployee = new ServiceEmployee();

    @GetMapping("/employee")
    public String employee(Model model){

      if(ServiceSession.isSomeoneLoggedIn()) {
        model.addAttribute("employees", serviceEmployee.getEmployees()); //får fat i arraylist
        model.addAttribute("employee", new Employee());

        return "employee";

      } else {
        return "redirect:/";
      }
    }

    @PostMapping("/employee")
    public String addEmployee(@ModelAttribute Employee em){
        ServiceEmployee.addEmployeeToList(em); //Metode i serviceEmployee der gemmer employee til arraylist
        new FileHandler("employees.txt").saveEmployeeToFile();
        //test
        System.out.println(em);
        return "redirect:/employee";
    }
}
