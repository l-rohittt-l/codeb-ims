package com.codeb.ims.repository;

import com.codeb.ims.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group, Long> {

    Optional<Group> findByGroupNameIgnoreCase(String groupName);
    Optional<Group> findByGroupCodeIgnoreCase(String groupCode);

    boolean existsByGroupNameIgnoreCase(String groupName);
    boolean existsByGroupCodeIgnoreCase(String groupCode);

    List<Group> findAllByIsActiveTrue();
}
