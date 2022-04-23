package id.test.mekar_investama_sampoerna.repositories;

import id.test.mekar_investama_sampoerna.entities.RefreshToken;
import id.test.mekar_investama_sampoerna.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(String token);

    @Modifying
    int deleteByUser(User user);

}
