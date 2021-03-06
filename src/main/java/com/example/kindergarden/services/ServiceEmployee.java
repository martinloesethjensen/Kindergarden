package com.example.kindergarden.services;

import com.example.kindergarden.base.Employee;
import com.example.kindergarden.FileHandler;
import com.example.kindergarden.base.Schedule;
import com.example.kindergarden.base.Session;

import java.util.ArrayList;
import java.util.Arrays;

public class ServiceEmployee {
  private ArrayList<Employee> employees = new ArrayList<>();
  public FileHandler fileHandler;

  public ServiceEmployee() {
    try {
      fileHandler = new FileHandler("employees.txt");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void addEmployeeToList(Employee em, Session session) {
    int id = employees.size() == 0 ? 1 : getHighestIdOfEmployees();
    ServiceSession.saveLogin(session, id, employees);
    employees.add(em.setId(id));
    fileHandler.saveEmployeeToFile(employees);

  }

  public ArrayList<Employee> getEmployees() {
    return employees = fileHandler.loadEmployees("employees.txt");
  }

  private int getHighestIdOfEmployees() {
    int id = 1;

    for (Employee employee : employees) {
      if (employee.getId() > id) {
        id = employee.getId();
      }
    }
    return id + 1; // +1 to increment id
  }

  public void deleteEmployee(int id) {
    int index = getIndex(id);

    if (index > -1) {
      employees.remove(index);

      //gemmer medarbejder til fil
      fileHandler.saveEmployeeToFile(employees);

      //Får alle vagterne for den pågældende medarbejder i et nyt array
      ArrayList<Schedule> schedules = fileHandler.setFileName("schedules.txt").getSchedules("0", "0", id);
      int[] schedulesToRemove = new int[schedules.size()];
      for(int i = 0; i < schedulesToRemove.length; i++){
        schedulesToRemove[i] = schedules.get(i).getId();
      }
      ArrayList<Schedule> tempSchedule = new ServiceSchedule().removeMultiple(schedulesToRemove);

      //Slet login for medarbejder
      ServiceSession.deleteLogin(id);

      fileHandler.setFileName("employees.txt");
    } else System.out.println("not found");
  }

  public void editEmployee(Employee employee){
      employees = fileHandler.loadEmployees("employees.txt");
      employees.add(getIndex(employee.getId()), employee);
      employees.remove(getIndex(employee.getId())+1);
      fileHandler.saveEmployeeToFile(employees);
  }

  public int getIndex(int id) {
    for (int i = 0; i < employees.size(); i++)
      if (employees.get(i).getId() == id)
        return i;
    return -1;
  }
}