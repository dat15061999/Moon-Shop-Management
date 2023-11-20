package com.cg.service.userService;


import com.cg.exception.ResourceNotFoundException;
import com.cg.model.Customer;
import com.cg.model.enums.ELock;
import com.cg.model.enums.ERole;
import com.cg.model.enums.EStatusCustomer;
import com.cg.repository.CustomerRepository;
import com.cg.service.userService.request.UserEditRequest;
import com.cg.service.userService.request.UserRegisterRequest;
import com.cg.service.userService.request.UserSaveRequest;
import com.cg.service.userService.response.UserListResponse;
import com.cg.service.userService.response.UserRegisterResponse;
import com.cg.until.AppMessage;
import com.cg.until.AppUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class UserService {
    private final CustomerRepository userRepository;

    public Page<UserListResponse> getAll(String search , Pageable pageable) {
        search = "%"+search+"%";
        return userRepository.searchAllUser(search, pageable)
                .map(user -> UserListResponse.builder()
                        .id(user.getId())
                        .name(user.getName())
                        .email(user.getEmail())
                        .phone(user.getPhone())
                        .dob(user.getDob())
                        .statusCustomer(String.valueOf(user.getStatusCustomer()))
                        .eLock(String.valueOf(user.getELock()))
                        .build());
    }
    public Customer findById(Long id){
        //de tai su dung
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException
                        (String.format(AppMessage.ID_NOT_FOUND, "User", id)));
    }
    public void deleteById(Long id){
        Customer user = findById(id);
        userRepository.delete(user);
    }
    public void create(UserSaveRequest request){
        var user = AppUtil.mapper.map(request, Customer.class);
        user.setRole(ERole.ROLE_USER);
        user.setELock(ELock.UNLOCK);
        userRepository.save(user);
    }
//    public UserEditResponse findByIdUser(Long id){
//        var user = userRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException
//                        (String.format(AppMessage.ID_NOT_FOUND, "User", id)));
//        var result = AppUtil.mapper.map(user, UserEditResponse.class);
//        result.setStatusCustomer(user.getStatusCustomer());
//        result.setERole(user.getRole());
//        result.setELock(user.getELock());
//        return result;
//    }
    public void changeLockStatus(Long id, ELock eLock){
        var user = findById(id);
        user.setELock(eLock);
        userRepository.save(user);
    }
    @Transactional
    public void update(UserEditRequest request, Long id) throws Exception {
        var userInDb = findById(id);
        if(!Objects.equals(userInDb.getPassWord(), request.getOldPassword())){
            throw new Exception("Incorrect Password");
        }//        user.setELock(ELock.valueOf(request.getELock()));
        userInDb.setStatusCustomer(EStatusCustomer.valueOf(request.getStatusCustomer()));
        AppUtil.mapper.map(request, userInDb);
        request.setId(id.toString());
        userRepository.save(userInDb);


    }

    public List<UserRegisterResponse> getAllUserRegister() {
        return userRepository.findAll()
                .stream()
                .map(service -> UserRegisterResponse.builder()
                        .id(service.getId())
                        .name(service.getName())
                        .email(service.getEmail())
                        .passWord(service.getPassWord())
                        // Chuyển thành chuỗi
                        .build())
                .collect(Collectors.toList());
    }

    public void createRegister(UserRegisterRequest request){
        var user = AppUtil.mapper.map(request, Customer.class);
        user.setRole(ERole.ROLE_USER);
        userRepository.save(user);
    }

    public Optional<Customer> findByNameIgnoreCaseOrEmailIgnoreCaseOrPhone(String loginName) {
        return Optional.ofNullable(userRepository.findByNameIgnoreCaseOrEmailIgnoreCaseOrPhone(loginName, loginName, loginName)
                .orElseThrow(() -> new ResourceNotFoundException
                        (String.format(AppMessage.ID_NOT_FOUND, "User"))));
    }
}

