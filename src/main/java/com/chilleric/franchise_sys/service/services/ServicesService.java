package com.chilleric.franchise_sys.service.services;

import com.chilleric.franchise_sys.dto.common.ListWrapperResponse;
import com.chilleric.franchise_sys.dto.service.ServiceRequest;
import com.chilleric.franchise_sys.dto.service.ServiceResponse;

import java.util.Map;
import java.util.Optional;

public interface ServicesService {
    void createService(ServiceRequest serviceRequest);

    Optional <ListWrapperResponse<ServiceResponse>> getServices (Map<String, String> allParams, String keySort,
                                                                 int page, int pageSize, String sortField);

    Optional<ServiceResponse> getServiceById (String serviceId);

    Optional<ServiceResponse> searchServiceByName (String serviceName);

    void updateService(ServiceRequest serviceRequest, String serviceId);

    void deleteService(String serviceId);
}
