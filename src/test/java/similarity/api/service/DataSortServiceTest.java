package similarity.api.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import similarity.api.model.Data;
import similarity.api.model.DataList;
import similarity.api.model.SortedData;
import similarity.api.util.EncodeAndGroupUtil;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
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

        @Bean
        public EncodeAndGroupUtil encodeAndGroupUtil() {
            return new EncodeAndGroupUtil();
        }
    }

    @Test
    public void shouldReturnEmptyListWhenSortTheEmptyListOfData(){
        DataList data1 = new DataList();
        data1.setDataList(new ArrayList<>());
        SortedData data = dataSortService.sortData(data1);
        assertTrue(data.getDuplicates().isEmpty());
        assertTrue(data.getNonDuplicates().isEmpty());
    }

    @Test
    public void shouldReturnEmptyListWhenDataIsNull(){
        SortedData data = dataSortService.sortData(null);
        assertTrue(data.getDuplicates().isEmpty());
        assertTrue(data.getNonDuplicates().isEmpty());
    }

    @Test
    public void shouldReturnEmptyListWhenDataListIsNull(){
        SortedData data = dataSortService.sortData(new DataList());
        assertTrue(data.getDuplicates().isEmpty());
        assertTrue(data.getNonDuplicates().isEmpty());
    }

    @Test
    public void shouldSortTheDataWithDuplicatesAtFirst(){
        DataList data = new DataList();
        ArrayList<Data> dataArrayList = new ArrayList<>();
        Data record1 = new Data();
        record1.setEmail("anc@gmail.com");
        dataArrayList.add(record1);

        Data record2 = new Data();
        dataArrayList.add(record2);

        Data record3 = new Data();
        record3.setEmail(" ank@gmail.com ");
        dataArrayList.add(record3);

        Data record4 = new Data();
        record4.setEmail("");
        dataArrayList.add(record4);


        data.setDataList(dataArrayList);
        SortedData sortedData= dataSortService.sortData(data);
        assertThat(sortedData.getDuplicates().size(), is(2));
        assertThat(sortedData.getDuplicates().get(0).getEmail(), is("anc@gmail.com"));
        assertThat(sortedData.getDuplicates().get(1).getEmail(), is(" ank@gmail.com "));
        assertThat(sortedData.getNonDuplicates().size(), is(2));
        assertNull(sortedData.getNonDuplicates().get(0).getEmail());
        assertThat(sortedData.getNonDuplicates().get(1).getEmail(), is(""));
    }

}