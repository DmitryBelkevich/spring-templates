package com.hard.controllers;

import com.hard.models.Entity;
import com.hard.services.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/entities")
public class EntityController {
    @Autowired
    private EntityService entityService;

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Collection<Entity>> getAll() {
        Collection<Entity> entities = entityService.getAll();

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");

        if (entities.isEmpty())
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .headers(headers)
                    .body(null);

        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(headers)
                .body(entities);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Entity> getById(@PathVariable("id") long id) {
        Entity e = entityService.getById(id);

        if (e == null)
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(null);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(e);
    }

    @PostMapping("")
    public ResponseEntity<Entity> add(@RequestBody Entity entity) {
        Entity e = entityService.getById(entity.getId());

        if (e != null)
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(null);

        entityService.add(entity);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(entity);
    }

    @PutMapping("")
    public ResponseEntity<Collection<Entity>> addCollection(@RequestBody Collection<Entity> entities) {
        for (Entity entity : entities) {
            Entity e = entityService.getById(entity.getId());

            if (e != null)
                return ResponseEntity
                        .status(HttpStatus.CONFLICT)
                        .body(null);
        }

        entityService.add(entities);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(entities);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Entity> update(@PathVariable("id") long id, @RequestBody Entity entity) {
        Entity e = entityService.getById(id);

        if (e == null)
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(null);

        entityService.update(id, entity);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(entity);
    }

    @PatchMapping("")
    public ResponseEntity<Collection<Entity>> updateCollection(@RequestBody Collection<Entity> entities) {
        for (Entity entity : entities) {
            Entity e = entityService.getById(entity.getId());

            if (e == null)
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(null);
        }

        entityService.update(entities);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(entities);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") long id) {
        Entity e = entityService.getById(id);

        if (e == null)
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(null);

        entityService.delete(id);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(null);
    }

    @DeleteMapping("")
    public ResponseEntity<Void> clear() {
        entityService.clear();

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(null);
    }
}
