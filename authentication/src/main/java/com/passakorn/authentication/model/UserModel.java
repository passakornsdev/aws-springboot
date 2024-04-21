package com.passakorn.authentication.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {

    @Id
    private String id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
}
