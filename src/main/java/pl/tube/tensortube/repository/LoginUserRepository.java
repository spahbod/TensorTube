package pl.tube.tensortube.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.tube.tensortube.model.LoginUser;

public interface LoginUserRepository extends JpaRepository<LoginUser, Long> {
    LoginUser findByUserIdAndPassOrderById(String userId, String pass);
}
