package com.manel.aigles.repos;

import com.manel.aigles.model.FormData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FormDataRepository extends JpaRepository<FormData, String> {
    FormData findByMatricule(String matricule);
}
