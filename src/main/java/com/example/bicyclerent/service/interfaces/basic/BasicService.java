package com.example.bicyclerent.service.interfaces.basic;

import java.util.List;

public interface BasicService<T, D>{
  T create(D dto);

  T get(Long id);

  List<T> getAll();

  T update(D dto);

  boolean delete(Long id);
}
