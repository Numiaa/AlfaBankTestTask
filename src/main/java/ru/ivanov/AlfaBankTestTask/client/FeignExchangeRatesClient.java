package ru.ivanov.AlfaBankTestTask.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ru.ivanov.AlfaBankTestTask.model.ExchangeRate;

@FeignClient(name = "ExchangeRatesClient", url = "${openexchangerates.url.general}")
public interface FeignExchangeRatesClient {

    //request's to external API -> OpenExchangeRates.org
    @GetMapping("/latest.json")
    ExchangeRate getLatestRate(@RequestParam("app_id") String id);

    @GetMapping("/historical/{date}.json")
    ExchangeRate getHistoryRate(@PathVariable String date,
                                @RequestParam("app_id") String id);
}
