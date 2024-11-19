package com.amido.stacks.workloads.menu.cqrs;

import com.amido.stacks.workloads.menu.cqrs.commands.DeleteCategoryCommand;
import com.amido.stacks.workloads.menu.domain.Menu;
import com.amido.stacks.workloads.menu.cqrs.service.CategoryService;
import com.amido.stacks.workloads.menu.cqrs.service.MenuService;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

/** @author ArathyKrishna */
@Component
public class DeleteCategoryHandler extends MenuBaseCommandHandler<DeleteCategoryCommand> {

  protected CategoryService categoryService;

  public DeleteCategoryHandler(MenuService menuService, CategoryService categoryService) {
    super(menuService);
    this.categoryService = categoryService;
  }

  Optional<UUID> handleCommand(Menu menu, DeleteCategoryCommand command) {

    categoryService.delete(menu, command);
    return Optional.empty();
  }
}
