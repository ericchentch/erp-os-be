package com.chilleric.franchise_sys.service.path;

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
import com.chilleric.franchise_sys.exception.InvalidRequestException;
import com.chilleric.franchise_sys.exception.ResourceNotFoundException;
import com.chilleric.franchise_sys.inventory.path.PathInventory;
import com.chilleric.franchise_sys.repository.accessability.Accessability;
import com.chilleric.franchise_sys.repository.path.Path;
import com.chilleric.franchise_sys.repository.path.PathRepository;
import com.chilleric.franchise_sys.service.AbstractService;

@Service
public class PathServiceImpl extends AbstractService<PathRepository> implements PathService {

    @Autowired
    private PathInventory pathInventory;

    @Override
    public Optional<ListWrapperResponse<PathResponse>> getPaths(Map<String, String> allParams,
            String keySort, int page, int pageSize, String sortField, String loginId) {
        List<Path> paths = repository.getPaths(allParams, keySort, page, pageSize, sortField).get();
        return Optional.of(new ListWrapperResponse<>(
                paths.stream()
                        .map(path -> new PathResponse(path.get_id().toString(), path.getLabel(),
                                path.getPath()))
                        .collect(Collectors.toList()),
                page, pageSize, repository.getTotal(allParams)));
    }

    @Override
    public void addNewPath(PathRequest pathRequest, String loginId) {
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
        Path path = new Path(newId, pathRequest.getLabel(), pathRequest.getPath());
        accessabilityRepository
                .addNewAccessability(new Accessability(null, new ObjectId(loginId), newId));
        repository.insertAndUpdate(path);

    }

    @Override
    public void deletePath(String id) {
        pathInventory.findPathById(id)
                .orElseThrow(() -> new ResourceNotFoundException(LanguageMessageKey.PATH_NOTFOUND));
        repository.deletePath(id);
    }

}
