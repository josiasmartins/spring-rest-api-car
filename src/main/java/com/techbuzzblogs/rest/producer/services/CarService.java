package com.techbuzzblogs.rest.producer.services;

import com.techbuzzblogs.rest.producer.models.Car;
import com.techbuzzblogs.rest.producer.models.DTOs.CarDTO;
import com.techbuzzblogs.rest.producer.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    @Transactional(readOnly = true)
    public CarDTO findById(Long id) {
        Car entity = carRepository.findById(id).get();
        return new CarDTO(entity);
    }

    @Transactional
    public Car create(CarDTO dto) {
        Car car = new Car(dto);
        carRepository.save(car);

        return car;
    }

}
