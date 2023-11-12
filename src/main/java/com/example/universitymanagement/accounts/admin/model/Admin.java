package com.example.universitymanagement.accounts.admin.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Admin {
    @Id
    private String email;
    private String password;
    public boolean compare(String name,String password){
        return name.equals(getEmail())&&password.equals(getPassword());
    }
}
