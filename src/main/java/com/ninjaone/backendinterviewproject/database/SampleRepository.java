package com.ninjaone.backendinterviewproject.database;

import com.ninjaone.backendinterviewproject.model.Sample;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SampleRepository extends CrudRepository<Sample, String> {}
