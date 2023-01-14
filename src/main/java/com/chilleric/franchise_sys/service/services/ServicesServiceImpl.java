package com.chilleric.franchise_sys.service.services;

import com.chilleric.franchise_sys.constant.LanguageMessageKey;
import com.chilleric.franchise_sys.dto.common.ListWrapperResponse;
import com.chilleric.franchise_sys.dto.service.ServiceRequest;
import com.chilleric.franchise_sys.dto.service.ServiceResponse;
import com.chilleric.franchise_sys.exception.BadSqlException;
import com.chilleric.franchise_sys.exception.ResourceNotFoundException;
import com.chilleric.franchise_sys.inventory.service.ServiceInventory;
import com.chilleric.franchise_sys.repository.informationRepository.service.ServiceRepository;
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
public class ServicesServiceImpl extends AbstractService<ServiceRepository> implements ServicesService {
    @Autowired
    private ServiceInventory serviceInventory;

    @Override
    public void createService(ServiceRequest serviceRequest) {
        validate(serviceRequest);
        com.chilleric.franchise_sys.repository.informationRepository.service.Service service
            = objectMapper.convertValue(serviceRequest, com.chilleric.franchise_sys.repository.informationRepository.service.Service.class);
        ObjectId serviceId = new ObjectId();
        service.set_id(serviceId);
        service.setName(serviceRequest.getServiceName());
        service.setPrice(serviceRequest.getPrice());
        repository.insertAndUpdate(service);
    }

    @Override
    public Optional<ListWrapperResponse<ServiceResponse>> getServices(Map<String, String> allParams, String keySort, int page, int pageSize, String sortField) {
        List<com.chilleric.franchise_sys.repository.informationRepository.service.Service> services =
            repository.getServices(allParams, keySort, page, pageSize, sortField).get();
        return Optional.of(new ListWrapperResponse<>(services.stream()
            .map(service -> new ServiceResponse(service.get_id().toString(), service.getName(),
                service.getPrice())).collect(Collectors.toList()), page, pageSize, repository.getTotalPage(allParams)));
    }

    @Override
    public Optional<ServiceResponse> getServiceById(String serviceId) {
        com.chilleric.franchise_sys.repository.informationRepository.service.Service service = serviceInventory.findServiceById(serviceId)
            .orElseThrow(() -> new ResourceNotFoundException(LanguageMessageKey.SERVICE_NOT_FOUND));
        return Optional.of(new ServiceResponse(service.get_id().toString(), service.getName(), service.getPrice()));
    }

    @Override
    public Optional<ServiceResponse> searchServiceByName(String serviceName) {
        return Optional.empty();
    }

    @Override
    public void updateService(ServiceRequest serviceRequest, String serviceId) {
        validate(serviceRequest);
        com.chilleric.franchise_sys.repository.informationRepository.service.Service service = serviceInventory.findServiceById(serviceId).orElseThrow(
            () -> new ResourceNotFoundException(LanguageMessageKey.SERVICE_NOT_FOUND));
        String normalizeName = StringUtils.normalizeString(serviceRequest.getServiceName());
        service.setName(normalizeName);
        service.setPrice(serviceRequest.getPrice());
        repository.insertAndUpdate(service);
    }

    @Override
    public void deleteService(String serviceId) {
        serviceInventory.findServiceById(serviceId).orElseThrow(
            () -> new ResourceNotFoundException(LanguageMessageKey.SERVICE_NOT_FOUND)
        );
        repository.deleteService(serviceId);
    }
}
