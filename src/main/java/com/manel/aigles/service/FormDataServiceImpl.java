package com.manel.aigles.service;

import com.manel.aigles.model.FormData;
import com.manel.aigles.repos.FormDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FormDataServiceImpl implements FormDataService {

    @Autowired
    private FormDataRepository formDataRepository;

    @Override
    public FormData saveEmp(FormData f) {
        return formDataRepository.save(f);
    }

    @Override
    public FormData updateEmp(FormData f) {
        return formDataRepository.save(f);
    }

    @Override
    public FormData getEmp(String matricule) {
        return formDataRepository.findByMatricule(matricule);
    }

    @Override
    public FormData addFormData(FormData formData) {
        return formDataRepository.save(formData);
    }

    @Override
    public FormData createEmp(FormData formData) {
        return formDataRepository.save(formData);
    }
}
