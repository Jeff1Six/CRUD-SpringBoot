package com.github.jeff1Six.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.github.jeff1Six.dominio.Cliente;
import com.github.jeff1Six.repositories.ClienteRepository;
import com.github.jeff1Six.services.excpetion.ObjectNotFoundException;

@Service
public class AuthService {
	
	@Autowired
	private ClienteRepository cliRepo;
	
	@Autowired
	private BCryptPasswordEncoder pe;
	
	@Autowired
	private EmailService emailService;
	
	private Random rand =  new Random();
	
	public void sendNewPassword(String email) {
		
		Cliente cliente = cliRepo.findByEmail(email);
		
		if (cliente == null) {
			throw new ObjectNotFoundException("Email nao encontrado");
		}
		
		String newPass = newPassword();
		cliente.setSenha(pe.encode(newPass));
		cliRepo.save(cliente);
		emailService.sendNewPassowrdEmail(cliente, newPass);
		
	
	}

	private String newPassword() {
		char[] vet =  new char [10];
		for (int i=0; i<10; i++) {
			vet[i] = randomChar();
		}
		return new String(vet);
	}

	private char randomChar() {
		int  opt = rand.nextInt(3);
		if(opt==0){
			return (char) (rand.nextInt(10)+ 48);
		}else if (opt==1) {
			return (char) (rand.nextInt(26)+ 65);

		}else {
			return (char) (rand.nextInt(26)+ 97);

		}
	}
}
