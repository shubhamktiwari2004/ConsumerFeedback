package CustomerFeedback.CF;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;

@Controller
public class AdminController {

    @Autowired
    AdminService service;

    @GetMapping("/adminFeedback")
    public String feedback(Model model , HttpSession session){
        ArrayList<AddFeedbackModel> views =  service.view();
        System.out.println(views.toString());
        model.addAttribute("views" , views);

        return "admin";
    }

    @GetMapping("/deleteFeedback/{id}")
    public String delete(@PathVariable Long id
    ){
        service.delete(id);
        return "redirect:/adminFeedback";
    }

    @GetMapping("/adminFeedback/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/login";

    }



}
