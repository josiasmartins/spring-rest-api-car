package com.techbuzzblogs.rest.producer.controller;

import com.techbuzzblogs.rest.producer.annotations.VerifyRoles;
import com.techbuzzblogs.rest.producer.enums.UserRole;
import com.techbuzzblogs.rest.producer.models.Car;

import java.util.ArrayList;
import java.util.List;

import com.techbuzzblogs.rest.producer.models.DTOs.CarDTO;
import com.techbuzzblogs.rest.producer.repository.CarRepository;
import com.techbuzzblogs.rest.producer.services.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/car")
public class CarController {

    @Autowired
    private CarService carService;

    @Autowired
    private CarRepository carRepository;

    public List<Car> listCar = new ArrayList<>();

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CarDTO getDetails(@PathVariable("id") Long id) {
            return this.carService.findById(id);
//        return listCar.get(id);
    }

    public void seedMockData() {

        carRepository.save(new Car(new CarDTO("a carName", "b carModel", "c company")));
        carRepository.save(new Car(new CarDTO("c carName", "d carModel", "a company")));
        carRepository.save(new Car(new CarDTO("x carName", "z carModel", "b company")));
        carRepository.save(new Car(new CarDTO("m carName", "k carModel", "z company")));

    }

    @PostMapping("/create")
    @VerifyRoles({ UserRole.APPROVER })
    @ResponseStatus(HttpStatus.CREATED)
    public Car createCar(@RequestBody CarDTO body) {
//        listCar.add(body);
//        System.out.println(listCar + "BODY");
        return this.carService.create(body);
//        return body;
    }

}
