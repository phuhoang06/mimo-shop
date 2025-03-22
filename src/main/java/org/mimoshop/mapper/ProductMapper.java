package org.mimoshop.mapper;

import org.mapstruct.*;
import org.mimoshop.model.Product;
import org.mimoshop.model.Tag;
import org.mimoshop.payload.request.CreateProductRequest;
import org.mimoshop.payload.request.UpdateProductRequest;
import org.mimoshop.payload.response.ProductResponse;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy =  NullValuePropertyMappingStrategy.IGNORE)
public interface ProductMapper {

    @Mapping(source = "category.name", target = "categoryName") // Thêm mapping này
    @Mapping(source = "tags", target = "tagNames", qualifiedByName = "tagListToStringList") // Thêm mapping này
    ProductResponse toResponse(Product product);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "tags", ignore = true)
    Product toEntity(CreateProductRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "tags", ignore = true)
    void updateEntityFromDto(UpdateProductRequest dto, @MappingTarget Product entity);

    Product toEntity(UpdateProductRequest request);


    // Phương thức hỗ trợ để map List<Tag> sang List<String>
    @Named("tagListToStringList")
    default List<String> tagListToStringList(List<Tag> tags) {
        if (tags == null) {
            return null;
        }
        return tags.stream().map(Tag::getName).collect(Collectors.toList());
    }
}