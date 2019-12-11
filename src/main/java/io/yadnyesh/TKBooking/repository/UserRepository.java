package io.yadnyesh.TKBooking.repository;

import io.yadnyesh.TKBooking.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
}
