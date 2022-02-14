package com.bookingflight.bookingflight.startup;

import com.bookingflight.bookingflight.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class Startup implements CommandLineRunner {

    private final UserRepository userRepository;

    public Startup(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


        System.out.println("Running...");
    }
}
