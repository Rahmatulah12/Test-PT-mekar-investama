package id.test.mekar_investama_sampoerna.controllers;

import id.test.mekar_investama_sampoerna.entities.Transaction;
import id.test.mekar_investama_sampoerna.entities.User;
import id.test.mekar_investama_sampoerna.payloads.requests.TransactionRequest;
import id.test.mekar_investama_sampoerna.payloads.response.ResponseData;
import id.test.mekar_investama_sampoerna.securities.jwt.JwtUtils;
import id.test.mekar_investama_sampoerna.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @Autowired
    private JwtUtils jwtUtils;

    @GetMapping("/api/transactions")
    public ResponseEntity<?> findAll(@RequestHeader("Authorization") String token)
    {
        String[] explodeToken = token.split(" ");
        String username = jwtUtils.getUserNameFromJwtToken(explodeToken[1]);
        List<Transaction> transactions = transactionService.findAll(username);
        return ResponseEntity.ok().body(transactions);
    }

    @GetMapping("/api/transactions/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return ResponseEntity.ok().body(transactionService.findById(id));
    }

    @PostMapping("/api/transactions")
    public ResponseEntity<ResponseData<Transaction>> saveOrUpdate(
        @Valid @RequestBody TransactionRequest request, Errors errors
    ) {
        ResponseData<Transaction> response = new ResponseData<>();
        if(errors.hasErrors()) {
            for(ObjectError error : errors.getAllErrors()) {
                response.getMessages().add(error.getDefaultMessage());
            }
            response.setStatus(false);
            response.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        response.setStatus(false);
        response.setPayload(transactionService.saveOrUpdate(request));
        response.setMessages(null);

        return ResponseEntity.ok().body(response);
    }
}
