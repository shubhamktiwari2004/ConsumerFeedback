package CustomerFeedback.CF;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegisterRepo extends JpaRepository<RegisterModel , Long> {


    RegisterModel findByUsername(String username);
}
