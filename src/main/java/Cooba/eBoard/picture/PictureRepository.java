package Cooba.eBoard.picture;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PictureRepository extends MongoRepository<Picture,String> {
    List<Picture> findByFilename(String filename);
    List<Picture> findByEnabled(Boolean enabled);
}
