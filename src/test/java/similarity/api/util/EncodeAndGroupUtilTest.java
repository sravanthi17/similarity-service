package similarity.api.util;

import org.apache.commons.codec.language.DoubleMetaphone;
import org.junit.Test;
import similarity.api.model.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class EncodeAndGroupUtilTest {

    EncodeAndGroupUtil encodeAndGroupUtil = new EncodeAndGroupUtil();
    DoubleMetaphone dm = new DoubleMetaphone();


    @Test
    public void shouldEncodeAndInsertNewEntryIfKeyDoesNotExist() {
        ArrayList<Data> dataArrayList = new ArrayList<>();
        Data record1 = new Data();
        record1.setEmail("anc@gmail.com");
        dataArrayList.add(record1);
        Map<String, List<Integer>> groupedEncodedData = encodeAndGroupUtil.getGroupedEncodedData(dataArrayList);
        assertThat(groupedEncodedData.size(), is(1));
        assertThat(groupedEncodedData.get("email#"+dm.doubleMetaphone(record1.getEmail())).size(), is(1));
        assertThat(groupedEncodedData.get("email#"+dm.doubleMetaphone(record1.getEmail())).get(0), is(0));
    }


    @Test
    public void shouldEncodeAndAddAnEntryIfKeyAlreadyExist() {
        ArrayList<Data> dataArrayList = new ArrayList<>();
        Data record1 = new Data();
        record1.setEmail("anc@gmail.com");
        dataArrayList.add(record1);

        Data record2 = new Data();
        dataArrayList.add(record2);

        Data record3 = new Data();
        record3.setEmail(" ank@gmail.com ");
        dataArrayList.add(record3);
        Map<String, List<Integer>> groupedEncodedData = encodeAndGroupUtil.getGroupedEncodedData(dataArrayList);
        assertThat(groupedEncodedData.size(), is(1));
        assertThat(groupedEncodedData.get("email#"+dm.doubleMetaphone(record1.getEmail())).size(), is(2));
        assertThat(groupedEncodedData.get("email#"+dm.doubleMetaphone(record1.getEmail())).get(0), is(0));
        assertThat(groupedEncodedData.get("email#"+dm.doubleMetaphone(record1.getEmail())).get(1), is(2));
    }

    @Test
    public void shouldIgnoreKeyIfNull() {
        ArrayList<Data> dataArrayList = new ArrayList<>();
        Data record1 = new Data();
        dataArrayList.add(record1);

        Map<String, List<Integer>> groupedEncodedData = encodeAndGroupUtil.getGroupedEncodedData(dataArrayList);
        assertThat(groupedEncodedData.size(), is(0));
    }


    @Test
    public void shouldIgnoreKeyIfEmpty() {
        ArrayList<Data> dataArrayList = new ArrayList<>();
        Data record1 = new Data();
        record1.setEmail("");
        dataArrayList.add(record1);

        Map<String, List<Integer>> groupedEncodedData = encodeAndGroupUtil.getGroupedEncodedData(dataArrayList);
        assertThat(groupedEncodedData.size(), is(0));
    }

    @Test
    public void shouldIgnoreKeyWithEmptyWhiteSpaces() {
        ArrayList<Data> dataArrayList = new ArrayList<>();
        Data record1 = new Data();
        record1.setEmail(" ");
        dataArrayList.add(record1);

        Map<String, List<Integer>> groupedEncodedData = encodeAndGroupUtil.getGroupedEncodedData(dataArrayList);
        assertThat(groupedEncodedData.size(), is(0));
    }

}