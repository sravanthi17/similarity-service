package similarity.api.util;

import org.apache.commons.codec.language.DoubleMetaphone;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import similarity.api.model.Data;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

@Component
public class EncodeAndGroupUtil {

    DoubleMetaphone dm = new DoubleMetaphone();

    @Value("${similarity.unique}")
    private String[] unique;

    public Map<String, List<Integer>> getGroupedEncodedData(List<Data> dataList) {
        Map<String, List<Integer>> duplicateEntries = new HashMap<>();

        dataList.stream().forEach((eachRecord) -> {
            Arrays.asList(unique).stream().forEach(uniqueKey -> {
            String value = callGetter(eachRecord, uniqueKey);
            if (value != null && !value.trim().isEmpty()) {
                value = value.trim();
                String encodedValue = dm.doubleMetaphone(value).isEmpty() ? value : dm.doubleMetaphone(value);
                ArrayList<Integer> duplicates = new ArrayList<>();
                String key = uniqueKey + "#" + encodedValue;
                if (!duplicateEntries.containsKey(key)) {
                    duplicateEntries.put(key, new ArrayList<>());
                }
                else {
                    duplicates.addAll(duplicateEntries.get(key));
                }
                duplicates.add(dataList.indexOf(eachRecord));
                duplicateEntries.put(key, duplicates);
            }
            });
        });
        return duplicateEntries;
    }

    private String callGetter(Object obj, String fieldName){
        PropertyDescriptor pd;
        try {
            pd = new PropertyDescriptor(fieldName, obj.getClass());
            String invoke = (String) pd.getReadMethod().invoke(obj);
            return invoke;
        } catch (IntrospectionException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
            return "";
        }
    }
}
