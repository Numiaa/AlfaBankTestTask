package ru.ivanov.AlfaBankTestTask.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "GiphyClient", url = "${giphy.url.general}")
public interface FeignGiphyClient {

    //request's to external API -> Giphy.com
    @GetMapping("/random")
    ResponseEntity<Map> getGif(@RequestParam("api_key") String key,
                               @RequestParam("tag") String tag);
}
