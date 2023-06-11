package com.amazon.aws.upload.AwsUpload.repository;

import com.amazon.aws.upload.AwsUpload.model.Todo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends CrudRepository<Todo,Long> {
    Todo findByTitle(String title);
}
