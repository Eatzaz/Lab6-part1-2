package com.example.lab6.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.boot.web.embedded.jetty.JettyServerCustomizer;

import java.util.Date;

@Data
@AllArgsConstructor
public class Employee {
    @NotEmpty(message = "The ID should be not Empty")
    @Size(min=2,max=10,message = "The ID Length must be more than 2")
    private String id;
    @NotEmpty(message = "The name should be not Empty")
    @Size(min=4,max=10,message = "The name Length must be more than 4")
  @Pattern(regexp = ".*[a-zA-Z]")
    private String name;
    @Email
    private String email;
   @Pattern(regexp ="05[0-9]{8}")
    private String phoneNumber;
    @NotNull(message= "The Age should be not Null")
    @Min(value = 25)
    @Max(value = 60)
    private int age;
    @NotEmpty(message = "The position should be not Empty")
    @Pattern(regexp ="supervisor|cordinator")
    private String position;
   @AssertFalse
    private boolean onLeave;
    @FutureOrPresent
    @JsonFormat(pattern="yyyy-mm-dd")
    private Date hireDate;
    @NotNull(message = "The annual Leave should be not Null")
    @PositiveOrZero
    private int annualLeave;
}
