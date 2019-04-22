package similarity.api.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import similarity.api.model.Data;
import similarity.api.model.SortedData;
import similarity.api.service.DataSortService;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;


@RunWith(SpringRunner.class)
@WebMvcTest(value = DataController.class, secure = false)
public class DataControllerTest {



    @MockBean
    DataSortService dataSortService;

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void endpointSortShouldReturnSortedList() throws Exception {

        Data data = new Data();
        data.setId("sampleId");
        data.setFirstName("Sample Name");
        SortedData sortedData = new SortedData();
        sortedData.setNonDuplicates(Collections.singletonList(data));
        Mockito.when(dataSortService.sortData(any())).thenReturn(sortedData);

        String exampleData = "{\n" +
                " \"dataList\": [{\n" +
                " \t\"id\": \"sample\"\n" +
                " }]\n" +
                "}";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(
                "/similarity/sort").content(exampleData).contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        System.out.println(result.getResponse());
        String expected = "{\n" +
                "  \"nonDuplicates\":\n" +
                "  [\n" +
                "    {\n" +
                "        \"id\": \"sample\",\n" +
                "        \"firstName\": null,\n" +
                "        \"lastName\": null,\n" +
                "        \"company\": null,\n" +
                "        \"email\": null,\n" +
                "        \"address1\": null,\n" +
                "        \"address2\": null,\n" +
                "        \"zip\": null,\n" +
                "        \"city\": null,\n" +
                "        \"state\": null,\n" +
                "        \"state_long\": null,\n" +
                "        \"phone\": null\n" +
                "    }\n" +
                "]\n" +
                "}";

        JSONAssert.assertEquals(expected, result.getResponse()
                .getContentAsString(), false);
    }
}