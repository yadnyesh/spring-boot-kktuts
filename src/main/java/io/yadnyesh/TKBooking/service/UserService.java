package io.yadnyesh.TKBooking.service;

import io.yadnyesh.TKBooking.model.User;
import io.yadnyesh.TKBooking.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    Object target;

    Logger logger = LoggerFactory.getLogger(UserService.class);

    @Async
    public CompletableFuture<List<User>> saveUser(MultipartFile file) throws Exception {
        long starTime = System.currentTimeMillis();
        List<User> users = parseCSVFile(file);
        logger.info("Saving {} of users in the DB", users.size() + Thread.currentThread().getName());
        userRepository.saveAll(users);
        long endTime = System.currentTimeMillis();
        logger.info("Total time to save all the records {}", (endTime - starTime));
        return CompletableFuture.completedFuture(users);
    }

    @Async
    public CompletableFuture<List<User>> findAllUsers() {
        logger.info("Fetch all the users by thread:- {}", Thread.currentThread().getName());
        List<User> users = (List<User>) userRepository.findAll();
        return CompletableFuture.completedFuture(users);
    }

    private List<User> parseCSVFile(final MultipartFile file) throws Exception {
        final List<User> users = new ArrayList<>();
        try {
            try (final BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))){
                String line;
                while ((br.readLine() != null)) {
                    line = br.readLine();
                    final String[] data = line.split(",");
                    final User user = new User();
                    user.setName(data[0]);
                    user.setEmail(data[1]);
                    user.setGender(data[2]);
                    users.add(user);
                }
            return users;
            }
        } catch(final IOException e) {
            logger.error("Failed to parse CSV file {}", e);
            throw new Exception("Failed to parse CSV file {}", e);
        }
    }
}
