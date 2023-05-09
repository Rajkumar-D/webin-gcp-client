package uk.ac.ebi.webingcpclient;

import com.google.cloud.secretmanager.v1.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.IOException;

@SpringBootApplication
public class WebinGcpClientApplication implements CommandLineRunner {

	@Autowired
	TestSecurityManager secManager;

	

	public static void main(String[] args) throws IOException {
		
		SpringApplication.run(WebinGcpClientApplication.class, args);
		
		
	}
	


	@Override
	public void run(String... args) throws Exception {
		secManager.getSecret();
	}
}
