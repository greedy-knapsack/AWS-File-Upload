package com.amazon.aws.upload.AwsUpload.service;

import com.amazon.aws.upload.AwsUpload.config.BucketName;
import com.amazon.aws.upload.AwsUpload.model.Todo;
import com.amazon.aws.upload.AwsUpload.repository.TodoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

import static org.apache.http.entity.ContentType.*;


@Service
@AllArgsConstructor
public class TodoServiceImpl implements TodoService{
    private final TodoRepository todoRepository;
    private final FileStore fileStore;
    @Override
    public List<Todo> getAllTodos() {
        List<Todo> todos = new ArrayList<>();
        todoRepository.findAll().forEach(todos::add);
        return todos;
    }
    @Override
    public Todo saveTodo(String title, String description, MultipartFile file) {
        if(file.isEmpty()){
            throw new IllegalStateException("Cannot upload empty file");
        }
        if(!Arrays.asList(IMAGE_PNG.getMimeType(),IMAGE_BMP.getMimeType(),
                IMAGE_GIF.getMimeType(),IMAGE_JPEG.getMimeType()).contains(file.getContentType())){
            throw new IllegalStateException("File uploaded is not an image");
        }
        Map<String,String> metadata = new HashMap<>();
        metadata.put("Content-Type",file.getContentType());
        metadata.put("Content-Length",String.valueOf(file.getSize()));
        String path = String.format("%s/%s", BucketName.TODO_IMAGE.getBucketName(),UUID.randomUUID());
        String fileName = String.format("%s",file.getOriginalFilename());
        try {
            fileStore.upload(path,fileName,Optional.of(metadata),file.getInputStream());
        } catch (IOException e){
            throw new IllegalStateException("Failed to upload file",e);
        }
        Todo todo = Todo.builder()
                .description(description)
                .title(title)
                .imagePath(path)
                .imageFileName(fileName)
                .build();
        todoRepository.save(todo);
        return todoRepository.findByTitle(todo.getTitle());
    }

    @Override
    public byte[] downloadTodoImage(Long id) {
        Todo todo = todoRepository.findById(id).get();
        return fileStore.download(todo.getImagePath(),todo.getImageFileName());
    }
}
