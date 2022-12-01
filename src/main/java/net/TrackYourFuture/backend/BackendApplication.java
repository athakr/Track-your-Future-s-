package net.TrackYourFuture.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import net.TrackYourFuture.backend.model.Profile;
import net.TrackYourFuture.backend.repository.UserRepository;

@SpringBootApplication()
public class BackendApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	@Autowired
    private UserRepository userRepository;

	@Override
	public void run(String... args) throws Exception {
		Budget bud1 = new Budget();
		Budget bud2 = new Budget();
		Budget bud3 = new Budget();
		Payment pay1 = new Payment();
		Payment pay2 = new Payment();
		Payment pay3 = new Payment();
		Profile prof1 = new Profile("Adithya", "Thakur", "adithya@email.com", bud1, pay1);
		Profile prof2 = new Profile("Frederec", "Green", "fred@email.com", bud2, pay2);
		Profile prof3 = new Profile("Jonathan", "Karkour", "jon@email.com", bud3, pay3);
		
		this.userRepository.save(prof1);
		this.userRepository.save(prof2);
		this.userRepository.save(prof3);
	}
}
