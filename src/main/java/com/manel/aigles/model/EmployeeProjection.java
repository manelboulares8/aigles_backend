package com.manel.aigles.model;

import org.springframework.data.rest.core.config.Projection;

@Projection(name = "matricule", types = { FormData.class })
public interface EmployeeProjection {
    String getMatricule();
    String getNomPrenom();
    String getGrade();
    String getEchelon();
    String getClasse();
    String getActe();
}
