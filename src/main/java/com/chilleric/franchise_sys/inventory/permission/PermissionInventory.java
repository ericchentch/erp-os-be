package com.chilleric.franchise_sys.inventory.permission;

import java.util.Optional;
import com.chilleric.franchise_sys.repository.systemRepository.permission.Permission;

public interface PermissionInventory {
    Optional<Permission> getPermissionByName(String name);

    Optional<Permission> getPermissionById(String id);
}
