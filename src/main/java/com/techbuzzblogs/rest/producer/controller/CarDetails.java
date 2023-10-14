package com.techbuzzblogs.rest.producer.controller;

import com.techbuzzblogs.rest.producer.bean.Car;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/car")
public class CarDetails {

    public List<Car> listCar = new ArrayList<>();

    @GetMapping("/id")
    public Car getDetails(@PathVariable("id") int id) {
        return listCar.get(id);
    }

    @PostMapping("/create")
    public Car createCar(@RequestBody Car body) {
        listCar.add(body);
        System.out.println(listCar + "BODY");
        return body;
    }

}
