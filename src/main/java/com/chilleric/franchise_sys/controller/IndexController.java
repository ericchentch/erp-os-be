package com.chilleric.franchise_sys.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController implements ErrorController {
  private static final String PATH = "/error";

  @RequestMapping(value = PATH)
  public String error() {
    return "<!DOCTYPE html><html><body style=\"width:100%; text-align:center; background-color:#E7E9EB\"><h1>This endpoint does not exist!</h1><a style=\"color:white;padding:10px; background-color:#0285f0;text-decoration:none;border-radius:10px\" href=\"/swagger-ui\">Swagger</a></body></html>";
  }
}
