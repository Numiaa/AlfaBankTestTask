package ru.ivanov.AlfaBankTestTask.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.ivanov.AlfaBankTestTask.client.FeignGiphyClient;

import java.util.Map;

@Service
public class GiphyServiceImpl implements GiphyService {

    private final FeignGiphyClient giphyClient;

    @Value("${giphy.api.key}")
    private String apiKey;

    @Autowired
    public GiphyServiceImpl(FeignGiphyClient giphyClient) {
        this.giphyClient = giphyClient;
    }

    //getting gif
    @Override
    public ResponseEntity<Map> getGif(String tag) {
        ResponseEntity<Map> result = giphyClient.getGif(this.apiKey, tag);
        result.getBody().put("result", tag);
        return result;
    }
}
