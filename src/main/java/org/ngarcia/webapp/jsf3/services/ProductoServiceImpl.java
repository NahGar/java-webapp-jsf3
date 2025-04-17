package org.ngarcia.webapp.jsf3.services;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import org.ngarcia.webapp.jsf3.entities.Producto;
import org.ngarcia.webapp.jsf3.repositories.CrudRepository;

import java.util.List;
import java.util.Optional;

@Stateless
public class ProductoServiceImpl implements ProductoService {

   @Inject
   private CrudRepository<Producto> repository;

   @Override
   public List<Producto> listar() {
      return repository.listar();
   }

   @Override
   public Optional<Producto> porId(Long id) {
      try {
         return Optional.of(repository.porId(id));
      } catch (Exception e) {
         return Optional.empty();
      }
   }
}
