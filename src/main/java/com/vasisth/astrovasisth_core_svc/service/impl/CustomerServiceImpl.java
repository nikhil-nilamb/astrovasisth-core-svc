package com.vasisth.astrovasisth_core_svc.service.impl;

import com.vasisth.astrovasisth_core_svc.constants.ColleagueProfileStatus;
import com.vasisth.astrovasisth_core_svc.dto.CustomerResponse;
import com.vasisth.astrovasisth_core_svc.entity.Customer;
import com.vasisth.astrovasisth_core_svc.exception.CustomException;
import com.vasisth.astrovasisth_core_svc.repo.CustomerRepository;
import com.vasisth.astrovasisth_core_svc.service.CustomerService;
import com.vasisth.astrovasisth_core_svc.service.JwtService;
import com.vasisth.astrovasisth_core_svc.utilityService.UtilityService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@AllArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository customerRepository;
    private final UtilityService utilityService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public CustomerResponse validateLogin(String emailOrMobile) {
        if(!isValidEmailOrMobile(emailOrMobile)) {
            throw new CustomException("Invalid email or mobile number format");
        }

        Optional<Customer> customer = customerRepository.findByEmailOrMobileAndIsDeletedFalse(emailOrMobile, emailOrMobile);
        if(customer.isPresent()) {
            Customer customer1 = customer.get();
            customer1.setUpdatedAt(LocalDateTime.now());
            String otp = utilityService.manageOtp(customer1.getMobile(),customer1.getEmail());
            customer1.setOtp(passwordEncoder.encode(otp));
            customerRepository.save(customer1);
            return mapToCustomerResponse(customer1, customer1.getProfileStatus() != ColleagueProfileStatus.APPROVED , null);
        }else{
            boolean isEmail = isEmail(emailOrMobile);
            Customer customer1 = new Customer();
            customer1.setEmail(isEmail ? emailOrMobile : null);
            customer1.setMobile(isEmail ? null : emailOrMobile);
            customer1.setActive(true);
            customer1.setDeleted(false);
            customer1.setCreatedAt(LocalDateTime.now());
            customer1.setUpdatedAt(LocalDateTime.now());
            String otp = utilityService.manageOtp(isEmail ? null : emailOrMobile,isEmail ? emailOrMobile : null);
            customer1.setOtp(passwordEncoder.encode(otp));
            customerRepository.save(customer1);
            return mapToCustomerResponse(customer1,customer1.getProfileStatus() != ColleagueProfileStatus.APPROVED , null);
        }
    }

    @Override
    public CustomerResponse validateOtp(String emailOrMobile, String otp) {
        if(!isValidEmailOrMobile(emailOrMobile)) {
            throw new CustomException("Invalid email or mobile number format");
        }
        Optional<Customer> customer = customerRepository.findByEmailOrMobileAndIsDeletedFalse(emailOrMobile, emailOrMobile);
        if(customer.isPresent()) {
            Customer customer1 = customer.get();
            if(passwordEncoder.matches(otp,customer1.getOtp())){
                customer1.setOtp(null);
                customer1.setUpdatedAt(LocalDateTime.now());
                customerRepository.save(customer1);
                String token = jwtService.generateToken(customer1.getId().toString());
                return mapToCustomerResponse(customer1,customer1.getProfileStatus() != ColleagueProfileStatus.APPROVED,token);
            }else{
                throw new CustomException("Invalid OTP");
            }
        }else{
            throw new CustomException("User not found");
        }
    }

    @Override
    public CustomerResponse getCustomer(String id) {
        Optional<Customer> customer = customerRepository.findById(java.util.UUID.fromString(id));
        if(customer.isPresent()) {
            return mapToCustomerResponse(customer.get(),customer.get().getProfileStatus() != ColleagueProfileStatus.APPROVED , null);
        }else{
            throw new CustomException("User not found");
        }
    }

    @Override
    public CustomerResponse saveCustomer(CustomerResponse customerResponse) {
        Customer customer = customerRepository.findById(customerResponse.getId())
                .orElseThrow(() -> new CustomException("User not found"));
        customer.setId(customerResponse.getId());
        customer.setFirstName(customerResponse.getFirstName());
        customer.setLastName(customerResponse.getLastName());
        customer.setEmail(customerResponse.getEmail());
        customer.setAddress(customerResponse.getAddress());
        customer.setDob(customerResponse.getDob());
        customer.setActive(true);
        customer.setDeleted(false);
        customer.setProfileStatus(ColleagueProfileStatus.APPROVED);
        customer.setUpdatedAt(LocalDateTime.now());
        customer.setGender(customerResponse.getGender());
        Customer savedCustomer = customerRepository.save(customer);
        return mapToCustomerResponse(savedCustomer,savedCustomer.getProfileStatus() != ColleagueProfileStatus.APPROVED , null);
    }

    private boolean isEmail(String emailOrMobile) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return emailOrMobile != null && emailOrMobile.matches(emailRegex);

    }

    private CustomerResponse mapToCustomerResponse(Customer customer,boolean isNewUser,String token) {
        CustomerResponse response = new CustomerResponse();
        response.setId(customer.getId());
        response.setFirstName(customer.getFirstName());
        response.setLastName(customer.getLastName());
        response.setEmail(customer.getEmail());
        response.setMobile(customer.getMobile());
        response.setNewUser(isNewUser);
        response.setToken(token);
        return response;
    }

    private boolean isValidEmailOrMobile(String emailOrMobile) {
        return isValidEmail(emailOrMobile) || isValidMobile(emailOrMobile);
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email != null && email.matches(emailRegex);
    }
    private boolean isValidMobile(String mobile) {
        String mobileRegex = "^[0-9]{10}$";
        return mobile != null && mobile.matches(mobileRegex);
    }
}
