package CustomerFeedback.CF;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AdminService{

    @Autowired
    AddFeedbackRepo addFeedbackRepo;

    public ArrayList<AddFeedbackModel> view() {
       return (ArrayList<AddFeedbackModel>) addFeedbackRepo.findAll();
    }

    public void delete(Long id) {
        addFeedbackRepo.deleteById(id);
    }
}
