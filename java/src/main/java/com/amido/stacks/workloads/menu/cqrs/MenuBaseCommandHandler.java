package com.amido.stacks.workloads.menu.cqrs;

import com.amido.stacks.core.cqrs.handler.CommandHandler;

import com.amido.stacks.workloads.menu.cqrs.commands.MenuCommand;
import com.amido.stacks.workloads.menu.cqrs.exception.MenuNotFoundException;
import com.amido.stacks.workloads.menu.cqrs.service.MenuService;
import com.amido.stacks.workloads.menu.domain.Menu;



import java.util.Optional;
import java.util.UUID;

public abstract class MenuBaseCommandHandler<T extends MenuCommand> implements CommandHandler<T> {

  abstract Optional<UUID> handleCommand(Menu menu, T command);

  protected MenuService menuService;

  protected MenuBaseCommandHandler(MenuService menuService) {
    this.menuService = menuService;
  }

  @Override
  public Optional<UUID> handle(T command) {

    Menu menu =
        menuService
            .findById(command.getMenuId().toString())
            .orElseThrow(() -> new MenuNotFoundException(command));

    return handleCommand(menu, command);
  }
}
