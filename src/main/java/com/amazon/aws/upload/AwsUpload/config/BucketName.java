package com.amazon.aws.upload.AwsUpload.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BucketName {
    TODO_IMAGE("spring-aws-storage1");
    private final String bucketName;
}
