package com.fintransfer.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/log")
public class LogController {
    private static final Logger logger = LoggerFactory.getLogger(LogController.class);

    public static class LogMessage {
        public String level;
        public String message;
        public Object details;
    }

    @PostMapping
    public ResponseEntity<?> log(@RequestBody LogMessage logMessage) {
        if ("error".equalsIgnoreCase(logMessage.level)) {
            logger.error("[FRONTEND] {} - {}", logMessage.message, logMessage.details);
        } else if ("warn".equalsIgnoreCase(logMessage.level)) {
            logger.warn("[FRONTEND] {} - {}", logMessage.message, logMessage.details);
        } else {
            logger.info("[FRONTEND] {} - {}", logMessage.message, logMessage.details);
        }
        return ResponseEntity.ok().build();
    }
}
