package za.ac.nextdoorpost.NextDoorPost.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.ac.nextdoorpost.NextDoorPost.model.User;
import za.ac.nextdoorpost.NextDoorPost.model.Role;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByEmailAndRole(String email, Role role);
    Optional<User> findByIdAndRole(Long id, Role role);
}
