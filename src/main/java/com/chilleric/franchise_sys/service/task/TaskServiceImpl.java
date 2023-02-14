package com.chilleric.franchise_sys.service.task;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.chilleric.franchise_sys.constant.DateTime;
import com.chilleric.franchise_sys.constant.LanguageMessageKey;
import com.chilleric.franchise_sys.dto.common.ListWrapperResponse;
import com.chilleric.franchise_sys.dto.task.TaskRequest;
import com.chilleric.franchise_sys.dto.task.TaskResponse;
import com.chilleric.franchise_sys.exception.ResourceNotFoundException;
import com.chilleric.franchise_sys.repository.crm_repository.task.Task;
import com.chilleric.franchise_sys.repository.crm_repository.task.TaskRepository;
import com.chilleric.franchise_sys.service.AbstractService;
import com.chilleric.franchise_sys.service.shift.ShiftService;
import com.chilleric.franchise_sys.service.user.UserService;
import com.chilleric.franchise_sys.utils.DateFormat;

@Service
public class TaskServiceImpl extends AbstractService<TaskRepository> implements TaskService {
  @Autowired
  private UserService userService;

  @Autowired
  private ShiftService shiftService;

  @Override
  public void createTask(TaskRequest taskRequest) {
    validate(taskRequest);

    Task task =
        new Task(new ObjectId(), new ObjectId(taskRequest.getEmployeeId()),
            new ObjectId(taskRequest.getReviewerId()), new ObjectId(taskRequest.getShiftId()),
            DateFormat.convertStringToDate(DateFormat.combineDateAndHour(taskRequest.getStartDate(),
                taskRequest.getStartHour()), DateTime.YYYY_MM_DD_HH_MM_SS_HYPHEN),
            taskRequest.getTitle(), taskRequest.getDescription(), taskRequest.getStatus());
    repository.insertAndUpdate(task);
  }

  @Override
  public void update(TaskRequest taskRequest, String taskId) {
    validate(taskRequest);

    Task task = repository.getListOrEntity(Map.ofEntries(Map.entry("_id", taskId)), "", 0, 0, "")
        .orElseThrow(() -> new ResourceNotFoundException(LanguageMessageKey.TASK_NOT_FOUND)).get(0);

    Task newTask =
        new Task(task.get_id(), new ObjectId(taskRequest.getEmployeeId()),
            new ObjectId(taskRequest.getReviewerId()), new ObjectId(taskRequest.getShiftId()),
            DateFormat.convertStringToDate(DateFormat.combineDateAndHour(taskRequest.getStartDate(),
                taskRequest.getStartHour()), DateTime.YYYY_MM_DD_HH_MM_SS_HYPHEN),
            taskRequest.getTitle(), taskRequest.getDescription(), taskRequest.getStatus());

    repository.insertAndUpdate(newTask);
  }

  @Override
  public Optional<ListWrapperResponse<TaskResponse>> getTasks(Map<String, String> allParams,
      int pageSize, int page, String keySort, String sortField) {
    List<Task> tasks =
        repository.getListOrEntity(allParams, keySort, page, pageSize, sortField).get();

    List<TaskResponse> listTaskResponses = tasks.stream().map(task -> {
      boolean isValidReferences = isReferencesValid(task.getReviewerId().toString(),
          task.getEmployeeId().toString(), task.getShiftId().toString());
      if (isValidReferences) {
        return new TaskResponse(task.get_id().toString(),
            userService.findOneUserById(task.getEmployeeId().toString()).get(),
            shiftService.getShiftById(task.getShiftId().toString()).get(),
            userService.findOneUserById(task.getReviewerId().toString()).get(),
            task.getStartTime().toString(), task.getTitle().toString(),
            task.getDescription().toString(), task.getStatus());
      } else {
        delete(task.get_id().toString());
        return null;
      }
    }).filter(e -> e != null).collect(Collectors.toList());

    return Optional.of(new ListWrapperResponse<TaskResponse>(listTaskResponses, page,
        listTaskResponses.size(), repository.getTotalPage(allParams)));
  }

  @Override
  public void delete(String taskId) {
    validateStringIsObjectId(taskId);

    repository.deleteById(taskId);
  }

  @Override
  public boolean isReferencesValid(String reviewerId, String employeeId, String shiftId) {
    try {
      userService.findOneUserById(employeeId).get();
      userService.findOneUserById(reviewerId).get();
      shiftService.getShiftById(shiftId).get();

      return true;
    } catch (Exception e) {
      return false;
    }
  }

}
