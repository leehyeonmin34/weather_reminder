package com.leehyeonmin34.weather_reminder.domain.user.service;

import com.leehyeonmin34.weather_reminder.domain.user.domain.User;
import com.leehyeonmin34.weather_reminder.domain.user.dto.UserGetResponse;
import com.leehyeonmin34.weather_reminder.domain.user.repository.UserRepository;
import com.leehyeonmin34.weather_reminder.global.common.model.DeleteResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserGetResponse createUser(){
        User user = new User.Builder().build();
        User saved = userRepository.save(user);
        return new UserGetResponse(saved);
    }

    public DeleteResponse deleteUser(Long id){
        userRepository.deleteById(id);
        return new DeleteResponse("user", id);
    }

}
