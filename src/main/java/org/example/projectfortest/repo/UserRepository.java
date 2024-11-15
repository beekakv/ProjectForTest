package org.example.projectfortest.repo;

import jakarta.transaction.Transactional;
import org.example.projectfortest.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    @Query(value = "select u from UserEntity u where u.login = :login")
    Optional<UserEntity> findByLogin(@Param(value = "login") String login);

    @Query(value = "select u from UserEntity u where u.id = :id")
    Optional<UserEntity> findById(@Param(value = "id") Long id);

    @Query(nativeQuery = false, value = "select u from UserEntity u")
    List<UserEntity> findAll();
    @Modifying
    void deleteById(Long id);

    @Modifying
    UserEntity save(UserEntity user);
}
