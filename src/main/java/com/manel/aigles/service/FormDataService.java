package com.manel.aigles.service;

import com.manel.aigles.model.FormData;

public interface FormDataService {
    FormData saveEmp(FormData f);
    FormData updateEmp(FormData f);
    FormData getEmp(String matricule);
    FormData addFormData(FormData formData);
    FormData createEmp(FormData formData);
}
