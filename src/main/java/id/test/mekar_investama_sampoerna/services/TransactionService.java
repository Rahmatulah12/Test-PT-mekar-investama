package id.test.mekar_investama_sampoerna.services;

import id.test.mekar_investama_sampoerna.entities.Transaction;
import id.test.mekar_investama_sampoerna.entities.User;
import id.test.mekar_investama_sampoerna.entities.UserBalance;
import id.test.mekar_investama_sampoerna.payloads.requests.TransactionRequest;
import id.test.mekar_investama_sampoerna.payloads.response.ResponseData;
import id.test.mekar_investama_sampoerna.repositories.TransactionRepository;
import id.test.mekar_investama_sampoerna.repositories.UserBalanceRepository;
import id.test.mekar_investama_sampoerna.repositories.UserRepository;
import id.test.mekar_investama_sampoerna.securities.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserBalanceRepository userBalanceRepository;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    public List<Transaction> findAll(String username) {
        User user = userRepository.findByUsername(username).orElse(null);
        UserBalance userBalance  = userBalanceRepository.findByBalanceNo(user.getPhoneNo());
        List<Transaction> transactions = transactionRepository.findBySenderId(userBalance.getId());
        return transactions;
    }

    public Transaction findById(Long id) {
        return transactionRepository.findById(id).orElse(null);
    }

    @Transactional
    public Transaction saveOrUpdate(TransactionRequest request) {
        UserBalance sender = userBalanceRepository.findByBalanceNo(request.getSender());
        UserBalance receiver = userBalanceRepository.findByBalanceNo(request.getReceiver());

        Integer balanceInput = request.getBalance();
        Integer balanceSender = sender.getBalance();
        Integer balanceReceiver = receiver.getBalance();

        if(balanceSender < balanceInput) {
            return null;
        }

        Transaction transaction = new Transaction();
        transaction.setSender(sender);
        transaction.setReceiver(receiver);
        transaction.setType(request.getType());
        transaction.setBalance(balanceInput);

        transactionRepository.save(transaction);

        sender.setBalance(balanceSender - balanceInput);
        userBalanceRepository.save(sender);
        receiver.setBalance(balanceReceiver + balanceInput);
        userBalanceRepository.save(receiver);

        return transaction;
    }

}
