package ru.ivanov.AlfaBankTestTask;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.ivanov.AlfaBankTestTask.controller.MainController;
import ru.ivanov.AlfaBankTestTask.service.ExchangeRatesServiceImpl;
import ru.ivanov.AlfaBankTestTask.service.GiphyServiceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@ExtendWith(SpringExtension.class)
@WebMvcTest(MainController.class)
public class MainControllerTest {

    @Value("${giphy.rich}")
    private String rich;
    @Value("${giphy.broke}")
    private String broke;

    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ExchangeRatesServiceImpl exchangeRatesService;
    @MockBean
    private GiphyServiceImpl giphyService;


    @Test
    public void whenReturnListOfCodes() throws Exception {
        List<String> list = new ArrayList<>();
        list.add("Test");
        Mockito.when(exchangeRatesService.getIsoCodes())
                .thenReturn(list);
        mockMvc.perform(get("/api/get-codes")
                        .content(mapper.writeValueAsString(null))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$[0]").value("Test"));
    }

    @Test
    public void whenListIsNull() throws Exception {
        Mockito.when(exchangeRatesService.getIsoCodes())
                .thenReturn(null);
        mockMvc.perform(get("/api/get-codes")
                        .content(mapper.writeValueAsString(null))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$[0]").doesNotExist());
    }

    @Test
    public void whenReturnRich() throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("result", this.rich);
        ResponseEntity<Map> responseEntity = new ResponseEntity<>(map, HttpStatus.OK);
        Mockito.when(exchangeRatesService.getKey(anyString()))
                .thenReturn(1);
        Mockito.when(giphyService.getGif(this.rich))
                .thenReturn(responseEntity);
        mockMvc.perform(get("/api/get-gif/code")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.result").value(this.rich));
    }

    @Test
    public void whenReturnBroke() throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("result", this.broke);
        ResponseEntity<Map> responseEntity = new ResponseEntity<>(map, HttpStatus.OK);
        Mockito.when(exchangeRatesService.getKey(anyString()))
                .thenReturn(-1);
        Mockito.when(giphyService.getGif(this.broke))
                .thenReturn(responseEntity);
        mockMvc.perform(get("/api/get-gif/code")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.result").value(this.broke));
    }
}
