package com.chilleric.franchise_sys.inventory.navbar;

import java.util.Optional;
import com.chilleric.franchise_sys.repository.systemRepository.navbar.Navbar;

public interface NavbarInventory {
    Optional<Navbar> findNavbarByName(String name);

    Optional<Navbar> findNavbarById(String id);
}
