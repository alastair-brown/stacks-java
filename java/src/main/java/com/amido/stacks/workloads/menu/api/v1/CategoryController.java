package com.amido.stacks.workloads.menu.api.v1;

import static org.springframework.http.HttpStatus.OK;

import com.amido.stacks.core.api.annotations.CreateAPIResponses;
import com.amido.stacks.core.api.annotations.DeleteAPIResponses;
import com.amido.stacks.core.api.annotations.UpdateAPIResponses;
import com.amido.stacks.core.api.dto.response.ResourceCreatedResponse;
import com.amido.stacks.core.api.dto.response.ResourceUpdatedResponse;
import com.amido.stacks.workloads.menu.api.v1.dto.request.CreateCategoryRequest;
import com.amido.stacks.workloads.menu.api.v1.dto.request.UpdateCategoryRequest;
import com.amido.stacks.workloads.menu.cqrs.CreateCategoryHandler;
import com.amido.stacks.workloads.menu.cqrs.DeleteCategoryHandler;
import com.amido.stacks.workloads.menu.cqrs.UpdateCategoryHandler;
import com.amido.stacks.workloads.menu.cqrs.commands.DeleteCategoryCommand;
import com.amido.stacks.workloads.menu.cqrs.mappers.RequestToCommandMapper;
import com.amido.stacks.workloads.menu.crud.service.v1.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(
    path = "/v1/menu/{id}/category",
    produces = MediaType.APPLICATION_JSON_VALUE + "; charset=utf-8")
@RestController
@RequiredArgsConstructor
public class CategoryController {
  #if USE_CQRS
  // CQRS
  private final CreateCategoryHandler createCategoryHandler;

  private final UpdateCategoryHandler updateCategoryHandler;

  private final DeleteCategoryHandler deleteCategoryHandler;

  private final RequestToCommandMapper requestToCommandMapper;
  #else
  // CRUD
  private final CategoryService categoryService;
  #endif

  @PostMapping
  @Operation(
      tags = "Category",
      summary = "Create a category in the menu",
      description = "Adds a category to menu",
      operationId = "AddMenuCategory")
  @CreateAPIResponses
  ResponseEntity<ResourceCreatedResponse> createCategory(
      @Parameter(description = "Menu id", required = true) @PathVariable("id") UUID menuId,
      @Valid @RequestBody CreateCategoryRequest body,
      @Parameter(hidden = true) @RequestAttribute("CorrelationId") String correlationId) {
    #if USE_CQRS
    return new ResponseEntity<>(
            new ResourceCreatedResponse(
                    createCategoryHandler
                            .handle(requestToCommandMapper.map(correlationId, menuId, body))
                            .orElseThrow()),
            HttpStatus.CREATED);
    #else
    return new ResponseEntity<>(categoryService.create(body, correlationId), HttpStatus.CREATED);
    #endif
  }

  @PutMapping("/{categoryId}")
  @Operation(
      tags = "Category",
      summary = "Update a category in the menu",
      description = "Update a category to menu",
      operationId = "UpdateMenuCategory")
  @UpdateAPIResponses
  ResponseEntity<ResourceUpdatedResponse> updateCategory(
      @Parameter(description = "Menu id", required = true) @PathVariable("id") UUID menuId,
      @Parameter(description = "Category id", required = true) @PathVariable("categoryId")
          UUID categoryId,
      @Valid @RequestBody UpdateCategoryRequest body,
      @Parameter(hidden = true) @RequestAttribute("CorrelationId") String correlationId) {
    #if USE_CQRS
    return new ResponseEntity<>(
            new ResourceUpdatedResponse(
                    updateCategoryHandler
                            .handle(requestToCommandMapper.map(correlationId, menuId, categoryId, body))
                            .orElseThrow()),
            OK);
    #else
    return new ResponseEntity<>(
        categoryService.update(menuId, categoryId, body, correlationId), OK);
    #endif
  }

  @DeleteMapping("/{categoryId}")
  @Operation(
      tags = "Category",
      summary = "Removes a category and its items from menu",
      description = "Removes a category and its items from menu",
      operationId = "DeleteCategory")
  @DeleteAPIResponses
  ResponseEntity<Void> deleteCategory(
      @Parameter(description = "Menu id", required = true) @PathVariable("id") UUID menuId,
      @Parameter(description = "Category id", required = true) @PathVariable("categoryId")
          UUID categoryId,
      @Parameter(hidden = true) @RequestAttribute("CorrelationId") String correlationId) {
    #if USE_CQRS
    deleteCategoryHandler.handle(new DeleteCategoryCommand(correlationId, menuId, categoryId));
    #endif
    return new ResponseEntity<>(OK);
  }
}
