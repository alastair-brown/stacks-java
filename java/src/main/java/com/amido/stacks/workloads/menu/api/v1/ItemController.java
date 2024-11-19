package com.amido.stacks.workloads.menu.api.v1;

import com.amido.stacks.core.api.annotations.CreateAPIResponses;
import com.amido.stacks.core.api.annotations.DeleteAPIResponses;
import com.amido.stacks.core.api.annotations.UpdateAPIResponses;
import com.amido.stacks.core.api.dto.response.ResourceCreatedResponse;
import com.amido.stacks.core.api.dto.response.ResourceUpdatedResponse;
import com.amido.stacks.workloads.menu.api.v1.dto.request.CreateItemRequest;
import com.amido.stacks.workloads.menu.api.v1.dto.request.UpdateItemRequest;
import com.amido.stacks.workloads.menu.cqrs.CreateItemHandler;
import com.amido.stacks.workloads.menu.cqrs.DeleteItemHandler;
import com.amido.stacks.workloads.menu.cqrs.UpdateItemHandler;
import com.amido.stacks.workloads.menu.cqrs.commands.DeleteItemCommand;
import com.amido.stacks.workloads.menu.cqrs.mappers.RequestToCommandMapper;
import com.amido.stacks.workloads.menu.crud.service.v1.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.http.HttpStatus.OK;

@RequestMapping(
    path = "/v1/menu/{id}/category/{categoryId}/items",
    produces = MediaType.APPLICATION_JSON_VALUE + "; charset=utf-8")
@RestController
@RequiredArgsConstructor
public class ItemController {

  #if USE_CQRS
  // CQRS
  private final CreateItemHandler createItemHandler;

  private final RequestToCommandMapper requestToCommandMapper;

  private final UpdateItemHandler updateItemHandler;

  private final DeleteItemHandler deleteItemHandler;
  #else
  // CRUD
  private final ItemService itemService;
  #endif

  @PostMapping
  @Operation(
      tags = "Item",
      summary = "Add an item to an existing category in a menu",
      description = "Adds a menu item",
      operationId = "AddMenuItem")
  @CreateAPIResponses
  ResponseEntity<ResourceCreatedResponse> createItem(
      @Parameter(description = "Menu id", required = true) @PathVariable("id") UUID menuId,
      @Parameter(description = "Category id", required = true) @PathVariable("categoryId")
          UUID categoryId,
      @Valid @RequestBody CreateItemRequest body,
      @Parameter(hidden = true) @RequestAttribute("CorrelationId") String correlationId) {
    #if USE_CQRS
    // CQRS
    return new ResponseEntity<>(
            new ResourceCreatedResponse(
                    createItemHandler
                            .handle(requestToCommandMapper.map(correlationId, menuId, categoryId, body))
                            .orElseThrow()),
            HttpStatus.CREATED);
    #else
    // CRUD
    return new ResponseEntity<>(
            itemService.create(menuId, categoryId, body, correlationId), HttpStatus.CREATED);
     #endif
  }

  @PutMapping("/{itemId}")
  @Operation(
      tags = "Item",
      summary = "Update an item in the menu",
      description = "Update an item in the menu",
      operationId = "UpdateMenuItem")
  @UpdateAPIResponses
  ResponseEntity<ResourceUpdatedResponse> updateItem(
      @Parameter(description = "Menu id", required = true) @PathVariable("id") UUID menuId,
      @Parameter(description = "Category id", required = true) @PathVariable("categoryId")
          UUID categoryId,
      @Parameter(description = "Item id", required = true) @PathVariable("itemId") UUID itemId,
      @Valid @RequestBody UpdateItemRequest body,
      @Parameter(hidden = true) @RequestAttribute("CorrelationId") String correlationId) {
    #if USE_CQRS
    // CQRS
    return new ResponseEntity<>(
            new ResourceUpdatedResponse(
                    updateItemHandler
                            .handle(requestToCommandMapper.map(correlationId, menuId, categoryId, itemId, body))
                            .orElseThrow()),
            HttpStatus.OK);
    #else
    // CRUD
    return new ResponseEntity<>(
        itemService.update(menuId, categoryId, body, correlationId), HttpStatus.OK);
     #endif
  }

  @DeleteMapping("/{itemId}")
  @Operation(
      tags = "Item",
      summary = "Removes an item from menu",
      description = "Removes an item from menu",
      operationId = "DeleteMenuItem")
  @DeleteAPIResponses
  ResponseEntity<Void> deleteItem(
      @Parameter(description = "Menu id", required = true) @PathVariable("id") UUID menuId,
      @Parameter(description = "Category id", required = true) @PathVariable("categoryId")
          UUID categoryId,
      @Parameter(description = "Item id", required = true) @PathVariable("itemId") UUID itemId,
      @Parameter(hidden = true) @RequestAttribute("CorrelationId") String correlationId) {

    #if USE_CQRS
    // CQRS
    deleteItemHandler.handle(new DeleteItemCommand(correlationId, menuId, categoryId, itemId));
    #endif
    return new ResponseEntity<>(OK);
  }
}
