package com.accenture.modulosPago.clients;

import com.accenture.modulosPago.entities.Account;
import com.accenture.modulosPago.models.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "userMicroService", url = "localhost:8001")
public interface UserClientRest {
    @GetMapping("/api/user/list")
    public List<User> findAll();

    @GetMapping("/api/user/list/{id}")
    public User getById(@PathVariable Long id);

    @PostMapping("/api/user/AddAccountToUser")
    public ResponseEntity<Object> addAccountToUser(@RequestBody Account account);
}
