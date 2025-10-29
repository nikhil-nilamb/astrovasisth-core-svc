package com.vasisth.astrovasisth_core_svc.controller;

import com.vasisth.astrovasisth_core_svc.dto.CustomerResponse;
import com.vasisth.astrovasisth_core_svc.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getCustomer(@PathVariable String id) {
        return ResponseEntity.ok(customerService.getCustomer(id));
    }

    @PostMapping()
    public ResponseEntity<CustomerResponse> getCustomer(@RequestBody CustomerResponse customerResponse) {
        return ResponseEntity.ok(customerService.saveCustomer(customerResponse));
    }
}
