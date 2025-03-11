package com.manel.aigles;
import com.manel.aigles.repos.FormDataRepository;
import com.manel.aigles.repos.UserRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.manel.aigles.model.FormData;
import com.manel.aigles.model.*;

@SpringBootTest
class AiglesSimacApplicationTests {
	@Autowired
	private FormDataRepository formDataRepository;
	@Autowired
    private UserRepository userRepository;
	

	@Test
	public void testCreateEmp() {
	    // Créer une nouvelle instance de FormData
	    FormData f = new FormData("manel", "try", "trial", "directeur", "test", "tesssttt");
	    
	    // Sauvegarder l'entité dans le repository
	    formDataRepository.save(f);
	    
	   	}
	@Test
	public void testCreateUser() {
		User u =new User((long) 1,"Manel","man@gmail.com","1234");
		userRepository.save(u);
	}

}
