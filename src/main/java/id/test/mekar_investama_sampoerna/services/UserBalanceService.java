package id.test.mekar_investama_sampoerna.services;

import id.test.mekar_investama_sampoerna.entities.UserBalance;
import id.test.mekar_investama_sampoerna.payloads.requests.AddUserBalanceRequest;
import id.test.mekar_investama_sampoerna.repositories.UserBalanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserBalanceService {
    @Autowired
    private UserBalanceRepository userBalanceRepo;

    public UserBalance addNew(AddUserBalanceRequest request) {
        UserBalance userBalance = new UserBalance(request.getBalanceNo(), request.getUser());
        return this.userBalanceRepo.save(userBalance);
    }
}
