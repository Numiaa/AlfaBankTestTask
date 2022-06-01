package ru.ivanov.AlfaBankTestTask.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.ivanov.AlfaBankTestTask.client.FeignExchangeRatesClient;
import ru.ivanov.AlfaBankTestTask.model.ExchangeRate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ExchangeRatesServiceImpl implements ExchangeRatesService {

    private ExchangeRate previousRate;
    private ExchangeRate currentRate;
    private final FeignExchangeRatesClient feignExchangeRatesClient;
    private final SimpleDateFormat dateFormat;
    private final SimpleDateFormat timeFormat;

    @Value("${openexchangerates.app.id}")
    private String id;
    @Value("${openexchangerates.base}")
    private String base;

    @Autowired
    public ExchangeRatesServiceImpl(FeignExchangeRatesClient feignExchangeRatesClient,
                                    @Qualifier("date_bean") SimpleDateFormat date,
                                    @Qualifier("time_bean") SimpleDateFormat time) {
        this.feignExchangeRatesClient = feignExchangeRatesClient;
        this.dateFormat = date;
        this.timeFormat = time;
    }

    //getting profit or loss token
    @Override
    public int getKey(String isoCode) {
        if (isoCode == null) {
            throw new NoSuchElementException();
        }
        this.update();
        Double prevPrice = this.getPrice(this.previousRate, isoCode);
        Double currPrice = this.getPrice(this.currentRate, isoCode);
        return Double.compare(prevPrice, currPrice);
    }

    //updating current time and exchange rates
    @Override
    public void update() {
        Long currTime = System.currentTimeMillis();
        this.updateCurrentRate(currTime);
        this.updatePreviousRate(currTime);
    }

    //getting list ISO codes from OpenExchangeRates
    @Override
    public List<String> getIsoCodes() {
        return new ArrayList<>(this.currentRate.getRates().keySet());
    }

    // updating exchange rates for today
    public void updateCurrentRate(Long time) {
        if (this.currentRate == null || !timeFormat.format(this.currentRate.getTimestamp() * 1000)
                .equals(timeFormat.format(time))) {
            this.currentRate = feignExchangeRatesClient.getLatestRate(id);
        }
    }

    // updating exchange rates for yesterday
    public void updatePreviousRate(Long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        String currentDate = dateFormat.format(calendar.getTime());
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        String previousDate = dateFormat.format(calendar.getTime());

        // update rate 1 hour from openexchangerates
        if (this.previousRate == null || !dateFormat.format(this.previousRate.getTimestamp() * 1000)
                .equals(previousDate)
                && !dateFormat.format(this.previousRate.getTimestamp() * 1000)
                .equals(currentDate)) {
            this.previousRate = feignExchangeRatesClient.getHistoryRate(previousDate, id);
        }
    }

    // getting price from rates
    public Double getPrice(ExchangeRate rate, String code) {
        if (rate == null || code == null) {
            throw new NoSuchElementException();
        }
        Map<String, Double> map = rate.getRates();
        Double targetRate = map.get(code);
        Double appBaseRate = map.get(this.base);
        Double defaultBaseRate = map.get(rate.getBase());
        return new BigDecimal(
                (defaultBaseRate / appBaseRate) * targetRate)
                .setScale(4, RoundingMode.UP)
                .doubleValue();
    }
}

