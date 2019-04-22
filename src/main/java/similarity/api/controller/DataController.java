package similarity.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import similarity.api.model.Data;
import similarity.api.model.DataList;
import similarity.api.service.DataSortService;

import java.util.List;

@RestController
public class DataController {

    @Autowired
    DataSortService dataSortService;

    @RequestMapping(value = "/similarity/sort", method = RequestMethod.POST)
    public ResponseEntity<List<Data>> getSortedData(@RequestBody DataList dataList){
       return new ResponseEntity<>(dataSortService.sortData(dataList), HttpStatus.OK);
    }
}
