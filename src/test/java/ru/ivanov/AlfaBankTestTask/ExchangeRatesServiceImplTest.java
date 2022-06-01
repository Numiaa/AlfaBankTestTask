package ru.ivanov.AlfaBankTestTask;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.ivanov.AlfaBankTestTask.client.FeignExchangeRatesClient;
import ru.ivanov.AlfaBankTestTask.model.ExchangeRate;
import ru.ivanov.AlfaBankTestTask.service.ExchangeRatesServiceImpl;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ComponentScan("ru.ivanov")
public class ExchangeRatesServiceImplTest {

    @Value("${openexchangerates.base}")
    private String base;
    @Autowired
    private ExchangeRatesServiceImpl exchangeRatesService;
    @MockBean
    private FeignExchangeRatesClient feignExchangeRatesClient;

    private ExchangeRate previousRate;
    private ExchangeRate currentRate;

    @BeforeEach
    public void init() {
        //current time
        Long time = 1569468234L;
        this.currentRate = new ExchangeRate();
        this.currentRate.setTimestamp(time);
        this.currentRate.setBase("test_base");
        Map<String, Double> currentRateMap = new HashMap<>();
        currentRateMap.put("test_1", 1.0);
        currentRateMap.put("test_2", 0.1);
        currentRateMap.put("test_base", 1.0);
        currentRateMap.put(this.base, 65.50);
        this.currentRate.setRates(currentRateMap);

        //previous time
        time = 1564463234L;
        this.previousRate = new ExchangeRate();
        this.previousRate.setTimestamp(time);
        this.previousRate.setBase("test_base");
        Map<String, Double> previousRateMap = new HashMap<>();
        previousRateMap.put("test_1", 0.1);
        previousRateMap.put("test_2", 1.0);
        previousRateMap.put("test_base", 1.0);
        previousRateMap.put(this.base, 65.50);
        this.previousRate.setRates(previousRateMap);
    }

    @Test
    public void whenProfit() {
        Mockito.when(feignExchangeRatesClient.getLatestRate(anyString()))
                .thenReturn(this.currentRate);
        Mockito.when(feignExchangeRatesClient.getHistoryRate(anyString(), anyString()))
                .thenReturn(this.previousRate);
        int result = exchangeRatesService.getKey("test_2");
        assertEquals(1, result);
    }

    @Test
    public void whenLoss() {
        Mockito.when(feignExchangeRatesClient.getLatestRate(anyString()))
                .thenReturn(this.currentRate);
        Mockito.when(feignExchangeRatesClient.getHistoryRate(anyString(), anyString()))
                .thenReturn(this.previousRate);
        int result = exchangeRatesService.getKey("test_1");
        assertEquals(-1, result);
    }
}
