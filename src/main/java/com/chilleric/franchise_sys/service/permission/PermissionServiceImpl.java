package com.chilleric.franchise_sys.service.permission;

import static java.util.Map.entry;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
import com.chilleric.franchise_sys.dto.permission.PermissionRequest;
import com.chilleric.franchise_sys.dto.permission.PermissionResponse;
import com.chilleric.franchise_sys.exception.BadSqlException;
import com.chilleric.franchise_sys.exception.ForbiddenException;
import com.chilleric.franchise_sys.exception.InvalidRequestException;
import com.chilleric.franchise_sys.exception.ResourceNotFoundException;
import com.chilleric.franchise_sys.exception.UnauthorizedException;
import com.chilleric.franchise_sys.repository.common_entity.ViewPoint;
import com.chilleric.franchise_sys.repository.systemRepository.accessability.Accessability;
import com.chilleric.franchise_sys.repository.systemRepository.permission.Permission;
import com.chilleric.franchise_sys.repository.systemRepository.permission.PermissionRepository;
import com.chilleric.franchise_sys.repository.systemRepository.user.UserRepository;
import com.chilleric.franchise_sys.service.AbstractService;
import com.chilleric.franchise_sys.utils.DateFormat;
import com.chilleric.franchise_sys.utils.ObjectUtilities;

@Service
public class PermissionServiceImpl extends AbstractService<PermissionRepository>
    implements PermissionService {


  @Autowired
  private UserRepository userRepository;

  @Override
  public Optional<ListWrapperResponse<PermissionResponse>> getYourPermissions(
      Map<String, String> allParams, String keySort, int page, int pageSize, String sortField,
      String loginId) {
    List<String> targets = accessabilityRepository.getListByAttribute(loginId, "userId")
        .orElseThrow(() -> new BadSqlException(LanguageMessageKey.SERVER_ERROR)).stream()
        .map(access -> access.getTargetId().toString()).collect(Collectors.toList());
    if (targets.size() == 0) {
      return Optional
          .of(new ListWrapperResponse<PermissionResponse>(new ArrayList<>(), page, pageSize, 0));
    }
    if (allParams.containsKey("_id")) {
      String[] idList = allParams.get("_id").split(",");
      ArrayList<String> check = new ArrayList<>(Arrays.asList(idList));
      for (int i = 0; i < check.size(); i++) {
        if (targets.contains(idList[i])) {
          check.add(idList[i]);
        }
      }
      if (check.size() == 0) {
        return Optional
            .of(new ListWrapperResponse<PermissionResponse>(new ArrayList<>(), page, pageSize, 0));
      }
      allParams.put("_id", generateParamsValue(check));
    } else {
      allParams.put("_id", generateParamsValue(targets));
    }
    List<Permission> permissions =
        repository.getListOrEntity(allParams, keySort, page, pageSize, sortField).get();
    return Optional.of(new ListWrapperResponse<PermissionResponse>(
        permissions.stream()
            .map(permission -> new PermissionResponse(permission.get_id().toString(),
                permission.getName(),
                permission.getUserId().size() > 0 ? permission.getUserId().stream()
                    .map(ObjectId::toString).collect(Collectors.toList()) : new ArrayList<>(),
                DateFormat.toDateString(permission.getCreated(), DateTime.YYYY_MM_DD),
                DateFormat.toDateString(permission.getModified(), DateTime.YYYY_MM_DD),
                removeId(permission.getViewPoints()), removeId(permission.getEditable()),
                permission.isServer() ? 1 : 0))
            .collect(Collectors.toList()),
        page, pageSize, repository.getTotalPage(allParams)));
  }

  @Override
  public Optional<PermissionResponse> getPermissionById(String id, String loginId) {
    Permission permission = repository.getEntityByAttribute(id, "_id")
        .orElseThrow(() -> new ResourceNotFoundException(LanguageMessageKey.PERMISSION_NOT_FOUND));
    return Optional
        .of(new PermissionResponse(permission.get_id().toString(), permission.getName(),
            permission.getUserId().size() > 0 ? permission.getUserId().stream()
                .map(ObjectId::toString).collect(Collectors.toList()) : new ArrayList<>(),
            DateFormat.toDateString(permission.getCreated(), DateTime.YYYY_MM_DD),
            DateFormat.toDateString(permission.getModified(), DateTime.YYYY_MM_DD),
            removeId(permission.getViewPoints()), removeId(permission.getEditable()),
            permission.isServer() ? 1 : 0));
  }

  @Override
  public void addNewPermissions(PermissionRequest permissionRequest, String loginId,
      boolean isServer) {
    validate(permissionRequest);
    permissionRequest.setEditable(removeId(permissionRequest.getEditable()));
    permissionRequest.setViewPoints(removeId(permissionRequest.getViewPoints()));
    Map<String, String> error = generateError(PermissionRequest.class);
    List<Permission> permissions = repository
        .getListOrEntity(Map.ofEntries(entry("name", permissionRequest.getName())), "", 0, 0, "")
        .get();
    if (permissions.size() != 0) {
      error.put("name", LanguageMessageKey.INVALID_NAME_PERMISSION);
      throw new InvalidRequestException(error, LanguageMessageKey.INVALID_NAME_PERMISSION);
    }
    Permission permission = new Permission();
    ObjectId newId = new ObjectId();
    permission.set_id(newId);
    permission.setName(permissionRequest.getName());
    permission.setCreated(DateFormat.getCurrentTime());
    permission.setViewPoints(permissionRequest.getViewPoints());
    if (isServer) {
      if (permissionRequest.getIsServer() == 0) {
        permission.setServer(false);
      }
      if (permissionRequest.getIsServer() == 1) {
        permission.setServer(true);
      }
    } else {
      permission.setServer(isServer);
    }
    if (permissionRequest.getUserId().size() != 0) {
      List<ObjectId> resultIds = new ArrayList<>();
      permissionRequest.getUserId().forEach(thisId -> {
        userRepository.getEntityByAttribute(thisId, "_id").ifPresent(thisUser -> {
          accessabilityRepository.getAccessability(loginId, thisId).ifPresent(thisAccess -> {
            resultIds.add(new ObjectId(thisId));
          });
        });
      });
      permission.setUserId(resultIds);
    } else {
      permission.setUserId(new ArrayList<>());
    }
    permission.setEditable(permissionRequest.getEditable());
    permission.setViewPoints(permissionRequest.getViewPoints());
    accessabilityRepository
        .insertAndUpdate(new Accessability(null, new ObjectId(loginId), newId, true, isServer));
    repository.insertAndUpdate(permission);
  }

  @Override
  public void editPermission(PermissionRequest permissionRequest, String id,
      List<ViewPoint> viewPoints, String loginId, boolean isServer) {
    Permission permission = repository.getEntityByAttribute(id, "_id")
        .orElseThrow(() -> new ResourceNotFoundException(LanguageMessageKey.PERMISSION_NOT_FOUND));
    validate(permissionRequest);
    permissionRequest.setEditable(removeId(permissionRequest.getEditable()));
    permissionRequest.setViewPoints(removeId(permissionRequest.getViewPoints()));
    Map<String, String> error = generateError(PermissionRequest.class);
    repository.getEntityByAttribute(permissionRequest.getName(), "name").ifPresent(perm -> {
      if (perm.get_id().compareTo(permission.get_id()) != 0) {
        error.put("name", LanguageMessageKey.INVALID_NAME_PERMISSION);
        throw new InvalidRequestException(error, LanguageMessageKey.INVALID_NAME_PERMISSION);
      }
    });
    permission.setName(permissionRequest.getName());
    if (permissionRequest.getUserId().size() != 0) {
      List<ObjectId> resultIds = new ArrayList<>();
      permissionRequest.getUserId().forEach(thisId -> {
        userRepository.getEntityByAttribute(thisId, "_id").ifPresent(thisUser -> {
          accessabilityRepository.getAccessability(loginId, thisId).ifPresent(thisAccess -> {
            resultIds.add(new ObjectId(thisId));
          });
        });
      });
      permission.setUserId(resultIds);
    } else {
      permission.setUserId(new ArrayList<>());
    }
    if (isServer) {
      if (permissionRequest.getIsServer() == 0) {
        permission.setServer(false);
      }
      if (permissionRequest.getIsServer() == 1) {
        permission.setServer(true);
      }
    } else {
      permission.setServer(isServer);
    }
    permission.setEditable(permissionRequest.getEditable());
    permission.setViewPoints(permissionRequest.getViewPoints());
    permission.setModified(DateFormat.getCurrentTime());
    repository.insertAndUpdate(permission);

  }

  @Override
  public void deletePermission(String id) {
    Permission permission = repository.getEntityByAttribute(id, "_id")
        .orElseThrow(() -> new ResourceNotFoundException(LanguageMessageKey.PERMISSION_NOT_FOUND));
    checkDeleteAndEdit(permission);
    repository.deleteById(id);
  }

  @Override
  public Map<String, List<ViewPoint>> getViewPointSelect(String loginId) {
    Map<String, List<ViewPoint>> thisView = new HashMap<>();
    repository.getListByAttribute(loginId, "userId")
        .orElseThrow(() -> new UnauthorizedException(LanguageMessageKey.UNAUTHORIZED))
        .forEach(thisPerm -> {
          thisView.putAll(ObjectUtilities.mergePermission(thisView, thisPerm.getViewPoints()));
        });
    return thisView;
  }

  @Override
  public List<ViewPoint> getPermissionView() {
    List<ViewPoint> attributes = new ArrayList<>();
    for (Field field : PermissionResponse.class.getDeclaredFields()) {
      attributes.add(new ViewPoint(field.getName(), field.getName()));
    }
    return removeId(Map.ofEntries(entry(PermissionResponse.class.getSimpleName(), attributes)))
        .get(PermissionResponse.class.getSimpleName());
  }

  @Override
  public Map<String, List<ViewPoint>> getEditableSelect(String loginId) {
    Map<String, List<ViewPoint>> thisView = new HashMap<>();
    repository.getListByAttribute(loginId, "userId")
        .orElseThrow(() -> new UnauthorizedException(LanguageMessageKey.UNAUTHORIZED))
        .forEach(thisPerm -> {
          thisView.putAll(ObjectUtilities.mergePermission(thisView, thisPerm.getEditable()));
        });
    return thisView;
  }

  @Override
  public Optional<ListWrapperResponse<PermissionResponse>> getAllPermissions(
      Map<String, String> allParams, String keySort, int page, int pageSize, String sortField,
      String loginId, boolean isServer) {
    if (!isServer) {
      List<String> targets = accessabilityRepository.getListByAttribute(loginId, "userId")
          .orElseThrow(() -> new BadSqlException(LanguageMessageKey.SERVER_ERROR)).stream()
          .map(access -> access.getTargetId().toString()).collect(Collectors.toList());
      if (targets.size() == 0) {
        return Optional
            .of(new ListWrapperResponse<PermissionResponse>(new ArrayList<>(), page, pageSize, 0));
      }
      if (allParams.containsKey("_id")) {
        String[] idList = allParams.get("_id").split(",");
        ArrayList<String> check = new ArrayList<>(Arrays.asList(idList));
        for (int i = 0; i < check.size(); i++) {
          if (targets.contains(idList[i])) {
            check.add(idList[i]);
          }
        }
        if (check.size() == 0) {
          return Optional.of(
              new ListWrapperResponse<PermissionResponse>(new ArrayList<>(), page, pageSize, 0));
        }
        allParams.put("_id", generateParamsValue(check));
      } else {
        allParams.put("_id", generateParamsValue(targets));
      }
    }
    List<Permission> permissions =
        repository.getListOrEntity(allParams, keySort, page, pageSize, sortField).get();
    return Optional.of(new ListWrapperResponse<PermissionResponse>(
        permissions.stream()
            .map(permission -> new PermissionResponse(permission.get_id().toString(),
                permission.getName(),
                permission.getUserId().size() > 0 ? permission.getUserId().stream()
                    .map(ObjectId::toString).collect(Collectors.toList()) : new ArrayList<>(),
                DateFormat.toDateString(permission.getCreated(), DateTime.YYYY_MM_DD),
                DateFormat.toDateString(permission.getModified(), DateTime.YYYY_MM_DD),
                removeId(permission.getViewPoints()), removeId(permission.getEditable()),
                permission.isServer() ? 1 : 0))
            .collect(Collectors.toList()),
        page, pageSize, repository.getTotalPage(allParams)));
  }

  private void checkDeleteAndEdit(Permission permission) {
    permission.getUserId().forEach(thisUser -> {
      accessabilityRepository.getListByAttribute(thisUser.toString(), "userId")
          .ifPresent(thisAccess -> {
            if (thisAccess.size() > 0) {
              throw new ForbiddenException(LanguageMessageKey.FORBIDDEN);
            }
          });
    });
  }

  private Map<String, List<ViewPoint>> removeId(Map<String, List<ViewPoint>> thisView) {
    return thisView.entrySet().stream().map((key) -> {
      List<ViewPoint> newValue = key.getValue().stream()
          .filter(viewList -> viewList.getKey().compareTo("id") != 0).collect(Collectors.toList());
      return entry(key.getKey(), newValue);
    }).collect(
        Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (x, y) -> y, LinkedHashMap::new));
  }
}
