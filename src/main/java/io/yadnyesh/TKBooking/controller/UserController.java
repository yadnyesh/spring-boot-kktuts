package io.yadnyesh.TKBooking.controller;

import io.yadnyesh.TKBooking.model.User;
import io.yadnyesh.TKBooking.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserService userService;

    @PostMapping(value = "/users", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = "application/json")
    public ResponseEntity saveUsers(@RequestParam(value = "files")MultipartFile[] files) throws Exception {
        logger.info("Saving the files");
        for (MultipartFile file: files) {
            userService.saveUser(file);
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(value = "/users", produces = "application/json")
    public CompletableFuture<ResponseEntity> findAllUsers() {
        logger.info("Total number of users are:");
        return userService.findAllUsers().thenApply(ResponseEntity::ok);
    }

    @GetMapping(value = "getUsersByThread", produces = "application/json")
    public ResponseEntity getUsers() {
        CompletableFuture<List<User>> usersList1 = userService.findAllUsers();
        CompletableFuture<List<User>> usersList2 = userService.findAllUsers();
        CompletableFuture<List<User>> usersList3 = userService.findAllUsers();
        CompletableFuture.allOf(usersList1).join();
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
