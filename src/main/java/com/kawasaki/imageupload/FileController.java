package com.kawasaki.imageupload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Objects;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@RestController
@RequestMapping("/file")
public class FileController {
    @Autowired
    private GridFsTemplate gridFsTemplate;

    @GetMapping("/{fileKey}")
    public ResponseEntity<byte[]> getFile(@PathVariable String fileKey) throws IOException {
        var result = gridFsTemplate.getResource(Objects.requireNonNull(gridFsTemplate.findOne(Query.query(where("_id").is(fileKey)))));

        return ResponseEntity
                .ok()
                .contentType(MediaType.valueOf(result.getContentType()))
                .body(result.getInputStream().readAllBytes());
    }
}
