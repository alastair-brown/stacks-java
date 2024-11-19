package com.amido.stacks.workloads.menu.cqrs;


import com.amido.stacks.workloads.menu.cqrs.commands.CreateItemCommand;
import com.amido.stacks.workloads.menu.cqrs.service.ItemService;
import com.amido.stacks.workloads.menu.cqrs.service.MenuService;
import com.amido.stacks.workloads.menu.domain.Menu;

import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class CreateItemHandler extends MenuBaseCommandHandler<CreateItemCommand> {

  public CreateItemHandler(MenuService menuService, ItemService itemService) {
    super(menuService);
    this.itemService = itemService;
  }

  protected ItemService itemService;

  @Override
  Optional<UUID> handleCommand(Menu menu, CreateItemCommand command) {

    itemService.create(menu, command);
    return Optional.of(command.getItemId());
  }
}
