package ru.ivanov.AlfaBankTestTask.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.ivanov.AlfaBankTestTask.service.ExchangeRatesServiceImpl;
import ru.ivanov.AlfaBankTestTask.service.GiphyServiceImpl;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class MainController {
    private final ExchangeRatesServiceImpl exchangeRatesService;
    private final GiphyServiceImpl giphyService;
    @Value("${giphy.rich}")
    private String rich;
    @Value("${giphy.broke}")
    private String broke;

    public MainController(ExchangeRatesServiceImpl exchangeRatesService,
                          GiphyServiceImpl giphyService) {
        this.exchangeRatesService = exchangeRatesService;
        this.giphyService = giphyService;
    }

    @GetMapping("/get-codes")
    public List<String> getIsoCodes() {
        return exchangeRatesService.getIsoCodes();
    }

    @GetMapping("/get-gif/{code}")
    public ResponseEntity<Map> getRates(@PathVariable String code) {
        if (code == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        int gifKey = exchangeRatesService.getKey(code);
        String gifTag = null;
        if (gifKey == -1) {
            gifTag = this.broke;
        } else if (gifKey == 1) {
            gifTag = this.rich;
        }
        return giphyService.getGif(gifTag);
    }
}
