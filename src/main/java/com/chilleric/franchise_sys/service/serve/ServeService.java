package com.chilleric.franchise_sys.service.serve;

import com.chilleric.franchise_sys.dto.common.ListWrapperResponse;
import com.chilleric.franchise_sys.dto.serve.ServeRequest;
import com.chilleric.franchise_sys.dto.serve.ServeResponse;

import java.util.Map;
import java.util.Optional;

public interface ServeService {
    void createServe(ServeRequest serveRequest);

    Optional <ListWrapperResponse<ServeResponse>> getServes (Map<String, String> allParams, String keySort,
                                                               int page, int pageSize, String sortField);

    Optional<ServeResponse> getServeById (String serveId);

    Optional<ServeResponse> searchServeByName (String serveName);

    void updateServe(ServeRequest serveRequest, String serveId);

    void deleteServe(String serviceId);
}
