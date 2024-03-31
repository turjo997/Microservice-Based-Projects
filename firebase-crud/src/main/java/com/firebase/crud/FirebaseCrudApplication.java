package com.firebase.crud;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

@SpringBootApplication
public class FirebaseCrudApplication {

	public static void main(String[] args) throws IOException {

//		ClassLoader classLoader = FirebaseCrudApplication.class.getClassLoader();
//		File file = new File(Objects.requireNonNull(classLoader
//				.getResource("/serviceAccountKey.json")).getFile());

		InputStream serviceAccount = FirebaseCrudApplication.
				class.getResourceAsStream("/serviceAccountKey.json");

//		FileInputStream serviceAccount =
//				new FileInputStream(file.getAbsolutePath());


		FirebaseOptions options = new FirebaseOptions.Builder()
				.setCredentials(GoogleCredentials.fromStream(serviceAccount))
				.build();

		FirebaseApp.initializeApp(options);


		SpringApplication.run(FirebaseCrudApplication.class, args);
	}

}
