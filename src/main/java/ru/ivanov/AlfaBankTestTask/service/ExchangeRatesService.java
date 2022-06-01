package ru.ivanov.AlfaBankTestTask.service;

import java.util.List;

public interface ExchangeRatesService {

    int getKey(String isoCode);

    void update();

    List<String> getIsoCodes();
}
