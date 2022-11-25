package net.TrackYourFuture.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import net.TrackYourFuture.backend.model.Profile;
import net.TrackYourFuture.backend.repository.UserRepository;

@SpringBootApplication
public class BackendApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	@Autowired
    private UserRepository userRepository;

	@Override
	public void run(String... args) throws Exception {
		this.userRepository.save(new Profile("Adithya", "Thakur", "adithya@email.com"));
		this.userRepository.save(new Profile("Frederec", "Green", "fred@email.com"));
		this.userRepository.save(new Profile("Jonathan", "Karkour", "jon@email.com"));
	}
}
