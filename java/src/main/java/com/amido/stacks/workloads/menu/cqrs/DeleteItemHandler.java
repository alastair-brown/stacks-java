package com.amido.stacks.workloads.menu.cqrs;

import com.amido.stacks.workloads.menu.cqrs.commands.DeleteItemCommand;
import com.amido.stacks.workloads.menu.domain.Menu;
import com.amido.stacks.workloads.menu.cqrs.service.ItemService;
import com.amido.stacks.workloads.menu.cqrs.service.MenuService;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

/** @author ArathyKrishna */
@Component
public class DeleteItemHandler extends MenuBaseCommandHandler<DeleteItemCommand> {

  protected ItemService itemService;

  public DeleteItemHandler(MenuService menuService, ItemService itemService) {
    super(menuService);
    this.itemService = itemService;
  }

  @Override
  Optional<UUID> handleCommand(Menu menu, DeleteItemCommand command) {

    itemService.delete(menu, command);

    return Optional.empty();
  }
}
