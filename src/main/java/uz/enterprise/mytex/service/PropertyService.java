package uz.enterprise.mytex.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.enterprise.mytex.entity.Property;
import uz.enterprise.mytex.repository.PropertyRepository;

@Service
@RequiredArgsConstructor
public class PropertyService {
    private final PropertyRepository propertyRepository;

    public String getValue(final String key){
        return propertyRepository.findByKey(key)
                .map(Property::getValue)
                .orElse("");
    }
}
