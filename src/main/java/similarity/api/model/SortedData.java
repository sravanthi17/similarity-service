package similarity.api.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class SortedData {
    private List<Data> duplicates = new ArrayList<>();
    private List<Data> nonDuplicates = new ArrayList<>();
}
