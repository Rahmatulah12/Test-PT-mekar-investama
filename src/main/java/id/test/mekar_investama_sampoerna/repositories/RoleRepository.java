package id.test.mekar_investama_sampoerna.repositories;

import id.test.mekar_investama_sampoerna.entities.ERole;
import id.test.mekar_investama_sampoerna.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
