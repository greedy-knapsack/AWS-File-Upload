package com.amazon.aws.upload.AwsUpload.service;

import com.amazon.aws.upload.AwsUpload.model.Todo;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TodoService {
    List<Todo> getAllTodos();

    Todo saveTodo(String title, String description, MultipartFile file);

    byte[] downloadTodoImage(Long id);
}
