package com.ninjaone.backendinterviewproject.database;

import com.ninjaone.backendinterviewproject.model.SampleEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SampleRepository extends CrudRepository<SampleEntity, String> {}
