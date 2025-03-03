package com.manel.aigles;
import com.manel.aigles.repos.FormDataRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import com.manel.aigles.model.FormData;
import com.manel.aigles.model.*;
@SpringBootTest
class AiglesSimacApplicationTests {
	private FormDataRepository formDataRepository;
	
	

	@Test
	public void testCreateEmp() {
	    // Créer une nouvelle instance de FormData
	    FormData f = new FormData("manel", "try", "trial", "directeur", "test", "tesssttt");
	    
	    // Sauvegarder l'entité dans le repository
	    formDataRepository.save(f);
	    
	   	}


}
