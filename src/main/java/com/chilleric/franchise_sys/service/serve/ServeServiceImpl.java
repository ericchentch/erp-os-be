package com.chilleric.franchise_sys.service.serve;

import com.chilleric.franchise_sys.constant.LanguageMessageKey;
import com.chilleric.franchise_sys.dto.common.ListWrapperResponse;
import com.chilleric.franchise_sys.dto.serve.ServeRequest;
import com.chilleric.franchise_sys.dto.serve.ServeResponse;
import com.chilleric.franchise_sys.exception.ResourceNotFoundException;
import com.chilleric.franchise_sys.inventory.service.ServeInventory;
import com.chilleric.franchise_sys.repository.informationRepository.serve.ServeRepository;
import com.chilleric.franchise_sys.repository.informationRepository.serve.Serve;
import com.chilleric.franchise_sys.service.AbstractService;
import com.chilleric.franchise_sys.utils.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ServeServiceImpl extends AbstractService<ServeRepository> implements ServeService {
    @Autowired
    private ServeInventory serveInventory;

    @Override
    public void createServe(ServeRequest serveRequest) {
        validate(serveRequest);
        Serve serve
            = objectMapper.convertValue(serveRequest, Serve.class);
        ObjectId serviceId = new ObjectId();
        serve.set_id(serviceId);
        serve.setName(serveRequest.getServiceName());
        serve.setPrice(serveRequest.getPrice());
        repository.insertAndUpdate(serve);
    }

    @Override
    public Optional<ListWrapperResponse<ServeResponse>> getServes(Map<String, String> allParams, String keySort, int page, int pageSize, String sortField) {
        List<Serve> Serves =
            repository.getServes(allParams, keySort, page, pageSize, sortField).get();
        return Optional.of(new ListWrapperResponse<>(Serves.stream()
            .map(serve -> new ServeResponse(serve.get_id().toString(), serve.getName(),
                serve.getPrice())).collect(Collectors.toList()), page, pageSize, repository.getTotalPage(allParams)));
    }

    @Override
    public Optional<ServeResponse> getServeById(String serviceId) {
        Serve serve = serveInventory.findServeById(serviceId)
            .orElseThrow(() -> new ResourceNotFoundException(LanguageMessageKey.SERVICE_NOT_FOUND));
        return Optional.of(new ServeResponse(serve.get_id().toString(), serve.getName(), serve.getPrice()));
    }

    @Override
    public Optional<ServeResponse> searchServeByName(String serviceName) {
        return Optional.empty();
    }

    @Override
    public void updateServe(ServeRequest serveRequest, String serviceId) {
        validate(serveRequest);
        Serve serve = serveInventory.findServeById(serviceId).orElseThrow(
            () -> new ResourceNotFoundException(LanguageMessageKey.SERVICE_NOT_FOUND));
        String normalizeName = StringUtils.normalizeString(serveRequest.getServiceName());
        serve.setName(normalizeName);
        serve.setPrice(serveRequest.getPrice());
        repository.insertAndUpdate(serve);
    }

    @Override
    public void deleteServe(String serviceId) {
        serveInventory.findServeById(serviceId).orElseThrow(
            () -> new ResourceNotFoundException(LanguageMessageKey.SERVICE_NOT_FOUND)
        );
        repository.deleteServe(serviceId);
    }
}
