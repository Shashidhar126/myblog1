package com.myblog10.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(value = HttpStatus.NOT_FOUND)//this object isv createdcwhen post is not found//404 status code
public class ResourceNotFound extends RuntimeException{

    public ResourceNotFound(String msg){//when i  create a object of resourcenot found i will suply message to this constructor

        super(msg);//super keyword will automatically display msg in postman response
    }
}