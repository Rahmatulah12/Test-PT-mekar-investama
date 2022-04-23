package id.test.mekar_investama_sampoerna.repositories;

import id.test.mekar_investama_sampoerna.entities.Transaction;
import id.test.mekar_investama_sampoerna.entities.User;
import id.test.mekar_investama_sampoerna.entities.UserBalance;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findBySenderId(Long userId);
}
