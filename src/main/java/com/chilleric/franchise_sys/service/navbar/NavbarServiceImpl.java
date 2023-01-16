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
import com.chilleric.franchise_sys.dto.navbar.NavbarRequest;
import com.chilleric.franchise_sys.dto.navbar.NavbarResponse;
import com.chilleric.franchise_sys.dto.path.PathResponse;
import com.chilleric.franchise_sys.exception.InvalidRequestException;
import com.chilleric.franchise_sys.exception.ResourceNotFoundException;
import com.chilleric.franchise_sys.inventory.navbar.NavbarInventory;
import com.chilleric.franchise_sys.inventory.path.PathInventory;
import com.chilleric.franchise_sys.inventory.user.UserInventory;
import com.chilleric.franchise_sys.repository.systemRepository.accessability.Accessability;
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

        @Autowired
        private UserInventory userInventory;

        @Override
        public Optional<ListWrapperResponse<NavbarResponse>> getListNavbar(
                        Map<String, String> allParams, String keySort, int page, int pageSize,
                        String sortField, String loginId) {
                List<Navbar> navbarList = repository
                                .getNavbarList(allParams, keySort, page, pageSize, sortField).get();
                return Optional.of(new ListWrapperResponse<>(navbarList.stream()
                                .map(navbar -> new NavbarResponse(navbar.get_id().toString(),
                                                navbar.getName(),
                                                navbar.getUserIds().stream()
                                                                .map(thisId -> thisId.toString())
                                                                .collect(Collectors.toList()),
                                                navbar.getMainSidebar().stream()
                                                                .filter(thisPath -> pathInventory
                                                                                .findPathById(thisPath
                                                                                                .toString())
                                                                                .isPresent())
                                                                .map(thisPath -> {
                                                                        Path path = pathInventory
                                                                                        .findPathById(thisPath
                                                                                                        .toString())
                                                                                        .get();
                                                                        return new PathResponse(path
                                                                                        .get_id()
                                                                                        .toString(),
                                                                                        path.getLabel(),
                                                                                        path.getPath(),
                                                                                        path.getType(),
                                                                                        path.getIcon());
                                                                }).collect(Collectors.toList()),
                                                navbar.getChildrenSidebar().stream()
                                                                .filter(thisPath -> pathInventory
                                                                                .findPathById(thisPath
                                                                                                .toString())
                                                                                .isPresent())
                                                                .map(thisPath -> {
                                                                        Path path = pathInventory
                                                                                        .findPathById(thisPath
                                                                                                        .toString())
                                                                                        .get();
                                                                        return new PathResponse(path
                                                                                        .get_id()
                                                                                        .toString(),
                                                                                        path.getLabel(),
                                                                                        path.getPath(),
                                                                                        path.getType(),
                                                                                        path.getIcon());
                                                                }).collect(Collectors.toList())))
                                .collect(Collectors.toList()), page, pageSize,
                                repository.getTotal(allParams)));
        }

        @Override
        public Optional<NavbarResponse> getNavbarDetailById(String id) {
                Navbar navbar = navbarInventory.findNavbarById(id)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                LanguageMessageKey.NAVBAR_NOT_FOUND));
                return Optional.of(new NavbarResponse(navbar.get_id().toString(), navbar.getName(),
                                navbar.getUserIds().stream().map(thisId -> thisId.toString())
                                                .collect(Collectors.toList()),

                                navbar.getMainSidebar().stream()
                                                .filter(thisPath -> pathInventory
                                                                .findPathById(thisPath.toString())
                                                                .isPresent())
                                                .map(thisPath -> {
                                                        Path path = pathInventory
                                                                        .findPathById(thisPath
                                                                                        .toString())
                                                                        .get();
                                                        return new PathResponse(
                                                                        path.get_id().toString(),
                                                                        path.getLabel(),
                                                                        path.getPath(),
                                                                        path.getType(),
                                                                        path.getIcon());
                                                }).collect(Collectors.toList()),
                                navbar.getChildrenSidebar().stream()
                                                .filter(thisPath -> pathInventory
                                                                .findPathById(thisPath.toString())
                                                                .isPresent())
                                                .map(thisPath -> {
                                                        Path path = pathInventory
                                                                        .findPathById(thisPath
                                                                                        .toString())
                                                                        .get();
                                                        return new PathResponse(
                                                                        path.get_id().toString(),
                                                                        path.getLabel(),
                                                                        path.getPath(),
                                                                        path.getType(),
                                                                        path.getIcon());
                                                }).collect(Collectors.toList())));
        }

        @Override
        public Optional<NavbarResponse> getNavbarDetailByName(String name) {
                Navbar navbar = navbarInventory.findNavbarByName(name)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                LanguageMessageKey.NAVBAR_NOT_FOUND));
                return Optional.of(new NavbarResponse(navbar.get_id().toString(), navbar.getName(),
                                navbar.getUserIds().stream().map(thisId -> thisId.toString())
                                                .collect(Collectors.toList()),

                                navbar.getMainSidebar().stream()
                                                .filter(thisPath -> pathInventory
                                                                .findPathById(thisPath.toString())
                                                                .isPresent())
                                                .map(thisPath -> {
                                                        Path path = pathInventory
                                                                        .findPathById(thisPath
                                                                                        .toString())
                                                                        .get();
                                                        return new PathResponse(
                                                                        path.get_id().toString(),
                                                                        path.getLabel(),
                                                                        path.getPath(),
                                                                        path.getType(),
                                                                        path.getIcon());
                                                }).collect(Collectors.toList()),
                                navbar.getChildrenSidebar().stream()
                                                .filter(thisPath -> pathInventory
                                                                .findPathById(thisPath.toString())
                                                                .isPresent())
                                                .map(thisPath -> {
                                                        Path path = pathInventory
                                                                        .findPathById(thisPath
                                                                                        .toString())
                                                                        .get();
                                                        return new PathResponse(
                                                                        path.get_id().toString(),
                                                                        path.getLabel(),
                                                                        path.getPath(),
                                                                        path.getType(),
                                                                        path.getIcon());
                                                }).collect(Collectors.toList())));
        }

        @Override
        public void updateNavbar(NavbarRequest navbarRequest, String loginId, String id) {
                validate(navbarRequest);
                Map<String, String> error = new HashMap<>();
                navbarInventory.findNavbarById(id).orElseThrow(() -> new ResourceNotFoundException(
                                LanguageMessageKey.NAVBAR_NOT_FOUND));
                navbarInventory.findNavbarByName(navbarRequest.getName()).ifPresent(thisNav -> {
                        error.put("name", LanguageMessageKey.NAVBAR_EXISTED);
                        throw new InvalidRequestException(error, LanguageMessageKey.NAVBAR_EXISTED);
                });
                List<ObjectId> userIdsUpdate = new ArrayList<>();
                navbarRequest.getUserIds().forEach(thisId -> {
                        if (accessabilityRepository.getAccessability(loginId, thisId).isPresent()
                                        && userInventory.findUserById(thisId.toString())
                                                        .isPresent()) {
                                userIdsUpdate.add(new ObjectId(thisId));
                        }
                });
                List<ObjectId> mainUpdate = new ArrayList<>();
                navbarRequest.getMainSidebar().forEach(thisId -> {
                        if (accessabilityRepository.getAccessability(loginId, thisId).isPresent()
                                        && pathInventory.findPathById(thisId).isPresent()) {
                                mainUpdate.add(new ObjectId(thisId));
                        }
                });
                List<ObjectId> childrenUpdate = new ArrayList<>();
                navbarRequest.getChildrenSidebar().forEach(thisId -> {
                        if (accessabilityRepository.getAccessability(loginId, thisId).isPresent()
                                        && pathInventory.findPathById(thisId).isPresent()) {
                                childrenUpdate.add(new ObjectId(thisId));
                        }
                });
                repository.insertAndUpdate(new Navbar(new ObjectId(id), navbarRequest.getName(),
                                userIdsUpdate, mainUpdate, childrenUpdate));
        }

        @Override
        public void addNewNavbar(NavbarRequest navbarRequest, String loginId) {
                validate(navbarRequest);
                Map<String, String> error = new HashMap<>();
                navbarInventory.findNavbarByName(navbarRequest.getName()).ifPresent(thisNav -> {
                        error.put("name", LanguageMessageKey.NAVBAR_EXISTED);
                        throw new InvalidRequestException(error, LanguageMessageKey.NAVBAR_EXISTED);
                });
                List<ObjectId> userIdsUpdate = new ArrayList<>();
                navbarRequest.getUserIds().forEach(thisId -> {
                        if (accessabilityRepository.getAccessability(loginId, thisId).isPresent()
                                        && userInventory.findUserById(thisId.toString())
                                                        .isPresent()) {
                                userIdsUpdate.add(new ObjectId(thisId));
                        }
                });
                List<ObjectId> mainUpdate = new ArrayList<>();
                navbarRequest.getMainSidebar().forEach(thisId -> {
                        if (accessabilityRepository.getAccessability(loginId, thisId).isPresent()
                                        && pathInventory.findPathById(thisId).isPresent()) {
                                mainUpdate.add(new ObjectId(thisId));
                        }
                });
                List<ObjectId> childrenUpdate = new ArrayList<>();
                navbarRequest.getChildrenSidebar().forEach(thisId -> {
                        if (accessabilityRepository.getAccessability(loginId, thisId).isPresent()
                                        && pathInventory.findPathById(thisId).isPresent()) {
                                childrenUpdate.add(new ObjectId(thisId));
                        }
                });
                ObjectId newId = new ObjectId();
                accessabilityRepository.addNewAccessability(
                                new Accessability(null, new ObjectId(loginId), newId, true));
                repository.insertAndUpdate(new Navbar(newId, navbarRequest.getName(), userIdsUpdate,
                                mainUpdate, childrenUpdate));
        }

        @Override
        public void deleteNavbar(String id) {
                navbarInventory.findNavbarById(id).orElseThrow(() -> new ResourceNotFoundException(
                                LanguageMessageKey.NAVBAR_NOT_FOUND));
                repository.deletePath(id);

        }

}
