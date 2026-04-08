package CustomerFeedback.CF;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Optional;

@org.springframework.stereotype.Service
public class Service {
    @Autowired
    private RegisterRepo registerRepo;

    @Autowired
    private AddFeedbackRepo addFeedbackRepo;

    public void register(RegisterModel registerModel) {
        registerRepo.save(registerModel);
    }

    public String login(String username, String password) {

        RegisterModel registerModel = registerRepo.findByUsername(username);
        if(registerModel != null && registerModel.getPassword().equals(password)){
            return "success";
        }
        return "error";


    }

    public void add(AddFeedbackModel add) {
        addFeedbackRepo.save(add);
    }

    public  ArrayList<AddFeedbackModel> view(String username){
        ArrayList<AddFeedbackModel> model = (ArrayList<AddFeedbackModel>) addFeedbackRepo.findAllByUsername(username);
        return model;
    }

    public Optional<AddFeedbackModel> edit(Long id) {
        Optional<AddFeedbackModel> feed = addFeedbackRepo.findById(id);
        return feed;
    }

    public void updateFeedback(String message, Long id , HttpSession session , String dateTime) {
        AddFeedbackModel addFeedbackModel = new AddFeedbackModel();
        addFeedbackModel.setId(id);
        addFeedbackModel.setMessage(message);
        addFeedbackModel.setTime(dateTime);
        addFeedbackModel.setUsername((String) session.getAttribute("username"));

        if (session.getAttribute("username") != null) {

            addFeedbackRepo.save(addFeedbackModel);
        }
        else{
            addFeedbackRepo.updateById(id , message);

        }
    }
}
