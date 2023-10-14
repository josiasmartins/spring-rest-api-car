package com.techbuzzblogs.rest.producer.models.DTOs;

import com.techbuzzblogs.rest.producer.models.Car;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
public class CarDTO {

    private String carName;
    private String carModel;
    private String company;

    public CarDTO(Car car) {
        carName = car.getCarName();
        carModel = car.getCarModel();
        company = car.getCompany();
    }


}
