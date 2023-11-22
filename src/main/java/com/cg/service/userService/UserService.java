package com.cg.service.userService;



import com.cg.exception.ResourceNotFoundException;
import com.cg.model.Customer;
import com.cg.model.enums.ELock;
import com.cg.model.enums.ERole;
import com.cg.model.enums.EStatusCustomer;
import com.cg.repository.CustomerRepository;
import com.cg.repository.ImageRepository;
import com.cg.service.response.SelectOptionResponse;
import com.cg.service.userService.request.UserCreateRequest;
import com.cg.service.userService.request.UserEditRequest;
import com.cg.service.userService.response.UserEditResponse;
import com.cg.service.userService.response.UserListResponse;
import com.cg.until.AppMessage;
import com.cg.until.AppUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@Transactional

public class UserService {
    private ImageRepository imageRepository;
    private CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    public Optional<Customer> findByNameIgnoreCaseOrEmailIgnoreCaseOrPhone(String loginName) {
        return Optional.ofNullable(customerRepository.findByNameIgnoreCaseOrEmailIgnoreCaseOrPhone(loginName, loginName, loginName)
                .orElseThrow(() -> new ResourceNotFoundException
                        (String.format(AppMessage.ID_NOT_FOUND, "User"))));
    }

    public List<SelectOptionResponse> findAll(){
        return customerRepository.findAllByDeletedFalse().stream().map(
                        e-> new SelectOptionResponse(e.getId().toString(),e.getName()))
                .collect(Collectors.toList());
    }
    public Page<UserListResponse> findAllUser(String search, Pageable pageable){
        return customerRepository.searchAllByUserName(search,pageable)
                .map(e->UserListResponse.builder()
                        .id(e.getId())
                        .name(e.getName())
                        .phone(e.getPhone())
                        .email(e.getEmail())
                        .dob(String.valueOf(e.getDob()))
                        .type(String.valueOf(e.getStatusCustomer()))
                        .lock(String.valueOf(e.getELock()))
                        .role(String.valueOf(e.getRole()))
                        .build());
    }
    public Customer findById(Long id){
        return customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException
                (String.format(AppMessage.ID_NOT_FOUND, "User", id)));
    }
    public UserEditResponse showEdit(Long id){
        var user = findById(id);
        return AppUtil.mapper.map(user, UserEditResponse.class);
    }
    public Customer create(UserCreateRequest userCreateRequest){
        Customer user = AppUtil.mapper.map(userCreateRequest, Customer.class);
        user.setPassWord(passwordEncoder.encode(userCreateRequest.getPassWord()));
        user.setDeleted(false);
        user.setELock(ELock.UNLOCK);
        user.setRole(ERole.ROLE_USER);
        user.setStatusCustomer(EStatusCustomer.SILVER);
        return customerRepository.save(user);
    }
    public void update(UserEditRequest userEditRequest, Long id) {
        var userDB = findById(id);
        userDB.setName(userEditRequest.getName());
        userDB.setPhone(userEditRequest.getPhone());
        userDB.setDob(LocalDate.parse(userEditRequest.getDob()));
    }

    public void save(Customer customer){

        customerRepository.save(customer);

    }
    public void lockById(Long id){
        Customer user = findById(id);
        user.setDeleted(true);
        user.setELock(ELock.LOCK);
        customerRepository.save(user);
    }
    public void unlockById(Long id){
        Customer user = findById(id);
        user.setDeleted(false);
        user.setELock(ELock.UNLOCK);
        customerRepository.save(user);
    }
    public void roleAdmin(Long id){
        Customer user = findById(id);
        user.setRole(ERole.ROLE_ADMIN);
        customerRepository.save(user);
    }
    public void roleUser(Long id){
        Customer user = findById(id);
        user.setRole(ERole.ROLE_USER);
        customerRepository.save(user);
    }
    public Optional<Customer> getCurrentCustomer() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

// Kiểm tra xem người dùng đã đăng nhập hay chưa
        if (authentication != null && authentication.isAuthenticated()) {
            // Lấy thông tin người dùng đăng nhập hiện tại
            Object principal = authentication.getPrincipal();

            // Kiểm tra xem principal có phải là một UserDetails không
            if (principal instanceof UserDetails userDetails) {
                String username = userDetails.getUsername();
                // Thực hiện xử lý với thông tin người dùng
                return findByNameIgnoreCaseOrEmailIgnoreCaseOrPhone(username);
            }
        }
        return Optional.empty();
    }
}
