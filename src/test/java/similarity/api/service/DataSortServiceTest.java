package similarity.api.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import similarity.api.model.Data;
import similarity.api.model.DataList;
import similarity.api.model.SortedData;
import similarity.api.util.EncodeAndGroupUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static org.hamcrest.CoreMatchers.any;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DataSortServiceTest {

    @InjectMocks
    DataSortService dataSortService;

    @Mock
    EncodeAndGroupUtil encodeAndGroupUtil;


    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(encodeAndGroupUtil, "unique", new String[]{"email", "id"});
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

        HashMap<String, List<Integer>> duplicateEntries = new HashMap<>();
        ArrayList<Integer> value = new ArrayList<>();
        value.add(0);
        value.add(2);
        duplicateEntries.put("anc@gmail.com", value);


        when(encodeAndGroupUtil.getGroupedEncodedData(data.getDataList())).thenReturn(duplicateEntries);
        SortedData sortedData= dataSortService.sortData(data);
        assertThat(sortedData.getDuplicates().size(), is(2));
        assertThat(sortedData.getDuplicates().get(0).getEmail(), is("anc@gmail.com"));
        assertThat(sortedData.getDuplicates().get(1).getEmail(), is(" ank@gmail.com "));
        assertThat(sortedData.getNonDuplicates().size(), is(2));
        assertNull(sortedData.getNonDuplicates().get(0).getEmail());
        assertThat(sortedData.getNonDuplicates().get(1).getEmail(), is(""));
    }

}