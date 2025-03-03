package com.manel.aigles.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class FormData {
    private String nomPrenom;
    private String classe;
    private String echelon;
    private String grade;
    private String acte;
    
    @Id
    private String matricule;

    // Constructeur sans argument (obligatoire pour Hibernate)
    public FormData() {
        // Si nécessaire, tu peux initialiser certains champs ici
    }

    // Constructeur avec arguments
    public FormData(String nomPrenom, String matricule, String classe, String echelon, String grade, String acte) {
        super();
        this.nomPrenom = nomPrenom;
        this.classe = classe;
        this.echelon = echelon;
        this.grade = grade;
        this.acte = acte;
        this.matricule = matricule;
    }

    // Getters et setters
    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public String getEchelon() {
        return echelon;
    }

    public void setEchelon(String echelon) {
        this.echelon = echelon;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getActe() {
        return acte;
    }

    public void setActe(String acte) {
        this.acte = acte;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getNomPrenom() {
        return nomPrenom;
    }

    public void setNomPrenom(String nomPrenom) {
        this.nomPrenom = nomPrenom;
    }
}