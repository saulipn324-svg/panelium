package com.saul.panelium.security;
import org.springframework.beans.factory.annotation.Value; import org.springframework.boot.CommandLineRunner; import org.springframework.security.crypto.password.PasswordEncoder; import org.springframework.stereotype.Component;
@Component public class AdminBootstrap implements CommandLineRunner{
 private final AppUserRepository users; private final PasswordEncoder encoder; private final String email,password;
 AdminBootstrap(AppUserRepository users,PasswordEncoder encoder,@Value("${panelium.admin-email}")String email,@Value("${panelium.admin-password}")String password){this.users=users;this.encoder=encoder;this.email=email;this.password=password;}
 public void run(String...args){if(!users.existsByEmailIgnoreCase(email))users.save(new AppUser("Administrador",email,encoder.encode(password),"ADMIN"));}
}
