package com.nequi.prueba.franquicias.presentation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/health")
public class HealthController {

    private final DataSource dataSource;

    public HealthController(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @GetMapping("/db")
    public ResponseEntity<Map<String, Object>> dbHealth() {
        Map<String, Object> body = new HashMap<>();
        try (Connection conn = dataSource.getConnection()) {
            DatabaseMetaData meta = conn.getMetaData();
            body.put("provider", "PostgreSQL");
            body.put("url", meta.getURL());
            body.put("user", meta.getUserName());
            body.put("driverName", meta.getDriverName());
            body.put("connected", true);
        } catch (Exception e) {
            body.put("connected", false);
            body.put("error", e.getMessage());
        }
        return ResponseEntity.ok(body);
    }
}