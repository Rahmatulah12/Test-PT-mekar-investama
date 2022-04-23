package id.test.mekar_investama_sampoerna.repositories;

import id.test.mekar_investama_sampoerna.entities.User;
import id.test.mekar_investama_sampoerna.entities.UserBalance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserBalanceRepository extends JpaRepository<UserBalance, Long> {
    UserBalance findByBalanceNo(String balanceNo);
}
