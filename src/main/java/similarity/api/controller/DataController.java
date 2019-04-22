package similarity.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import similarity.api.model.DataList;
import similarity.api.model.SortedData;
import similarity.api.service.DataSortService;

@RestController
public class DataController {

    @Autowired
    DataSortService dataSortService;

    @RequestMapping(value = "/similarity/sort", method = RequestMethod.POST)
    public ResponseEntity<SortedData> getSortedData(@RequestBody DataList dataList){
       return new ResponseEntity<>(dataSortService.sortData(dataList), HttpStatus.OK);
    }
}
