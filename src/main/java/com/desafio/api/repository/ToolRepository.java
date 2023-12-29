package com.desafio.api.repository;

import com.desafio.api.entity.Tool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ToolRepository extends JpaRepository<Tool, Long> {

    @Query(value = "SELECT * FROM tb_tool WHERE tags like %:tag%", nativeQuery = true)
    List<Tool> findAllByTag(@Param("tag") String tag);
}
