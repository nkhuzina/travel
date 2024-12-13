package com.tgog.service;

import com.tgog.config.AppProporties;
import com.tgog.constants.Roles;
import com.tgog.constants.UserStatus;
import com.tgog.model.User;
import com.tgog.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    AppProporties appProporties;

    public boolean createNewUser(User user){
        boolean isSaved = false;
        user.setRole(Roles.ROLE_MODERATOR);
        user.setPwd(passwordEncoder.encode(user.getPwd()));
        user.setStatus(UserStatus.ACTIVE);
        user = userRepository.save(user);
        if (null != user && user.getUserId() > 0)
        {
            isSaved = true;
        }
        return isSaved;
    }

    public Page<User> findAllUsers(int pageNum, String sortField, String sortDir){
        int pageSize = appProporties.getPageSize();
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize,
                sortDir.equals("asc") ? Sort.by(sortField).ascending()
                        : Sort.by(sortField).descending());
        Page<User> page = userRepository.findByRoleNotInCustom(
                List.of(Roles.ROLE_ADMIN, Roles.ANONYMOUS),
                pageable
        );
        return page;
    }

    public User updateUser(User user){
        User updatedUser = userRepository.readByEmail(user.getEmail());
        updatedUser.setName(user.getName());
        updatedUser.setMobileNumber(user.getMobileNumber());
        updatedUser.setPwd(passwordEncoder.encode(user.getPwd()));
        return userRepository.save(updatedUser);
    }

    public boolean updateUserStatus(int userId){
        boolean isUpdated = false;
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            String status = user.get().getStatus().equals(UserStatus.ACTIVE) ?
                    UserStatus.DISABLED:UserStatus.ACTIVE;
            userRepository.updateUserStatus(status, userId);
            isUpdated = true;
        }
        return isUpdated;
    }
}
