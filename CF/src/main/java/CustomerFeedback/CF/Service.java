package CustomerFeedback.CF;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Optional;

@org.springframework.stereotype.Service
public class Service implements UserDetailsService {
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        RegisterModel user = registerRepo.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .build();
    }
}
