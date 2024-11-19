package com.amido.stacks.workloads.menu.cqrs;


import com.amido.stacks.workloads.menu.cqrs.commands.UpdateCategoryCommand;
import com.amido.stacks.workloads.menu.domain.Menu;
import com.amido.stacks.workloads.menu.cqrs.service.CategoryService;
import com.amido.stacks.workloads.menu.cqrs.service.MenuService;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

/** @author ArathyKrishna */
@Component
public class UpdateCategoryHandler extends MenuBaseCommandHandler<UpdateCategoryCommand> {

  protected CategoryService categoryService;

  public UpdateCategoryHandler(MenuService menuService, CategoryService categoryService) {
    super(menuService);
    this.categoryService = categoryService;
  }

  @Override
  Optional<UUID> handleCommand(Menu menu, UpdateCategoryCommand command) {
    categoryService.update(menu, command);
    return Optional.of(command.getCategoryId());
  }
}
