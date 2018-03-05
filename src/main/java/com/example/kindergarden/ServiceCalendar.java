package com.example.kindergarden;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

public class ServiceCalendar {

  private Calendar calendar = new GregorianCalendar(TimeZone.getDefault(), new Locale("dk", "DK"));
  int today = calendar.get(Calendar.DATE);
  private String[] months = {"Januar", "Februar", "Marts", "April", "Maj", "Juni", "Juli", "August", "September", "Oktober", "November", "December"};
  private int[] days = new int[42];


  private void listCalendar(){
    int counter = 0;

    /*--- Forrige måned ---*/
    //Sætter datoen til den første i måneden
    calendar.set(Calendar.DAY_OF_MONTH, 1);

    //Regner ud hvor mange dage der skal udskrives af den forrige måned
    int weekdays = (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY ? 6 : calendar.get(Calendar.DAY_OF_WEEK)-2);

    //Trækker dage fra kalenderen
    calendar.add(Calendar.DAY_OF_MONTH, -weekdays);

    for(int i = 0; i < weekdays; i++) {
      days[counter] = calendar.get(Calendar.DAY_OF_MONTH);
      calendar.add(Calendar.DATE, 1);
      counter++;
    }

    //Nuværende måned
    for (int i = 1; i <= calendar.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
      days[counter] = i;
      counter++;
    }

    //Næste måned
    for(int i = 1; counter < days.length; i++) {
      days[counter] = i;
      counter++;
    }
  }
  public String getMonthAndYear(){
    listCalendar();
    return String.valueOf(months[calendar.get(Calendar.MONTH)]+" "+calendar.get(Calendar.YEAR));
  }

  public int[] getDays(){
    listCalendar();
    return days;
  }

  public void decrement(){
    calendar.add(Calendar.MONTH, -1);
  }
  public void increment(){
    calendar.add(Calendar.MONTH, 1);
  }
}