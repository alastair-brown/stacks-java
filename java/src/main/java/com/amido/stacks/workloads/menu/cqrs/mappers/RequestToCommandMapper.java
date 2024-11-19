package com.amido.stacks.workloads.menu.cqrs.mappers;

import com.amido.stacks.workloads.menu.api.v1.dto.request.*;
import com.amido.stacks.workloads.menu.cqrs.commands.*;
import com.amido.stacks.workloads.menu.cqrs.mappers.commands.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RequestToCommandMapper {

  @Autowired private CreateMenuMapper createMenuMapper;

  @Autowired private UpdateMenuMapper updateMenuMapper;

  @Autowired private CreateCategoryMapper createCategoryMapper;

  @Autowired private UpdateCategoryMapper updateCategoryMapper;

  @Autowired private CreateItemMapper createItemMapper;

  @Autowired private UpdateItemMapper updateItemMapper;

  public CreateMenuCommand map(String correlationId, CreateMenuRequest r) {

    CreateMenuCommand createMenuCommand = createMenuMapper.fromDto(r);
    createMenuCommand.setCorrelationId(correlationId);

    return createMenuCommand;
  }

  public UpdateMenuCommand map(String correlationId, UUID menuId, UpdateMenuRequest r) {

    UpdateMenuCommand updateMenuCommand = updateMenuMapper.fromDto(r);
    updateMenuCommand.setCorrelationId(correlationId);
    updateMenuCommand.setMenuId(menuId);

    return updateMenuCommand;
  }

  public CreateCategoryCommand map(String correlationId, UUID menuId, CreateCategoryRequest r) {

    CreateCategoryCommand createCategoryCommand = createCategoryMapper.fromDto(r);
    createCategoryCommand.setCorrelationId(correlationId);
    createCategoryCommand.setMenuId(menuId);

    return createCategoryCommand;
  }

  public UpdateCategoryCommand map(
      String correlationId, UUID menuId, UUID categoryId, UpdateCategoryRequest r) {

    UpdateCategoryCommand updateCategoryCommand = updateCategoryMapper.fromDto(r);
    updateCategoryCommand.setCorrelationId(correlationId);
    updateCategoryCommand.setMenuId(menuId);
    updateCategoryCommand.setCategoryId(categoryId);

    return updateCategoryCommand;
  }

  public CreateItemCommand map(
      String correlationId, UUID menuId, UUID categoryId, CreateItemRequest r) {

    CreateItemCommand createItemCommand = createItemMapper.fromDto(r);
    createItemCommand.setCorrelationId(correlationId);
    createItemCommand.setMenuId(menuId);
    createItemCommand.setCategoryId(categoryId);

    return createItemCommand;
  }

  public UpdateItemCommand map(
      String correlationId, UUID menuId, UUID categoryId, UUID itemId, UpdateItemRequest r) {

    UpdateItemCommand updateItemCommand = updateItemMapper.fromDto(r);
    updateItemCommand.setCorrelationId(correlationId);
    updateItemCommand.setMenuId(menuId);
    updateItemCommand.setCategoryId(categoryId);
    updateItemCommand.setItemId(itemId);

    return updateItemCommand;
  }

  private RequestToCommandMapper() {}
}
