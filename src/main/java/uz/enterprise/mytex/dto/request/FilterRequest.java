package uz.enterprise.mytex.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.enterprise.mytex.enums.Operator;
import uz.enterprise.mytex.enums.PropertyType;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class FilterRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 6293344849078612450L;

    private String key;

    private Operator operator;

    private PropertyType propertyType;

    /**
     *  if operator is unary such as LIKE, EQUALS,
     *  single value must be used
     */
    private transient Object value;

    /**
     *   if operator is binary such as BETWEEN,
     *   valueTo along with value must be used
     */
    private transient Object valueTo;

    /**
     *  if operator is more than unary, such as IN,
     *  values must be used
     */
    private transient List<Object> values;

    public FilterRequest(String key, Operator operator, PropertyType propertyType, Object value){
        this.key = key;
        this.operator = operator;
        this.propertyType = propertyType;
        this.value = value;
    }
}
