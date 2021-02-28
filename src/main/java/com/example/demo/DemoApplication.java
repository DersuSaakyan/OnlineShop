package com.example.demo;

import com.example.demo.model.User;
import com.example.demo.model.UserGender;
import com.example.demo.model.UserType;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {
    @Autowired
    private UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        if(!userRepository.existsUserByEmail("an@mail.ru")) {
            User user = User.builder()
                    .name("Anna")
                    .surname("Anyan")
                    .email("an@mail.ru")
                    .phone("+37491345689")
                    .picUrl("default.png")
                    .password(new BCryptPasswordEncoder().encode("12345678"))
                    .gender(UserGender.MALE)
                    .verify(true)
                    .token(null)
                    .age(20)
                    .code(0)
                    .type(UserType.ADMIN)
                    .build();
            userRepository.save(user);
        }
    }
}
