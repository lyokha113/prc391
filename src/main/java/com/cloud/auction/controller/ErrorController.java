package com.cloud.auction.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {

    @GetMapping("/error")
    public String handleError(HttpServletRequest request) throws JsonProcessingException {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            int statusCode = Integer.valueOf(status.toString());
            Map<String, Object> error = new LinkedHashMap<>();
            error.put("status", 0);
            error.put("errors", statusCode == HttpStatus.NOT_FOUND.value() ?
                    "API Not found" :
                    HttpStatus.valueOf(statusCode).getReasonPhrase());
            return new ObjectMapper().writeValueAsString(error);
        }
        return "error";
    }

    @Override
    public String getErrorPath() {
        return "error";
    }
}
