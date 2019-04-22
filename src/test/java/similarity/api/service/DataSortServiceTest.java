package similarity.api.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import similarity.api.model.Data;
import similarity.api.model.DataList;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class DataSortServiceTest {

    @Autowired
    DataSortService dataSortService;

    @TestConfiguration
    static class DataSortServiceTestContextConfiguration {

        @Bean
        public DataSortService dataSortService() {
            return new DataSortService();
        }
    }

    @Test
    public void shouldReturnEmptyListWhenSortTheEmptyListOfData(){
        DataList data1 = new DataList();
        data1.setDataList(new ArrayList<>());
        List<Data> data = dataSortService.sortData(data1);
        assertTrue(data.isEmpty());
    }

    @Test
    public void shouldReturnEmptyListWhenDataIsNull(){
        List<Data> data = dataSortService.sortData(null);
        assertTrue(data.isEmpty());
    }

    @Test
    public void shouldReturnEmptyListWhenDataListIsNull(){
        List<Data> data = dataSortService.sortData(new DataList());
        assertTrue(data.isEmpty());
    }
}