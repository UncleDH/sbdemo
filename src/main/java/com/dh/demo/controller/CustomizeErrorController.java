package com.dh.demo.controller;

import org.springframework.boot.web.servlet.error.ErrorController;

public class CustomizeErrorController implements ErrorController {

    @Override
    public String getErrorPath() {
        return "error";
    }
}
