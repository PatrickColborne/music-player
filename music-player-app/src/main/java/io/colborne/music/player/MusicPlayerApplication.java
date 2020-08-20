package io.colborne.music.player;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import io.colborne.music.player.bluetooth.MusicPlayerBluetoothServer;

@SpringBootApplication
public class MusicPlayerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MusicPlayerApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {

			System.out.println("Let's inspect the beans provided by Spring Boot:");

			MusicPlayerBluetoothServer server = new MusicPlayerBluetoothServer();
			server.initialise();
			server.start();
			server.makeBluetoothDeviceTemporarilyVisible();
			server.waitForClient();

		};
	}
}
