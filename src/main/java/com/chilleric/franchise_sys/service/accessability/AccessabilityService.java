package com.chilleric.franchise_sys.service.accessability;

import com.chilleric.franchise_sys.dto.accessability.AccessabilityRequest;
import com.chilleric.franchise_sys.dto.accessability.AccessabilityResponse;
import java.util.List;
import java.util.Optional;

public interface AccessabilityService {
  Optional<List<AccessabilityResponse>> getAccessByTargetId(
    String targetId,
    String loginId
  );

  void shareAccess(
    AccessabilityRequest accessabilityRequest,
    String loginId,
    String targetId,
    boolean isServer
  );
}
