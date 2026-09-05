package com.saul.panelium.security;

import jakarta.validation.Valid; import jakarta.validation.constraints.*; import org.springframework.http.HttpStatus; import org.springframework.security.crypto.password.PasswordEncoder; import org.springframework.web.bind.annotation.*; import org.springframework.web.server.ResponseStatusException;

@RestController @RequestMapping("/api/auth")
public class AuthController {
  private final AppUserRepository users; private final PasswordEncoder encoder; private final JwtService jwt;
  AuthController(AppUserRepository users,PasswordEncoder encoder,JwtService jwt){this.users=users;this.encoder=encoder;this.jwt=jwt;}
  @PostMapping("/register") @ResponseStatus(HttpStatus.CREATED) Session register(@Valid @RequestBody Register body){if(users.existsByEmailIgnoreCase(body.email()))throw new ResponseStatusException(HttpStatus.CONFLICT,"El correo ya está registrado");return session(users.save(new AppUser(body.name(),body.email(),encoder.encode(body.password()),"READER")));}
  @PostMapping("/login") Session login(@Valid @RequestBody Login body){var user=users.findByEmailIgnoreCase(body.email()).orElseThrow(()->new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Credenciales inválidas"));if(!encoder.matches(body.password(),user.getPasswordHash()))throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Credenciales inválidas");return session(user);}
  private Session session(AppUser u){return new Session(jwt.create(u),new Profile(u.getId(),u.getName(),u.getEmail(),u.getRole()));}
  public record Login(@Email String email,@NotBlank String password){} public record Register(@NotBlank String name,@Email String email,@Size(min=8) String password){} public record Profile(Long id,String name,String email,String role){} public record Session(String token,Profile user){}
}
