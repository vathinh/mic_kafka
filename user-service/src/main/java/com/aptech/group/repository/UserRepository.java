package com.aptech.group.repository;

import com.aptech.group.model.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    Page<UserEntity> findAll(Specification<UserEntity> specification, Pageable pageable);

    List<UserEntity> findAllByTitleIdIn(List<Integer> id);

    Optional<UserEntity> findByIdAndDeleteFlag(Integer id, boolean b);

    Optional<UserEntity> findByEmail(String email);

}
