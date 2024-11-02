package com.EaseTravels.et.forms;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserForm {
    @NotBlank(message = "First Name is required")
    @Size(min = 3, message = "Min 3 Characters is required")
    private String firstName;
    @NotBlank(message = "Last Name is required")
    @Size(min = 3, message = "Min 3 Characters is required")
    private String lastName;
    @NotBlank(message = "Email is required")
    @Size(min = 3, message = "Min 3 Characters is required")
    private String email;
    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Min 6 Characters is required")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{6,20}$", message = "Password must contain at least one digit, one lowercase, one uppercase, one special character and have 6 to 20 characters")
    private String password;
    @NotBlank(message = "Confirm Password is required")
    private String confirmPassword;
    @NotBlank(message = "Phone Number is required")
    @Pattern(regexp = "^[6-9][0-9]{9}$", message = "Invalid phone number. It must start with 6-9 and have 10 digits")
    private String phoneNo;
}
