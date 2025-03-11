package com.manel.aigles.service;
import com.manel.aigles.model.User;
import com.manel.aigles.repos.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User registerUser(User user) {
        // Ajouter une logique ici pour vérifier les données ou crypter le mot de passe
        return userRepository.save(user);
    }
}