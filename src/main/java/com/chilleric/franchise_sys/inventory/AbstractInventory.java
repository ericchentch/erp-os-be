package com.chilleric.franchise_sys.inventory;

import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractInventory<r> {
  @Autowired
  protected r repository;
}
