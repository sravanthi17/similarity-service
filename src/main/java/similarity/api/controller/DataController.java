package similarity.api.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import similarity.api.model.DataList;
import similarity.api.model.SortedData;
import similarity.api.service.DataSortService;

@RestController
public class DataController {

    @Autowired
    DataSortService dataSortService;

    private String EVENT_TYPE = "API";

    Logger logger = LogManager.getLogger(DataController.class);

    @RequestMapping(value = "/similarity/sort", method = RequestMethod.POST)
    public ResponseEntity<SortedData> sortData(@RequestBody DataList dataList){
        try{
            logger.info("EventType: {} SORT_DATA_REQUEST Datalist: {}", EVENT_TYPE, dataList);
            SortedData sortedData = dataSortService.sortData(dataList);
            logger.info("EventType: {} SORT_DATA_SUCCESS Sorted: {}", EVENT_TYPE, sortedData);
            return new ResponseEntity<>(sortedData, HttpStatus.OK);

        }catch (Exception e){
            logger.error("EventType: {} SORT_DATA_ERROR Datalist: {}, Message: {}", EVENT_TYPE, dataList, e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
