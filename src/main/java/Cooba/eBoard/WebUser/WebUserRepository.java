package Cooba.eBoard.WebUser;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WebUserRepository extends MongoRepository<WebUser, String> {
    Optional<WebUser> findByEmail(String email);
}
