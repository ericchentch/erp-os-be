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
import com.chilleric.franchise_sys.inventory.navbar.NavbarInventory;
import com.chilleric.franchise_sys.inventory.path.PathInventory;
import com.chilleric.franchise_sys.repository.systemRepository.accessability.Accessability;
import com.chilleric.franchise_sys.repository.systemRepository.navbar.ContentNavbar;
import com.chilleric.franchise_sys.repository.systemRepository.navbar.Navbar;
import com.chilleric.franchise_sys.repository.systemRepository.navbar.NavbarRepository;
import com.chilleric.franchise_sys.repository.systemRepository.path.Path;
import com.chilleric.franchise_sys.service.AbstractService;

@Service
public class NavbarServiceImpl extends AbstractService<NavbarRepository> implements NavbarService {

  @Autowired
  private NavbarInventory navbarInventory;

  @Autowired
  private PathInventory pathInventory;

  @Override
  public Optional<ListWrapperResponse<NavbarResponse>> getListNavbar(Map<String, String> allParams,
      String keySort, int page, int pageSize, String sortField, String loginId) {
    List<Navbar> navbarList =
        repository.getNavbarList(allParams, keySort, page, pageSize, sortField).get();
    return Optional
        .of(new ListWrapperResponse<>(navbarList.stream()
            .map(navbar -> new NavbarResponse(navbar.get_id().toString(), navbar.getName(),
                navbar.getContent().stream()
                    .filter(thisMain -> pathInventory
                        .findPathById(thisMain.getMainItem().toString()).isPresent())
                    .map(thisMain -> {
                      Path mainPath =
                          pathInventory.findPathById(thisMain.getMainItem().toString()).get();
                      List<PathResponse> result = new ArrayList<>();
                      thisMain.getChildrenItem().forEach(thisChild -> {
                        pathInventory.findPathById(thisChild.toString()).ifPresent(thisChildId -> {
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
                          .filter(
                              thisMainPath -> thisMainPath.compareTo(new ObjectId(loginId)) == 0)
                          .findFirst().isPresent()
                              ? new PathResponse(mainPath.get_id().toString(), mainPath.getLabel(),
                                  mainPath.getPath(), mainPath.getType(), new ArrayList<>(),
                                  mainPath.getIcon())
                              : new PathResponse(),
                          result);
                    }).collect(Collectors.toList())))
            .collect(Collectors.toList()), page, pageSize, repository.getTotal(allParams)));
  }

  @Override
  public Optional<NavbarResponse> getNavbarDetailById(String id, String loginId) {
    Navbar navbar = navbarInventory.findNavbarById(id)
        .orElseThrow(() -> new ResourceNotFoundException(LanguageMessageKey.NAVBAR_NOT_FOUND));
    return Optional.of(new NavbarResponse(navbar.get_id().toString(), navbar.getName(),
        navbar.getContent().stream().filter(
            thisMain -> pathInventory.findPathById(thisMain.getMainItem().toString()).isPresent())
            .map(thisMain -> {
              Path mainPath = pathInventory.findPathById(thisMain.getMainItem().toString()).get();
              List<PathResponse> result = new ArrayList<>();
              thisMain.getChildrenItem().forEach(thisChild -> {
                pathInventory.findPathById(thisChild.toString()).ifPresent(thisChildId -> {
                  if (thisChildId.getUserId().stream()
                      .filter(thisChildItem -> thisChildItem.compareTo(new ObjectId(loginId)) == 0)
                      .findFirst().isPresent()) {
                    result.add(new PathResponse(thisChildId.get_id().toString(),
                        thisChildId.getLabel(), thisChildId.getPath(), thisChildId.getType(),
                        new ArrayList<>(), thisChildId.getIcon()));
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
            }).collect(Collectors.toList())));
  }

  @Override
  public Optional<NavbarResponse> getNavbarDetailByName(String name, String loginId) {
    Navbar navbar = navbarInventory.findNavbarByName(name)
        .orElseThrow(() -> new ResourceNotFoundException(LanguageMessageKey.NAVBAR_NOT_FOUND));
    return Optional.of(new NavbarResponse(navbar.get_id().toString(), navbar.getName(),
        navbar.getContent().stream().filter(
            thisMain -> pathInventory.findPathById(thisMain.getMainItem().toString()).isPresent())
            .map(thisMain -> {
              Path mainPath = pathInventory.findPathById(thisMain.getMainItem().toString()).get();
              List<PathResponse> result = new ArrayList<>();
              thisMain.getChildrenItem().forEach(thisChild -> {
                pathInventory.findPathById(thisChild.toString()).ifPresent(thisChildId -> {
                  if (thisChildId.getUserId().stream()
                      .filter(thisChildItem -> thisChildItem.compareTo(new ObjectId(loginId)) == 0)
                      .findFirst().isPresent()) {
                    result.add(new PathResponse(thisChildId.get_id().toString(),
                        thisChildId.getLabel(), thisChildId.getPath(), thisChildId.getType(),
                        new ArrayList<>(), thisChildId.getIcon()));
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
            }).collect(Collectors.toList())));
  }

  @Override
  public void updateNavbar(NavbarRequest navbarRequest, String loginId, String id) {
    validate(navbarRequest);
    Map<String, String> error = new HashMap<>();
    navbarInventory.findNavbarById(id)
        .orElseThrow(() -> new ResourceNotFoundException(LanguageMessageKey.NAVBAR_NOT_FOUND));
    navbarInventory.findNavbarByName(navbarRequest.getName()).ifPresent(thisNav -> {
      error.put("name", LanguageMessageKey.NAVBAR_EXISTED);
      throw new InvalidRequestException(error, LanguageMessageKey.NAVBAR_EXISTED);
    });
    List<ContentNavbar> contentUpdate = new ArrayList<>();
    navbarRequest.getContent().forEach(thisId -> {
      if (accessabilityRepository.getAccessability(loginId, thisId.getMainItem()).isPresent()
          && pathInventory.findPathById(thisId.getMainItem()).isPresent()) {
        List<ObjectId> listChild = new ArrayList<>();
        thisId.getChildrenItem().forEach(thisChild -> {
          if (accessabilityRepository.getAccessability(loginId, thisChild).isPresent()
              && pathInventory.findPathById(thisChild).isPresent()) {
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
  public void addNewNavbar(NavbarRequest navbarRequest, String loginId) {
    validate(navbarRequest);
    Map<String, String> error = new HashMap<>();
    navbarInventory.findNavbarByName(navbarRequest.getName()).ifPresent(thisNav -> {
      error.put("name", LanguageMessageKey.NAVBAR_EXISTED);
      throw new InvalidRequestException(error, LanguageMessageKey.NAVBAR_EXISTED);
    });
    List<ContentNavbar> contentUpdate = new ArrayList<>();
    navbarRequest.getContent().forEach(thisId -> {
      if (accessabilityRepository.getAccessability(loginId, thisId.getMainItem()).isPresent()
          && pathInventory.findPathById(thisId.getMainItem()).isPresent()) {
        List<ObjectId> listChild = new ArrayList<>();
        thisId.getChildrenItem().forEach(thisChild -> {
          if (accessabilityRepository.getAccessability(loginId, thisChild).isPresent()
              && pathInventory.findPathById(thisChild).isPresent()) {
            listChild.add(new ObjectId(thisChild));
          }
        });
        contentUpdate.add(new ContentNavbar(new ObjectId(thisId.getMainItem()), listChild));
      }
    });
    ObjectId newId = new ObjectId();
    accessabilityRepository
        .addNewAccessability(new Accessability(null, new ObjectId(loginId), newId, true));
    repository.insertAndUpdate(new Navbar(newId, navbarRequest.getName(), contentUpdate));
  }

  @Override
  public void deleteNavbar(String id) {
    navbarInventory.findNavbarById(id)
        .orElseThrow(() -> new ResourceNotFoundException(LanguageMessageKey.NAVBAR_NOT_FOUND));
    repository.deletePath(id);

  }

}
