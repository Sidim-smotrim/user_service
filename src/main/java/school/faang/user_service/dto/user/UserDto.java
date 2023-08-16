package school.faang.user_service.dto.user;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import school.faang.user_service.entity.Country;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private long id;
    @NotBlank
    @Size(min = 1, max = 64)
    private String username;
    @Email
    @NotBlank
    private String email;
    @NotBlank
    @Max(128)
    private String password;
    @Size(min =9, max = 15)
    private String phone;
    @Max(4096)
    private String aboutMe;
    private Country country;
    @Max(64)
    private String city;
    private Integer experience;
}
