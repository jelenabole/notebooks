package hr.tvz.bole.other;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordGenerator {
	public static void main(String[] args) {
		String password = "password";
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(password);
		System.out.println(hashedPassword);
	}
	
	public static String generatePassword(String string) {
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashed = passwordEncoder.encode(string);
		return hashed;
	}
}