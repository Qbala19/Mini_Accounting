package pl.qbala.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.qbala.entity.Reminder;
import pl.qbala.repository.ReminderRepository;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.Arrays;


@Controller
public class ReminderController {

    @Autowired
    ReminderRepository reminderRepository;

    @PostMapping("/addReminder")
    public String reminder(HttpServletRequest request){
        Reminder reminder = new Reminder();
        reminder.setText(request.getParameter("text"));
        reminder.setDate(LocalDate.parse(request.getParameter("date")));
        reminderRepository.save(reminder);
        return "redirect:"+request.getContextPath()+"/";
    }

    @GetMapping("/deleteReminder")
    public String delete(HttpServletRequest request, @RequestParam(name = "id") Long id){
        reminderRepository.delete(id);
        return "redirect:"+request.getContextPath()+"/";
    }

}
