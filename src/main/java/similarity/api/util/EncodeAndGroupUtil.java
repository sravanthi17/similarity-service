package similarity.api.util;

import org.apache.commons.codec.language.DoubleMetaphone;
import org.springframework.stereotype.Component;
import similarity.api.model.Data;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class EncodeAndGroupUtil {

    DoubleMetaphone dm = new DoubleMetaphone();

    public Map<String, List<Integer>> getGroupedEncodedData(List<Data> dataList) {
        Map<String, List<Integer>> duplicateEntries = new HashMap<>();

        dataList.stream().forEach((eachRecord) -> {
            String email = "email";
            String value = callGetter(eachRecord, email);
            if (value != null && !value.trim().isEmpty()) {
                value = value.trim();
                String encodedValue = dm.doubleMetaphone(value);
                ArrayList<Integer> duplicates = new ArrayList<>();
                String key = email + "#" + encodedValue;
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
