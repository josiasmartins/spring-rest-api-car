package com.techbuzzblogs.rest.producer.controller;

import com.techbuzzblogs.rest.producer.annotations.VerifyRoles;
import com.techbuzzblogs.rest.producer.enums.UserRole;
import com.techbuzzblogs.rest.producer.models.Car;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.techbuzzblogs.rest.producer.models.DTOs.CarDTO;
import com.techbuzzblogs.rest.producer.repository.CarRepository;
import com.techbuzzblogs.rest.producer.services.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

        if (carRepository.count() == 0) { // previne duplicação ao reiniciar
            carRepository.save(new Car(new CarDTO("A", "Z", "Company1")));
            carRepository.save(new Car(new CarDTO("A", "Y", "Company2")));
            carRepository.save(new Car(new CarDTO("A", "X", "Company3")));
            carRepository.save(new Car(new CarDTO("B", "K", "Company4")));
        }

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

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<Car> listCarsDynamicSorting(
            @RequestParam Map<String, String> sortParams,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        this.seedMockData();

        List<Sort.Order> orders = sortParams.entrySet().stream()
                .filter(e -> isValidField(Car.class, e.getKey()))
                .map(e -> "desc".equalsIgnoreCase(e.getValue())
                        ? Sort.Order.desc(e.getKey())
                        : Sort.Order.asc(e.getKey()))
                .collect(Collectors.toList());

        Pageable pageable = orders.isEmpty()
                ? PageRequest.of(page, size)
                : PageRequest.of(page, size, Sort.by(orders));

        return carRepository.findAll(pageable);
    }

    private boolean isValidField(Class<?> clazz, String fieldName) {
        return Arrays.stream(clazz.getDeclaredFields())
                .anyMatch(f -> f.getName().equals(fieldName));
    }


//    @GetMapping
//    @ResponseStatus(HttpStatus.OK)
//    public List<Car> listCarsDynamicSorting(
//            @RequestParam Map<String, String> sortParams,
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size
//    ) {
//        // Optional: só para testes
//        this.seedMockData();
//
//        List<Sort.Order> orders = new ArrayList<>();
//
//        // Campos válidos da entidade
//        Set<String> validFields = Arrays.stream(Car.class.getDeclaredFields())
//                .map(Field::getName)
//                .collect(Collectors.toSet());
//
//        // MANTÉM a ordem de inserção dos parâmetros
//        Map<String, String> orderedParams = new LinkedHashMap<>(sortParams);
//
//        for (Map.Entry<String, String> entry : orderedParams.entrySet()) {
//            String field = entry.getKey();
//            String direction = entry.getValue();
//
//            if (validFields.contains(field)) {
//                if ("ASC".equalsIgnoreCase(direction)) {
//                    orders.add(Sort.Order.asc(field));
//                } else if ("DESC".equalsIgnoreCase(direction)) {
//                    orders.add(Sort.Order.desc(field));
//                }
//            }
//        }
//
//        Pageable pageable = orders.isEmpty()
//                ? PageRequest.of(page, size)
//                : PageRequest.of(page, size, Sort.by(orders));
//
//        return carRepository.findAll(pageable).get().collect(Collectors.toList());
//    }


}
