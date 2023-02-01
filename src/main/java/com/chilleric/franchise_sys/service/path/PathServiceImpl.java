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
import com.chilleric.franchise_sys.dto.common.ListWrapperResponse;
import com.chilleric.franchise_sys.dto.path.PathRequest;
import com.chilleric.franchise_sys.dto.path.PathResponse;
import com.chilleric.franchise_sys.exception.BadSqlException;
import com.chilleric.franchise_sys.exception.InvalidRequestException;
import com.chilleric.franchise_sys.exception.ResourceNotFoundException;
import com.chilleric.franchise_sys.inventory.path.PathInventory;
import com.chilleric.franchise_sys.inventory.user.UserInventory;
import com.chilleric.franchise_sys.repository.systemRepository.accessability.Accessability;
import com.chilleric.franchise_sys.repository.systemRepository.path.Path;
import com.chilleric.franchise_sys.repository.systemRepository.path.PathRepository;
import com.chilleric.franchise_sys.repository.systemRepository.user.User;
import com.chilleric.franchise_sys.repository.systemRepository.user.User.TypeAccount;
import com.chilleric.franchise_sys.service.AbstractService;

@Service
public class PathServiceImpl extends AbstractService<PathRepository> implements PathService {

  private static String PATH_PRE_FIX = "data:image/svg+xml;base64,";

  private static int foundAdmin = 0;

  @Autowired
  private PathInventory pathInventory;

  @Autowired
  private UserInventory userInventory;

  @Override
  public Optional<ListWrapperResponse<PathResponse>> getPaths(Map<String, String> allParams,
      String keySort, int page, int pageSize, String sortField, String loginId) {
    List<Path> paths = repository.getPaths(allParams, keySort, page, pageSize, sortField).get();
    return Optional.of(new ListWrapperResponse<>(
        paths.stream()
            .map(path -> new PathResponse(path.get_id().toString(), path.getLabel(), path.getPath(),
                path.getType(),
                path.getUserId().stream().map(thisId -> thisId.toString())
                    .collect(Collectors.toList()),
                path.getIcon()))
            .collect(Collectors.toList()),
        page, pageSize, repository.getTotal(allParams)));
  }

  @Override
  public void addNewPath(PathRequest pathRequest, String loginId, boolean isServer) {
    validate(pathRequest);
    Map<String, String> error = generateError(PathRequest.class);
    pathInventory.findPathByLabel(pathRequest.getLabel()).ifPresent(path -> {
      error.put("label", LanguageMessageKey.LABEL_EXISTED);
      throw new InvalidRequestException(error, LanguageMessageKey.LABEL_EXISTED);
    });
    pathInventory.findPathByPath(pathRequest.getPath()).ifPresent(path -> {
      error.put("path", LanguageMessageKey.PATH_EXISTED);
      throw new InvalidRequestException(error, LanguageMessageKey.PATH_EXISTED);
    });
    ObjectId newId = new ObjectId();
    if (pathRequest.getType().compareTo("EXTERNAL") != 0
        && pathRequest.getType().compareTo("INTERNAL") != 0) {
      error.put("type", LanguageMessageKey.TYPE_PATH_INVALID);
      throw new InvalidRequestException(error, LanguageMessageKey.TYPE_PATH_INVALID);
    }
    User adminUser = userInventory.findUserByUsername("super_admin_dev")
        .orElseThrow(() -> new BadSqlException(LanguageMessageKey.SERVER_ERROR));
    Path path = new Path();
    List<ObjectId> listUserId = new ArrayList<>();
    foundAdmin = 0;
    if (pathRequest.getUserId().size() > 0) {
      pathRequest.getUserId().forEach(thisId -> {
        if (thisId.compareTo(adminUser.get_id().toString()) == 0) {
          foundAdmin++;
        }
        if (accessabilityRepository.getAccessability(loginId, thisId).isPresent()
            && userInventory.findUserById(thisId).isPresent()) {
          listUserId.add(new ObjectId(thisId));
        }
      });
    }
    if (foundAdmin == 0) {
      listUserId.add(adminUser.get_id());
    }
    if (pathRequest.getIcon().length() > 0 && !pathRequest.getIcon().startsWith(PATH_PRE_FIX)) {
      error.put("type", LanguageMessageKey.INVALID_PATH_ICON);
      throw new InvalidRequestException(error, LanguageMessageKey.INVALID_PATH_ICON);
    }
    if (pathRequest.getType().compareTo("EXTERNAL") == 0) {
      path = new Path(newId, pathRequest.getLabel(), pathRequest.getPath(), TypeAccount.EXTERNAL,
          listUserId, pathRequest.getIcon());
    }
    if (pathRequest.getType().compareTo("INTERNAL") == 0) {
      path = new Path(newId, pathRequest.getLabel(), pathRequest.getPath(), TypeAccount.INTERNAL,
          listUserId, pathRequest.getIcon());
    }
    accessabilityRepository
        .addNewAccessability(new Accessability(null, new ObjectId(loginId), newId, true, isServer));
    repository.insertAndUpdate(path);

  }

  @Override
  public void deletePath(String id) {
    pathInventory.findPathById(id)
        .orElseThrow(() -> new ResourceNotFoundException(LanguageMessageKey.PATH_NOTFOUND));
    repository.deletePath(id);
  }

  @Override
  public Optional<PathResponse> getPathDetail(String id) {
    Path path = pathInventory.findPathById(id)
        .orElseThrow(() -> new ResourceNotFoundException(LanguageMessageKey.PATH_NOTFOUND));
    return Optional.of(
        new PathResponse(path.get_id().toString(), path.getLabel(), path.getPath(), path.getType(),
            path.getUserId().stream().map(thisId -> thisId.toString()).collect(Collectors.toList()),
            path.getIcon()));
  }

  @Override
  public Optional<List<String>> getPathAccess(String loginId) {
    User user = userInventory.getActiveUserById(loginId)
        .orElseThrow(() -> new ResourceNotFoundException(LanguageMessageKey.NOT_FOUND_USER));
    if (user.getType().compareTo(TypeAccount.EXTERNAL) == 0) {
      List<Path> paths = repository
          .getPaths(Map.ofEntries(entry("type", TypeAccount.EXTERNAL.toString())), "", 0, 0, "")
          .get();
      if (paths.size() == 0) {
        return Optional.of(new ArrayList<>());
      }
      return Optional
          .of(paths.stream().map(thisPath -> thisPath.getPath()).collect(Collectors.toList()));
    } else {
      List<Path> paths =
          repository.getPaths(Map.ofEntries(entry("userId", loginId)), "", 0, 0, "").get();
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
    Path path = pathInventory.findPathById(id)
        .orElseThrow(() -> new ResourceNotFoundException(LanguageMessageKey.PATH_NOTFOUND));
    Map<String, String> error = generateError(PathRequest.class);
    pathInventory.findPathByLabel(pathRequest.getLabel()).ifPresent(thisPath -> {
      if (thisPath.get_id().toString().compareTo(id) != 0) {
        error.put("label", LanguageMessageKey.LABEL_EXISTED);
        throw new InvalidRequestException(error, LanguageMessageKey.LABEL_EXISTED);
      }
    });
    pathInventory.findPathByPath(pathRequest.getPath()).ifPresent(thisPath -> {
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
    User adminUser = userInventory.findUserByUsername("super_admin_dev")
        .orElseThrow(() -> new BadSqlException(LanguageMessageKey.SERVER_ERROR));
    List<ObjectId> listUserId = new ArrayList<>();
    foundAdmin = 0;
    if (pathRequest.getUserId().size() > 0) {
      pathRequest.getUserId().forEach(thisId -> {
        if (thisId.compareTo(adminUser.get_id().toString()) == 0) {
          foundAdmin++;
        }
        if (accessabilityRepository.getAccessability(loginId, thisId).isPresent()
            && userInventory.findUserById(thisId).isPresent()) {
          listUserId.add(new ObjectId(thisId));
        }
      });
    }
    if (foundAdmin == 0) {
      listUserId.add(adminUser.get_id());
    }
    if (pathRequest.getType().compareTo("EXTERNAL") == 0) {
      path.setType(TypeAccount.EXTERNAL);
    }
    if (pathRequest.getType().compareTo("INTERNAL") == 0) {
      path.setType(TypeAccount.INTERNAL);
    }
    if (pathRequest.getIcon().length() > 0 && !pathRequest.getIcon().startsWith(PATH_PRE_FIX)) {
      error.put("type", LanguageMessageKey.INVALID_PATH_ICON);
      throw new InvalidRequestException(error, LanguageMessageKey.INVALID_PATH_ICON);
    }
    path.setUserId(listUserId);
    path.setLabel(pathRequest.getLabel());
    path.setIcon(pathRequest.getIcon());
    path.setPath(pathRequest.getPath());
    repository.insertAndUpdate(path);
  }

}
