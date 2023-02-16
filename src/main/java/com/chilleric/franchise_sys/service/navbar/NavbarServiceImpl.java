package com.chilleric.franchise_sys.service.navbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.chilleric.franchise_sys.constant.LanguageMessageKey;
import com.chilleric.franchise_sys.dto.common.ListWrapperResponse;
import com.chilleric.franchise_sys.dto.navbar.ContentNavbarResponse;
import com.chilleric.franchise_sys.dto.navbar.NavbarRequest;
import com.chilleric.franchise_sys.dto.navbar.NavbarResponse;
import com.chilleric.franchise_sys.dto.path.PathResponse;
import com.chilleric.franchise_sys.exception.InvalidRequestException;
import com.chilleric.franchise_sys.exception.ResourceNotFoundException;
import com.chilleric.franchise_sys.repository.system_repository.accessability.Accessability;
import com.chilleric.franchise_sys.repository.system_repository.navbar.ContentNavbar;
import com.chilleric.franchise_sys.repository.system_repository.navbar.Navbar;
import com.chilleric.franchise_sys.repository.system_repository.navbar.NavbarRepository;
import com.chilleric.franchise_sys.repository.system_repository.path.Path;
import com.chilleric.franchise_sys.repository.system_repository.path.PathRepository;
import com.chilleric.franchise_sys.service.AbstractService;

@Service
public class NavbarServiceImpl extends AbstractService<NavbarRepository> implements NavbarService {


  @Autowired
  private PathRepository pathRepository;

  @Override
  public Optional<ListWrapperResponse<NavbarResponse>> getListNavbar(Map<String, String> allParams,
      String keySort, int page, int pageSize, String sortField, String loginId) {
    List<Navbar> navbarList =
        repository.getListOrEntity(allParams, keySort, page, pageSize, sortField).get();
    return Optional.of(new ListWrapperResponse<>(navbarList.stream()
        .map(navbar -> new NavbarResponse(navbar.get_id().toString(), navbar.getName(),
            navbar.getContent().stream()
                .filter(thisMain -> pathRepository
                    .getEntityByAttribute(thisMain.getMainItem().toString(), "_id").isPresent())
                .map(thisMain -> {
                  Path mainPath = pathRepository
                      .getEntityByAttribute(thisMain.getMainItem().toString(), "_id").get();
                  List<PathResponse> result = new ArrayList<>();
                  thisMain.getChildrenItem().forEach(thisChild -> {
                    pathRepository.getEntityByAttribute(thisChild.toString(), "_id")
                        .ifPresent(thisChildId -> {
                          if (thisChildId.getUserId().stream().filter(
                              thisChildItem -> thisChildItem.compareTo(new ObjectId(loginId)) == 0)
                              .findFirst().isPresent()) {
                            result.add(new PathResponse(thisChildId.get_id().toString(),
                                thisChildId.getLabel(), thisChildId.getPath(),
                                thisChildId.getType(), new ArrayList<>(), thisChildId.getIcon()));
                          }
                        });;
                  });
                  return new ContentNavbarResponse(mainPath.getUserId().stream()
                      .filter(thisMainPath -> thisMainPath.compareTo(new ObjectId(loginId)) == 0)
                      .findFirst().isPresent()
                          ? new PathResponse(mainPath.get_id().toString(), mainPath.getLabel(),
                              mainPath.getPath(), mainPath.getType(), new ArrayList<>(),
                              mainPath.getIcon())
                          : new PathResponse(),
                      result);
                }).collect(Collectors.toList())))
        .collect(Collectors.toList()), page, pageSize, repository.getTotalPage(allParams)));
  }

  @Override
  public Optional<NavbarResponse> getNavbarDetailById(String id, String loginId) {
    Navbar navbar = repository.getEntityByAttribute(id, "_id")
        .orElseThrow(() -> new ResourceNotFoundException(LanguageMessageKey.NAVBAR_NOT_FOUND));
    List<ContentNavbarResponse> resultContent = new ArrayList<>();
    navbar.getContent().stream()
        .filter(thisMain -> pathRepository
            .getEntityByAttribute(thisMain.getMainItem().toString(), "_id").isPresent())
        .forEach(thisMain -> {
          Path mainPath =
              pathRepository.getEntityByAttribute(thisMain.getMainItem().toString(), "_id").get();
          List<PathResponse> result = new ArrayList<>();
          thisMain.getChildrenItem().forEach(thisChild -> {
            pathRepository.getEntityByAttribute(thisChild.toString(), "_id")
                .ifPresent(thisChildId -> {
                  if (thisChildId.getUserId().stream()
                      .filter(thisChildItem -> thisChildItem.compareTo(new ObjectId(loginId)) == 0)
                      .findFirst().isPresent()) {
                    result.add(new PathResponse(thisChildId.get_id().toString(),
                        thisChildId.getLabel(), thisChildId.getPath(), thisChildId.getType(),
                        new ArrayList<>(), thisChildId.getIcon()));
                  }
                });;
          });
          mainPath.getUserId().stream()
              .filter(thisMainPath -> thisMainPath.compareTo(new ObjectId(loginId)) == 0)
              .findFirst().ifPresent(thisMainPath -> {
                resultContent
                    .add(new ContentNavbarResponse(new PathResponse(mainPath.get_id().toString(),
                        mainPath.getLabel(), mainPath.getPath(), mainPath.getType(),
                        new ArrayList<>(), mainPath.getIcon()), result));
              });

        });
    return Optional
        .of(new NavbarResponse(navbar.get_id().toString(), navbar.getName(), resultContent));
  }

  @Override
  public Optional<NavbarResponse> getNavbarDetailByName(String name, String loginId) {
    Navbar navbar = repository.getEntityByAttribute(name, "name")
        .orElseThrow(() -> new ResourceNotFoundException(LanguageMessageKey.NAVBAR_NOT_FOUND));
    List<ContentNavbarResponse> resultContent = new ArrayList<>();
    navbar.getContent().stream()
        .filter(thisMain -> pathRepository
            .getEntityByAttribute(thisMain.getMainItem().toString(), "_id").isPresent())
        .forEach(thisMain -> {
          Path mainPath =
              pathRepository.getEntityByAttribute(thisMain.getMainItem().toString(), "_id").get();
          List<PathResponse> result = new ArrayList<>();
          thisMain.getChildrenItem().forEach(thisChild -> {
            pathRepository.getEntityByAttribute(thisChild.toString(), "_id")
                .ifPresent(thisChildId -> {
                  if (thisChildId.getUserId().stream()
                      .filter(thisChildItem -> thisChildItem.compareTo(new ObjectId(loginId)) == 0)
                      .findFirst().isPresent()) {
                    result.add(new PathResponse(thisChildId.get_id().toString(),
                        thisChildId.getLabel(), thisChildId.getPath(), thisChildId.getType(),
                        new ArrayList<>(), thisChildId.getIcon()));
                  }
                });;
          });
          mainPath.getUserId().stream()
              .filter(thisMainPath -> thisMainPath.compareTo(new ObjectId(loginId)) == 0)
              .findFirst().ifPresent(thisMainPath -> {
                resultContent
                    .add(new ContentNavbarResponse(new PathResponse(mainPath.get_id().toString(),
                        mainPath.getLabel(), mainPath.getPath(), mainPath.getType(),
                        new ArrayList<>(), mainPath.getIcon()), result));
              });

        });
    return Optional
        .of(new NavbarResponse(navbar.get_id().toString(), navbar.getName(), resultContent));
  }

  @Override
  public void updateNavbar(NavbarRequest navbarRequest, String loginId, String id) {
    validate(navbarRequest);
    Map<String, String> error = new HashMap<>();
    repository.getEntityByAttribute(id, "_id")
        .orElseThrow(() -> new ResourceNotFoundException(LanguageMessageKey.NAVBAR_NOT_FOUND));
    repository.getEntityByAttribute(navbarRequest.getName(), "name").ifPresent(thisNav -> {
      error.put("name", LanguageMessageKey.NAVBAR_EXISTED);
      throw new InvalidRequestException(error, LanguageMessageKey.NAVBAR_EXISTED);
    });
    List<ContentNavbar> contentUpdate = new ArrayList<>();
    navbarRequest.getContent().forEach(thisId -> {
      if (accessabilityRepository.getAccessability(loginId, thisId.getMainItem()).isPresent()
          && pathRepository.getEntityByAttribute(thisId.getMainItem(), "_id").isPresent()) {
        List<ObjectId> listChild = new ArrayList<>();
        thisId.getChildrenItem().forEach(thisChild -> {
          if (accessabilityRepository.getAccessability(loginId, thisChild).isPresent()
              && pathRepository.getEntityByAttribute(thisChild, "_id").isPresent()) {
            listChild.add(new ObjectId(thisChild));
          }
        });
        contentUpdate.add(new ContentNavbar(new ObjectId(thisId.getMainItem()), listChild));
      }
    });
    repository
        .insertAndUpdate(new Navbar(new ObjectId(id), navbarRequest.getName(), contentUpdate));
  }

  @Override
  public void addNewNavbar(NavbarRequest navbarRequest, String loginId, boolean isServer) {
    validate(navbarRequest);
    Map<String, String> error = new HashMap<>();
    repository.getEntityByAttribute(navbarRequest.getName(), "name").ifPresent(thisNav -> {
      error.put("name", LanguageMessageKey.NAVBAR_EXISTED);
      throw new InvalidRequestException(error, LanguageMessageKey.NAVBAR_EXISTED);
    });
    List<ContentNavbar> contentUpdate = new ArrayList<>();
    navbarRequest.getContent().forEach(thisId -> {
      if (accessabilityRepository.getAccessability(loginId, thisId.getMainItem()).isPresent()
          && pathRepository.getEntityByAttribute(thisId.getMainItem(), "id").isPresent()) {
        List<ObjectId> listChild = new ArrayList<>();
        thisId.getChildrenItem().forEach(thisChild -> {
          if (accessabilityRepository.getAccessability(loginId, thisChild).isPresent()
              && pathRepository.getEntityByAttribute(thisChild, "_id").isPresent()) {
            listChild.add(new ObjectId(thisChild));
          }
        });
        contentUpdate.add(new ContentNavbar(new ObjectId(thisId.getMainItem()), listChild));
      }
    });
    ObjectId newId = new ObjectId();
    accessabilityRepository
        .insertAndUpdate(new Accessability(null, new ObjectId(loginId), newId, true, isServer));
    repository.insertAndUpdate(new Navbar(newId, navbarRequest.getName(), contentUpdate));
  }

  @Override
  public void deleteNavbar(String id) {
    repository.getEntityByAttribute(id, "_id")
        .orElseThrow(() -> new ResourceNotFoundException(LanguageMessageKey.NAVBAR_NOT_FOUND));
    repository.deleteById(id);

  }

}
