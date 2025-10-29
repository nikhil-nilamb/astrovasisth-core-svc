package com.vasisth.astrovasisth_core_svc.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/payments/")
public class PaymentController {

    @PostMapping("{id}")
    public Map<String,String> makePayment(@RequestBody Map<String,String> map){
        return map;
    }

    @PostMapping("{id}/success/{transId}")
    public Map<String,String> successPayment(@PathVariable("id") String id,@PathVariable("transId") String transId, @RequestBody Map<String,String> map){
        map.put("id",id);
        map.put("transId",transId);
        return map;
    }

    @PostMapping("{id}/failure/{transId}")
    public Map<String,String> failurePayment(@PathVariable("id") String id,@PathVariable("transId") String transId,@RequestBody Map<String,String> map){
        map.put("id",id);
        map.put("transId",transId);
        return map;
    }
}
