package com.amazon.aws.upload.AwsUpload.controller;

import com.amazon.aws.upload.AwsUpload.model.Todo;
import com.amazon.aws.upload.AwsUpload.service.TodoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/todo")
@CrossOrigin("*")
public class TodoController {
    TodoService service;
    @GetMapping
    public ResponseEntity<List<Todo>> getTodos(){
        return new ResponseEntity<>(service.getAllTodos(), HttpStatus.OK);
    }
    @PostMapping(
            path = "",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Todo> saveTodo(@RequestParam("title") String title,
                                         @RequestParam("description") String description,
                                         @RequestParam("file")MultipartFile file){
        return new ResponseEntity<>(service.saveTodo(title,description,file),HttpStatus.OK);
    }
    @GetMapping(value = "{id}/image/download")
    public byte[] downloadTodoImage(@PathVariable("id") Long id){
        return service.downloadTodoImage(id);
    }
}
