package com.saul.panelium.security;

import jakarta.persistence.*;
import java.time.Instant;

@Entity @Table(name = "app_user")
public class AppUser {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
  private String name;
  private String email;
  @Column(name = "password_hash") private String passwordHash;
  @Column(name = "user_role") private String role;
  @Column(name = "created_at") private Instant createdAt = Instant.now();
  protected AppUser() {}
  public AppUser(String name, String email, String passwordHash, String role) { this.name=name; this.email=email.toLowerCase(); this.passwordHash=passwordHash; this.role=role; }
  public Long getId(){return id;} public String getName(){return name;} public String getEmail(){return email;} public String getPasswordHash(){return passwordHash;} public String getRole(){return role;}
}
