package com.chilleric.franchise_sys.repository.abstract_repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface RepositoryInterface<T> {
  Optional<T> getEntityByAttribute(String value, String attribute);
  Optional<List<T>> getListByAttribute(String value, String attribute);
  Optional<List<T>> getListOrEntity(
    Map<String, String> allParams,
    String keySort,
    int page,
    int pageSize,
    String sortField
  );
  void insertAndUpdate(T entity);
  long getTotalPage(Map<String, String> allParams);
  void deleteById(String id);
}
