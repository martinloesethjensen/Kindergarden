package com.example.kindergarden.services;

import com.example.kindergarden.FileHandler;
import com.example.kindergarden.base.Employee;
import com.example.kindergarden.base.Schedule;

import java.util.*;

public class ServiceSchedule {
    private ArrayList<Schedule> schedules = new ArrayList<>();
    private ArrayList<Employee> employees = new ArrayList<>();
    private FileHandler fileHandler;
    private int[] countSchedulesPerDay = new int[42];
    private int[] countSchedulesPerDayForPerson = new int[42];
    private int[] toIndexArray = new int[42];

    public ServiceSchedule() {
        try {
            fileHandler = new FileHandler("schedules.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Schedule> getSchedules(String month, String year, int id) {
        return schedules = fileHandler.getSchedules(month, year, id);
    }

    public ArrayList<Employee> getEmployees() {
        return employees = fileHandler.loadEmployees("employees.txt");
    }


    public int[] getCountSchedulesPerDay(ServiceCalendar serviceCalendar) {
        resetAllSchedules();

        for(int i = 0; i < countSchedulesPerDay.length; i++) {
           for(Schedule schedule: schedules) {
                if(Integer.parseInt(schedule.getDate()) == i+1) {
                    countSchedulesPerDay[i+serviceCalendar.getPreviousMonthDays()]++;
                }
           }
        }

        return countSchedulesPerDay;
    }

    public int[] getGetCountSchedulesPerDayForPerson(ServiceCalendar serviceCalendar, int id) {
        if(id != 0){
            resetCountPerPerson();

            for(int i = 0; i < countSchedulesPerDayForPerson.length; i++) {
                for(Schedule schedule: schedules) {
                    if(Integer.parseInt(schedule.getDate()) == i+1 && schedule.getEmployeeKey() == id) {
                        countSchedulesPerDayForPerson[i+serviceCalendar.getPreviousMonthDays()]++;
                    }
                }
            }
        }

        return countSchedulesPerDayForPerson;
    }

    private void resetCountPerPerson() {
        for(int i = 0; i < countSchedulesPerDayForPerson.length; i++) {
            countSchedulesPerDayForPerson[i] = 0;
        }
    }

    public int[] getToIndexArray(ServiceCalendar serviceCalendar) {
        int cumulative = 0;
        int daysFromPrevMonth = serviceCalendar.getPreviousMonthDays();
        int prevIndex = 0;

        for(int i = 0; i < toIndexArray.length; i++) {
            int thisIndex = 0;
            int count = 1;

            for (Schedule schedule : schedules) {
                if (Integer.parseInt(schedule.getDate()) + 1 == (i - daysFromPrevMonth + 2) && count != 3) {
                    thisIndex++;
                    count++;
                }else if (Integer.parseInt(schedule.getDate()) + 1 > (i - daysFromPrevMonth + 2)) {
                    break;
                }
            }

            toIndexArray[i] = (thisIndex == 0 ? 0 : thisIndex +prevIndex + cumulative);
            cumulative += prevIndex;

            prevIndex = 0;

            for (Schedule schedule : schedules) {
                if (Integer.parseInt(schedule.getDate()) + 1 == (i - daysFromPrevMonth + 2)) {
                    prevIndex++;
                }
            }
        }

        return toIndexArray;
    }

    private void resetAllSchedules() {
        for(int i = 0; i < countSchedulesPerDay.length; i++) {
            countSchedulesPerDay[i] = toIndexArray[i] = 0;
        }
    }

    public void add(Schedule schedule) {
        schedules = fileHandler.getAllSchedules();
        schedules.add(schedule.setId(getHighestId()));
        sort();
        fileHandler.saveSchedulesToFile(schedules);
    }

    private int getHighestId() {
        int highestId = 00;

        for(Schedule schedule: schedules) {
            if(schedule.getId() > highestId) {
                highestId = schedule.getId();
            }
        }

        return (highestId == 0 ? 1 : highestId + 1);
    }

    public Schedule remove(int id) {
        Schedule removedSchedule = new Schedule();

        schedules = fileHandler.getAllSchedules();

        for(Schedule schedule: schedules) {
            if(schedule.getId() == id) {
                schedules.remove(schedule);
                removedSchedule = schedule;
                break;
            }
        }

        sort();
        fileHandler.saveSchedulesToFile(schedules);

        return removedSchedule;
    }

    public ArrayList<Schedule> removeMultiple(int[] ids) {
        ArrayList<Schedule> removedSchedules = new ArrayList<>();

        schedules = fileHandler.getAllSchedules();
        Iterator<Schedule> it = schedules.iterator();

        while(it.hasNext()){
            Schedule temp = it.next();

            for (int id : ids) {
                if(temp.getId() == id) {
                    it.remove();
                    removedSchedules.add(temp);
                    break;
                }
            }
        }

        sort();
        fileHandler.saveSchedulesToFile(schedules);

        return removedSchedules;
    }

    public int[] getAllIdForSchedulesFromEmployee(int id){


        return null;
    }

    public String getViewCalendarForName(int id) {
        String name = "Alle";

        if(id != 0) {
            for(Employee employee: employees) {
                if(employee.getId() == id) {
                    name = employee.getFirstName()+" "+employee.getLastName();
                }
            }
        }

        return name;
    }

    public void edit(String id, int employeeKey, String date, String fromTime, String toTime) {
        schedules = fileHandler.getAllSchedules();

        for(Schedule schedule: schedules) {
            if(schedule.getId() == Integer.parseInt(id)) {
                schedule.setEmployeeKey(employeeKey);
                schedule.setDate(date.replaceAll("-", ""));
                schedule.setFromTime(fromTime.replace(":", ""));
                schedule.setToTime(toTime.replace(":", ""));
                break;
            }
        }

        sort();
        fileHandler.saveSchedulesToFile(schedules);
    }

    private void sort() {
        Collections.sort(schedules, new Comparator<Schedule>() {
            @Override
            public int compare(Schedule o1, Schedule o2) {
                int date = o1.getDate().compareTo(o2.getDate());
                if(date != 0){
                    return date;
                }
                int fromTime = o1.getFromTime().compareTo(o2.getFromTime());
                if(fromTime != 0) {
                    return fromTime;
                }

                return o1.getDate().compareTo(o2.getDate());
            }
        });
    }
}
