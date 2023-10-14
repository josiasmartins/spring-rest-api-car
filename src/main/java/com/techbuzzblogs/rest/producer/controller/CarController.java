package com.techbuzzblogs.rest.producer.controller;

import com.techbuzzblogs.rest.producer.models.Car;

import java.util.ArrayList;
import java.util.List;

import com.techbuzzblogs.rest.producer.models.DTOs.CarDTO;
import com.techbuzzblogs.rest.producer.services.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/car")
public class CarController {

    @Autowired
    private CarService carService;

    public List<Car> listCar = new ArrayList<>();

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CarDTO getDetails(@PathVariable("id") Long id) {
            return this.carService.findById(id);
//        return listCar.get(id);
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Car createCar(@RequestBody CarDTO body) {
//        listCar.add(body);
//        System.out.println(listCar + "BODY");
        return this.carService.create(body);
//        return body;
    }

}
