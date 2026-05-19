package com.proyectosu.invernadero.inventory.infrastructure.persistence.repository;

import com.proyectosu.invernadero.inventory.infrastructure.persistence.document.ItemInventarioDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ItemInventarioMongoRepository extends MongoRepository<ItemInventarioDocument, String> {

    List<ItemInventarioDocument> findAllByOrderByNombreAsc();
}