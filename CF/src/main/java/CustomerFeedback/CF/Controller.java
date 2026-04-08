package CustomerFeedback.CF;


import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.model.IModel;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;

@org.springframework.stereotype.Controller
public class Controller {

    @Autowired
    private Service service;

    @GetMapping("/register")
    public String register(){
        return "register";
    }
    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/feedback")
    public String feedback(Model model ,HttpSession session){

        String username = (String) session.getAttribute("username");
        ArrayList<AddFeedbackModel> views =  service.view(username);
        System.out.println(views.toString());
        model.addAttribute("views" , views);

        return "feedback";
    }

    @GetMapping("/addFeedback")
    public String add(Model model){
        return "add";
    }

    @PostMapping("/register")
    public String register(@RequestParam String username,
                           @RequestParam String password ,
                           @RequestParam String email
    ){
        RegisterModel registerModel = new RegisterModel();
        registerModel.setUsername(username);
        registerModel.setEmail(email);
        registerModel.setPassword(password);
        service.register(registerModel);
        return "redirect:/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                           @RequestParam String password ,
                        HttpSession session
    ){
        String res = service.login(username , password);
        if (res.equals("success")) {
            session.setAttribute("username" , username);
            System.out.println("Mass : "+session.getAttribute("username"));
            return "redirect:/feedback";
        }
        else{
            return "redirect:/login?error";
        }
    }




    @PostMapping("/addFeedback")
    public String add(@RequestParam String message , HttpSession session
    ){

        String username = (String) session.getAttribute("username");
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDate = localDateTime.format(formatter);
        AddFeedbackModel add = new AddFeedbackModel();
        add.setMessage(message);
        add.setUsername(username);
        add.setTime(formattedDate);
        service.add(add);
        return "redirect:/feedback";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id , Model model
    ){
        Optional<AddFeedbackModel> optional = service.edit(id);

        if (optional.isPresent()) {
            model.addAttribute("addFeedbackModel", optional.get());
        } else {
            return "redirect:/feedback"; // if not found
        }

        return "edit";
    }

    @PostMapping("/editFeedback")
    public String updateFeedback(@RequestParam String message ,
                                 @RequestParam Long id ,
                                 HttpSession session
    ){
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDate = localDateTime.format(formatter);

        service.updateFeedback(message , id , session , formattedDate);
        if(session.getAttribute("username")== null){
            return "redirect:/adminFeedback";
        }
        return "redirect:/feedback";
    }



    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/login";

    }



}
