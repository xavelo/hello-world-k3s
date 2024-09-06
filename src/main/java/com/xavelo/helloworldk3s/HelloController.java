package com.xavelo.helloworldk3s;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.info.GitProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@RestController
public class HelloController {

    private static final Logger logger = LogManager.getLogger(HelloController.class);

    @Value("${HOSTNAME:unknown}")
    private String podName;

    @Autowired
    private GitProperties gitProperties;

    @GetMapping("/hello")
    public ResponseEntity<Hello> hello() {
        String commitId = gitProperties.getCommitId();
        LocalDateTime dateTime = LocalDateTime.ofInstant(gitProperties.getCommitTime(), ZoneId.systemDefault());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String commitTime = dateTime.format(formatter);
        logger.info("hello from pod {} - commitId {} - commitTime {}", commitId, commitTime, podName);
        return ResponseEntity.ok(new Hello("hello from pod " + podName, commitId + " - " + commitTime));
    }

}

