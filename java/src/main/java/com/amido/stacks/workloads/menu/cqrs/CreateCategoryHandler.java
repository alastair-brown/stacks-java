package com.amido.stacks.workloads.menu.cqrs;

import com.amido.stacks.workloads.menu.cqrs.commands.CreateCategoryCommand;
import com.amido.stacks.workloads.menu.cqrs.service.CategoryService;
import com.amido.stacks.workloads.menu.cqrs.service.MenuService;
import com.amido.stacks.workloads.menu.domain.Menu;

import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class CreateCategoryHandler extends MenuBaseCommandHandler<CreateCategoryCommand> {

  protected CategoryService categoryService;

  public CreateCategoryHandler(MenuService menuService, CategoryService categoryService) {
    super(menuService);
    this.categoryService = categoryService;
  }

  @Override
  Optional<UUID> handleCommand(Menu menu, CreateCategoryCommand command) {
    return categoryService.create(menu, command);
  }
}
