package com.techbuzzblogs.rest.producer.models;


import com.techbuzzblogs.rest.producer.models.DTOs.CarDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@Entity
@Table(name = "tb_car")
@AllArgsConstructor
@NoArgsConstructor
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String carName;
    private String carModel;
    private String company;

    public Car(CarDTO car) {
        carName = car.getCarName();
        carModel = car.getCarModel();
        company = car.getCompany();
    }

}
