package com.example.springsecurity.service;

import com.example.springsecurity.UserInfoDetailService;
import com.example.springsecurity.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.example.springsecurity.repository.UserRepository

import java.util.Optional;

@Service
public class userService {
    private final UserRepository repository;

    @Autowired
    public UserInfoService(UserRepository userInfoRepository) {
        this.repository = userInfoRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userInfo = repository.findByUsername(username);
        return userInfo.map(UserInfoDetailService::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }
    public String addUser(User userInfo) {
        // Mã hóa mật khẩu trước khi lưu
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        // Lưu người dùng vào cơ sở dữ liệu
        repository.save(userInfo);
        return "Thêm user thành công!";
    }
}
