package com.chilleric.franchise_sys.inventory.navbar;

import static java.util.Map.entry;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.chilleric.franchise_sys.inventory.AbstractInventory;
import com.chilleric.franchise_sys.repository.systemRepository.navbar.Navbar;
import com.chilleric.franchise_sys.repository.systemRepository.navbar.NavbarRepository;

@Service
public class NavbarInventoryImpl extends AbstractInventory<NavbarRepository>
    implements NavbarInventory {

  @Override
  public Optional<Navbar> findNavbarByName(String name) {
    List<Navbar> navbar =
        repository.getNavbarList(Map.ofEntries(entry("name", name)), "", 0, 0, "").get();
    if (navbar.size() == 0)
      return Optional.empty();
    return Optional.of(navbar.get(0));
  }

  @Override
  public Optional<Navbar> findNavbarById(String id) {
    List<Navbar> navbar =
        repository.getNavbarList(Map.ofEntries(entry("_id", id)), "", 0, 0, "").get();
    if (navbar.size() == 0)
      return Optional.empty();
    return Optional.of(navbar.get(0));
  }

  @Override
  public Optional<Navbar> findNavbarByUserId(String userId) {
    List<Navbar> navbar =
        repository.getNavbarList(Map.ofEntries(entry("userIds", userId)), "", 0, 0, "").get();
    if (navbar.size() == 0)
      return Optional.empty();
    return Optional.of(navbar.get(0));
  }

}
