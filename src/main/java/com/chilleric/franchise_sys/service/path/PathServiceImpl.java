package com.chilleric.franchise_sys.service.path;

import static java.util.Map.entry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.chilleric.franchise_sys.constant.LanguageMessageKey;
import com.chilleric.franchise_sys.constant.TypeValidation;
import com.chilleric.franchise_sys.dto.common.ListWrapperResponse;
import com.chilleric.franchise_sys.dto.path.PathRequest;
import com.chilleric.franchise_sys.dto.path.PathResponse;
import com.chilleric.franchise_sys.exception.BadSqlException;
import com.chilleric.franchise_sys.exception.InvalidRequestException;
import com.chilleric.franchise_sys.exception.ResourceNotFoundException;
import com.chilleric.franchise_sys.pusher.PusherUpdateResponse;
import com.chilleric.franchise_sys.repository.systemRepository.accessability.Accessability;
import com.chilleric.franchise_sys.repository.systemRepository.path.Path;
import com.chilleric.franchise_sys.repository.systemRepository.path.PathRepository;
import com.chilleric.franchise_sys.repository.systemRepository.user.User;
import com.chilleric.franchise_sys.repository.systemRepository.user.User.TypeAccount;
import com.chilleric.franchise_sys.repository.systemRepository.user.UserRepository;
import com.chilleric.franchise_sys.service.AbstractService;

@Service
public class PathServiceImpl extends AbstractService<PathRepository> implements PathService {

  @Autowired
  private UserRepository userRepository;

  public void sendNotificationUpdate(List<ObjectId> currentList, List<ObjectId> newListId) {
    List<String> notiList = new ArrayList<>();
    if (currentList.size() > 0) {
      for (int i = 0; i < currentList.size(); i++) {
        boolean isFound = false;
        for (int j = 0; j < newListId.size(); j++) {
          if (currentList.get(i).compareTo(newListId.get(j)) == 0) {
            isFound = true;
            break;
          }
        }
        if (!isFound) {
          notiList.add(currentList.get(i).toString());
        }
      }
    }
    for (int i = 0; i < newListId.size(); i++) {
      boolean isFound = false;
      for (int j = 0; j < currentList.size(); j++) {
        if (newListId.get(i).compareTo(currentList.get(j)) == 0) {
          isFound = true;
          break;
        }
      }
      if (!isFound) {
        notiList.add(newListId.get(i).toString());
      }
    }
    if (notiList.size() > 0) {
      notiList.forEach(thisNotiId -> {
        userRepository.getEntityByAttribute(thisNotiId, "_id").ifPresent(thisUser -> {
          pusherService.pushInfo(thisUser.getChannelId(), thisUser.getEventId().toString(),
              new PusherUpdateResponse(true, false));
        });
      });
    }
  }

  @Override
  public Optional<ListWrapperResponse<PathResponse>> getPaths(Map<String, String> allParams,
      String keySort, int page, int pageSize, String sortField, String loginId) {
    List<Path> paths =
        repository.getListOrEntity(allParams, keySort, page, pageSize, sortField).get();
    return Optional.of(new ListWrapperResponse<>(
        paths.stream()
            .map(path -> new PathResponse(path.get_id().toString(), path.getLabel(), path.getPath(),
                path.getType(),
                path.getUserId().stream().map(thisId -> thisId.toString())
                    .collect(Collectors.toList()),
                path.getIcon()))
            .collect(Collectors.toList()),
        page, pageSize, repository.getTotalPage(allParams)));
  }

  @Override
  public void addNewPath(PathRequest pathRequest, String loginId, boolean isServer) {
    validate(pathRequest);
    Map<String, String> error = generateError(PathRequest.class);
    repository.getEntityByAttribute(pathRequest.getLabel(), "label").ifPresent(path -> {
      error.put("label", LanguageMessageKey.LABEL_EXISTED);
      throw new InvalidRequestException(error, LanguageMessageKey.LABEL_EXISTED);
    });
    repository.getEntityByAttribute(pathRequest.getPath(), "path").ifPresent(path -> {
      error.put("path", LanguageMessageKey.PATH_EXISTED);
      throw new InvalidRequestException(error, LanguageMessageKey.PATH_EXISTED);
    });
    ObjectId newId = new ObjectId();
    if (pathRequest.getType().compareTo("EXTERNAL") != 0
        && pathRequest.getType().compareTo("INTERNAL") != 0) {
      error.put("type", LanguageMessageKey.TYPE_PATH_INVALID);
      throw new InvalidRequestException(error, LanguageMessageKey.TYPE_PATH_INVALID);
    }
    User adminUser = userRepository.getEntityByAttribute("super_admin_dev", "username")
        .orElseThrow(() -> new BadSqlException(LanguageMessageKey.SERVER_ERROR));
    Path path = new Path();
    List<ObjectId> listUserId = new ArrayList<>();
    if (pathRequest.getUserId().size() > 0) {
      pathRequest.getUserId().forEach(thisId -> {
        if (!listUserId.contains(new ObjectId(thisId))
            && userRepository.getEntityByAttribute(thisId, "_id").isPresent()) {
          listUserId.add(new ObjectId(thisId));
        }
      });
    }
    if (!listUserId.contains(adminUser.get_id())) {
      listUserId.add(adminUser.get_id());
    }
    if (pathRequest.getType().compareTo("EXTERNAL") == 0) {
      path = new Path(newId, pathRequest.getLabel(), pathRequest.getPath(), TypeAccount.EXTERNAL,
          listUserId, pathRequest.getIcon());
    }
    if (pathRequest.getType().compareTo("INTERNAL") == 0) {
      path = new Path(newId, pathRequest.getLabel(), pathRequest.getPath(), TypeAccount.INTERNAL,
          listUserId, pathRequest.getIcon());
    }
    if (pathRequest.getIcon().length() > 0
        && !pathRequest.getIcon().startsWith(TypeValidation.PATH_PRE_FIX)) {
      error.put("type", LanguageMessageKey.INVALID_PATH_ICON);
      throw new InvalidRequestException(error, LanguageMessageKey.INVALID_PATH_ICON);
    }
    sendNotificationUpdate(new ArrayList<>(), listUserId);
    accessabilityRepository
        .insertAndUpdate(new Accessability(null, new ObjectId(loginId), newId, true, isServer));
    repository.insertAndUpdate(path);

  }

  @Override
  public void deletePath(String id) {
    repository.getEntityByAttribute(id, "_id")
        .orElseThrow(() -> new ResourceNotFoundException(LanguageMessageKey.PATH_NOTFOUND));
    repository.deleteById(id);
  }

  @Override
  public Optional<PathResponse> getPathDetail(String id) {
    Path path = repository.getEntityByAttribute(id, "_id")
        .orElseThrow(() -> new ResourceNotFoundException(LanguageMessageKey.PATH_NOTFOUND));
    return Optional.of(
        new PathResponse(path.get_id().toString(), path.getLabel(), path.getPath(), path.getType(),
            path.getUserId().stream().map(thisId -> thisId.toString()).collect(Collectors.toList()),
            path.getIcon()));
  }

  @Override
  public Optional<List<String>> getPathAccess(String loginId) {
    User user = userRepository.getEntityByAttribute(loginId, "_id")
        .orElseThrow(() -> new ResourceNotFoundException(LanguageMessageKey.NOT_FOUND_USER));
    if (user.getType().compareTo(TypeAccount.EXTERNAL) == 0) {
      List<Path> paths =
          repository.getListOrEntity(Map.ofEntries(entry("type", TypeAccount.EXTERNAL.toString())),
              "", 0, 0, "").get();
      if (paths.size() == 0) {
        return Optional.of(new ArrayList<>());
      }
      return Optional
          .of(paths.stream().map(thisPath -> thisPath.getPath()).collect(Collectors.toList()));
    } else {
      List<Path> paths =
          repository.getListOrEntity(Map.ofEntries(entry("userId", loginId)), "", 0, 0, "").get();
      if (paths.size() == 0) {
        return Optional.of(new ArrayList<>());
      }
      return Optional
          .of(paths.stream().map(thisPath -> thisPath.getPath()).collect(Collectors.toList()));
    }
  }

  @Override
  public void updatePath(PathRequest pathRequest, String loginId, String id) {
    validate(pathRequest);
    Path path = repository.getEntityByAttribute(id, "_id")
        .orElseThrow(() -> new ResourceNotFoundException(LanguageMessageKey.PATH_NOTFOUND));
    Map<String, String> error = generateError(PathRequest.class);
    repository.getEntityByAttribute(pathRequest.getLabel(), "label").ifPresent(thisPath -> {
      if (thisPath.get_id().toString().compareTo(id) != 0) {
        error.put("label", LanguageMessageKey.LABEL_EXISTED);
        throw new InvalidRequestException(error, LanguageMessageKey.LABEL_EXISTED);
      }
    });
    repository.getEntityByAttribute(pathRequest.getPath(), "path").ifPresent(thisPath -> {
      if (thisPath.get_id().toString().compareTo(id) != 0) {
        error.put("path", LanguageMessageKey.PATH_EXISTED);
        throw new InvalidRequestException(error, LanguageMessageKey.PATH_EXISTED);
      }
    });
    if (pathRequest.getType().compareTo("EXTERNAL") != 0
        && pathRequest.getType().compareTo("INTERNAL") != 0) {
      error.put("type", LanguageMessageKey.TYPE_PATH_INVALID);
      throw new InvalidRequestException(error, LanguageMessageKey.TYPE_PATH_INVALID);
    }
    User adminUser = userRepository.getEntityByAttribute("super_admin_dev", "username")
        .orElseThrow(() -> new BadSqlException(LanguageMessageKey.SERVER_ERROR));
    List<ObjectId> listUserId = new ArrayList<>();
    if (pathRequest.getUserId().size() > 0) {
      pathRequest.getUserId().forEach(thisId -> {
        if (!listUserId.contains(new ObjectId(thisId))
            && userRepository.getEntityByAttribute(thisId, "_id").isPresent()) {
          listUserId.add(new ObjectId(thisId));
        }
      });
    }
    if (!listUserId.contains(adminUser.get_id())) {
      listUserId.add(adminUser.get_id());
    }
    if (pathRequest.getType().compareTo("EXTERNAL") == 0) {
      path.setType(TypeAccount.EXTERNAL);
    }
    if (pathRequest.getType().compareTo("INTERNAL") == 0) {
      path.setType(TypeAccount.INTERNAL);
    }
    if (pathRequest.getIcon().length() > 0
        && !pathRequest.getIcon().startsWith(TypeValidation.PATH_PRE_FIX)) {
      error.put("type", LanguageMessageKey.INVALID_PATH_ICON);
      throw new InvalidRequestException(error, LanguageMessageKey.INVALID_PATH_ICON);
    }
    sendNotificationUpdate(path.getUserId(), listUserId);
    path.setUserId(listUserId);
    path.setLabel(pathRequest.getLabel());
    path.setIcon(pathRequest.getIcon());
    path.setPath(pathRequest.getPath());
    repository.insertAndUpdate(path);
  }

}
