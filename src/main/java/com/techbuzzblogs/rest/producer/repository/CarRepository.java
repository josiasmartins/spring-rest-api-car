package com.techbuzzblogs.rest.producer.repository;

import com.techbuzzblogs.rest.producer.models.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Long, Car> {
}
