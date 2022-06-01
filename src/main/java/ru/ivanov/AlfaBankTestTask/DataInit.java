package ru.ivanov.AlfaBankTestTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.ivanov.AlfaBankTestTask.service.ExchangeRatesService;

import javax.annotation.PostConstruct;

@Component
public class DataInit {

    private final ExchangeRatesService exchangeRatesService;

    @Autowired
    public DataInit(ExchangeRatesService exchangeRatesService) {
        this.exchangeRatesService = exchangeRatesService;
    }

    @PostConstruct
    public void firstDataInit() {
        exchangeRatesService.update();
    }
}
