package com.WebProject.WebService.repository.user;
import com.WebProject.WebService.entity.User;
import java.util.List;

public interface UserCustomRepository {
    List<User> searchUsers(String username, String email);
}
