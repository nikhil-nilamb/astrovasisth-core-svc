package com.vasisth.astrovasisth_core_svc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

@EnableWebSocket
@SpringBootApplication
public class AstrovasisthCoreSvcApplication {

	public static void main(String[] args) {
		SpringApplication.run(AstrovasisthCoreSvcApplication.class, args);
	}

}
