package com.example.oss.api.repository;

import com.example.oss.api.models.PostEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PostEventRepository extends JpaRepository<PostEvent, UUID> {
}