package CustomerFeedback.CF;


import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface AddFeedbackRepo extends JpaRepository<AddFeedbackModel , Long> {
    ArrayList<AddFeedbackModel> findAllByUsername(String username);

    @Transactional
    @Modifying
    @Query(value = "UPDATE feedback SET message = ?2  WHERE id = ?1" ,  nativeQuery = true)
    void updateById(Long id , String message);

}
