package id.test.mekar_investama_sampoerna.payloads.requests;

import id.test.mekar_investama_sampoerna.entities.User;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddUserBalanceRequest {

    @NotBlank
    @Size(max = 20)
    private String balanceNo;

    @NotBlank
    private User user;

}
