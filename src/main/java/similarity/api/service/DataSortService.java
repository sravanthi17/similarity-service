package similarity.api.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import similarity.api.model.Data;
import similarity.api.model.DataList;
import similarity.api.model.SortedData;
import similarity.api.util.EncodeAndGroupUtil;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class DataSortService {


    @Autowired
    EncodeAndGroupUtil encodeAndGroupUtil;

    private String EVENT_TYPE = "SERVICE";

    Logger logger = LogManager.getLogger(DataSortService.class);

    public SortedData sortData(DataList data) {
        logger.info("EventType: {} SORT_DATA_REQUEST Datalist: {}", EVENT_TYPE, data);
        SortedData sortedData = new SortedData();
        if(data == null || data.getDataList() == null ||  data.getDataList().isEmpty() ) {
            logger.info("EventType: {} SORT_DATA_EMPTY_DATA", EVENT_TYPE);
            return sortedData;
        }else {
            List<Data> dataList = data.getDataList();
            Map<String, List<Integer>> duplicateEntries = encodeAndGroupUtil.getGroupedEncodedData(dataList);
            Set<Integer> duplicateIndices = duplicateEntries.entrySet().stream().filter(entry -> entry.getValue().size() > 1)
                    .flatMap(entry -> entry.getValue().stream())
                    .collect(Collectors.toSet());
            List<Data> duplicateData = duplicateIndices
                    .stream()
                    .map(index -> dataList.get(index)).collect(Collectors.toList());
            List<Data> nonDuplicates = IntStream.range(0, dataList.size())
                    .filter(index -> !duplicateIndices.contains(index))
                    .mapToObj(index -> dataList.get(index))
                    .collect(Collectors.toList());

            sortedData.setDuplicates(duplicateData);
            sortedData.setNonDuplicates(nonDuplicates);
            logger.info("EventType: {} SORT_DATA_SUCCESS SortedData: {}", EVENT_TYPE, sortedData);
            return sortedData;
        }
    }


}
