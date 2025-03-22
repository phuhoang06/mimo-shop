package org.mimoshop.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mimoshop.model.Category;
import org.mimoshop.payload.request.CreateCategoryRequest;
import org.mimoshop.payload.request.UpdateCategoryRequest;
import org.mimoshop.payload.response.CategoryResponse;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CategoryMapper {

    CategoryResponse toResponse(Category category);

    @Mapping(target = "id", ignore = true) // Bỏ qua id
    @Mapping(target = "products", ignore = true)  // Không map products
    Category toEntity(CreateCategoryRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "products", ignore = true)
    void updateEntityFromDto(UpdateCategoryRequest dto, @MappingTarget Category entity);
    Category toEntity(UpdateCategoryRequest request);
}