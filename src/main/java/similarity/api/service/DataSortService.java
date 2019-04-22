package similarity.api.service;

import org.springframework.stereotype.Service;
import similarity.api.model.Data;
import similarity.api.model.DataList;

import java.util.ArrayList;
import java.util.List;

@Service
public class DataSortService {
    public List<Data> sortData(DataList data) {
        if(data == null || data.getDataList() == null ||  data.getDataList().isEmpty() ) {
            return new ArrayList<>();
        }else {
            return data.getDataList();
        }
    }
}
