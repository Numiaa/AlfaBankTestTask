package ru.ivanov.AlfaBankTestTask;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.ivanov.AlfaBankTestTask.client.FeignGiphyClient;
import ru.ivanov.AlfaBankTestTask.service.GiphyServiceImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ComponentScan("ru.ivanov")
public class GiphyServiceImplTest {

    @Autowired
    private GiphyServiceImpl giphyService;
    @MockBean
    private FeignGiphyClient giphyClient;

    @Test
    public void whenProfitChange() {
        ResponseEntity<Map> responseEntity = new ResponseEntity<>(new HashMap(), HttpStatus.OK);
        Mockito.when(giphyClient.getGif(anyString(), anyString()))
                .thenReturn(responseEntity);
        ResponseEntity<Map> result = giphyService.getGif("test_gif");
        assertEquals("test_gif", Objects.requireNonNull(result.getBody()).get("result"));
    }
}
