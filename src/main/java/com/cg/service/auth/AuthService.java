package com.cg.service.auth;



import com.cg.model.Customer;
import com.cg.model.Image;
import com.cg.model.enums.ELock;
import com.cg.model.enums.ERole;
import com.cg.model.enums.EStatusCustomer;
import com.cg.repository.CustomerRepository;
import com.cg.repository.ImageRepository;
import com.cg.service.auth.request.RegisterRequest;
import com.cg.until.AppUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.awt.*;
import java.util.ArrayList;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthService implements UserDetailsService {

    private final CustomerRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    private ImageRepository imageRepository;

    public Customer register(RegisterRequest request){
        Customer user = AppUtil.mapper.map(request, Customer.class);
        Image avatar = imageRepository.findById(request.getAvatar().getId()).get();
        user.setAvatar(avatar);
        user.setRole(ERole.ROLE_USER);
        user.setPassWord(passwordEncoder.encode(user.getPassWord()));
        user.setDeleted(false);
        user.setELock(ELock.UNLOCK);
        user.setStatusCustomer(EStatusCustomer.SILVER);
      return   userRepository.save(user);
    }



    public Optional<Customer> getCustomerByName(String name){
        return userRepository.getCustomerByName(name);
    };
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer user = userRepository.findByNameIgnoreCaseOrEmailIgnoreCaseOrPhone(username,username,username)
                .orElseThrow(() -> new LockedException("Mật khẩu hoặc tên đăng nhập không hợp lê") );
        if (user.getELock() == ELock.LOCK) {
            throw new LockedException("Tài khoản đã bị khóa");
        }

        var role = new ArrayList<SimpleGrantedAuthority>();
        role.add(new SimpleGrantedAuthority(user.getRole().toString()));

        return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassWord(), role);
    }
    // để làm 1. kiểm tra xem user có tồn tại trong hệ thông hay không và tìm bằng 3 field Username Email PhoneNumber
    // 2. Nếu có thì sẽ trả về User của .security.core.userdetails.User để nó lưu vô kho spring sercurity context holder
    // 3. nếu không thì trả ra message lỗi User not Exist
}