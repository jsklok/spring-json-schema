package com.example.demo.controllers;

import com.example.demo.system.validators.ValidSchema;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 *
 */
@RestController
@RequestMapping("/api/v1")
@Validated
public class DebugController {
    // --------------------------------------------------------------------------------------------

    //
    private final Logger log = LoggerFactory.getLogger(getClass());

    /**
     *
     * @return
     */
    @GetMapping("/get")
    public String get() {
        log.trace("TRACE");
        log.debug("DEBUG");
        log.info("INFO");
        log.warn("WARN");
        log.error("ERROR");
        return UUID.randomUUID().toString();
    }

    /**
     *
     * @return
     */
    @PostMapping("/post")
    public String post(
        @RequestBody String body) {
        return String.format("POST: %s", body);
    }

    /**
     *
     * @return
     */
    @PutMapping("/put")
    public String put(
        @RequestBody String body) {
        return String.format("PUT: %s", body);
    }

    /**
     *
     * @return
     */
    @DeleteMapping("/delete")
    public String delete() {
        return UUID.randomUUID().toString();
    }

    // -------------------------------------------------

    /**
     *
     * @return
     */
    @PostMapping("/json")
    public String json(
        @RequestBody @Valid @ValidSchema TemplateRequest schema) {
        return "valid";
    }

}
