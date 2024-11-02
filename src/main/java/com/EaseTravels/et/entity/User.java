package com.EaseTravels.et.entity;

import com.EaseTravels.et.models.types.Providers;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="user_details")
public class User {
    @Id
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "phone_no", unique = true)
    private String phoneNo;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "profile_pic", length = 10000)
    private String profilePic;

    @Column(name = "email_verified")
    private boolean emailVerified = false;

    @Column(name = "phoneNo_verified")
    private boolean phoneVerified = false;

    @Column(name = "provider_type")
    private String providerType= Providers.SELF.toString();

    @Column(name = "provider_id")
    private String providerId;
}
