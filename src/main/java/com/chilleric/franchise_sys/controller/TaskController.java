package com.chilleric.franchise_sys.controller;

import java.util.ArrayList;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.chilleric.franchise_sys.constant.LanguageMessageKey;
import com.chilleric.franchise_sys.dto.common.CommonResponse;
import com.chilleric.franchise_sys.dto.common.ListWrapperResponse;
import com.chilleric.franchise_sys.dto.task.TaskRequest;
import com.chilleric.franchise_sys.dto.task.TaskResponse;
import com.chilleric.franchise_sys.service.task.TaskService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping(value = "task-controller")
public class TaskController extends AbstractController<TaskService> {

  @SecurityRequirement(name = "Bearer Authentication")
  @PostMapping(value = "create-new-task")
  public ResponseEntity<CommonResponse<String>> createTask(@RequestBody TaskRequest taskRequest,
      HttpServletRequest request) {
    validateToken(request);

    service.createTask(taskRequest);

    return new ResponseEntity<CommonResponse<String>>(new CommonResponse<String>(true, null,
        LanguageMessageKey.SUCCESS, HttpStatus.OK.value(), new ArrayList<>(), new ArrayList<>()),
        null, HttpStatus.OK.value());
  }

  @SecurityRequirement(name = "Bearer Authentication")
  @GetMapping(value = "get-tasks")
  public ResponseEntity<CommonResponse<ListWrapperResponse<TaskResponse>>> getTasks(
      @RequestParam Map<String, String> allParams,
      @RequestParam(defaultValue = "asc") String keySort,
      @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int pageSize,
      @RequestParam(defaultValue = "title") String sortField, HttpServletRequest request) {
    validateToken(request);

    return response(service.getTasks(allParams, pageSize, page, keySort, sortField),
        LanguageMessageKey.SUCCESS, new ArrayList<>(), new ArrayList<>());
  }

  @SecurityRequirement(name = "Bearer Authentication")
  @DeleteMapping(value = "delete-task")
  public ResponseEntity<CommonResponse<String>> deleteTask(@RequestParam String taskId,
      HttpServletRequest request) {
    validateToken(request);

    service.delete(taskId);

    return new ResponseEntity<CommonResponse<String>>(
        new CommonResponse<String>(true, null, LanguageMessageKey.SHIFT_DELETE_SUCCESS,
            HttpStatus.OK.value(), new ArrayList<>(), new ArrayList<>()),
        null, HttpStatus.OK.value());
  }
}
