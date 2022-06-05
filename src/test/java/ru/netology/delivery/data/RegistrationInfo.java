package ru.netology.delivery.data;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
class RegistrationInfo {

    private final String city;
    private final String date;
    private final String name;
    private final String phone;

}


