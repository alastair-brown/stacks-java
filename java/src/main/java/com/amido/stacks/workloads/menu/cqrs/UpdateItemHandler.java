package com.amido.stacks.workloads.menu.cqrs;

import com.amido.stacks.workloads.menu.cqrs.commands.UpdateItemCommand;
import com.amido.stacks.workloads.menu.domain.Menu;
import com.amido.stacks.workloads.menu.cqrs.service.ItemService;
import com.amido.stacks.workloads.menu.cqrs.service.MenuService;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

/** @author ArathyKrishna */
@Component
public class UpdateItemHandler extends MenuBaseCommandHandler<UpdateItemCommand> {

  protected ItemService itemService;

  public UpdateItemHandler(MenuService menuService, ItemService itemService) {
    super(menuService);
    this.itemService = itemService;
  }

  @Override
  Optional<UUID> handleCommand(Menu menu, UpdateItemCommand command) {

    itemService.update(menu, command);
    return Optional.of(command.getItemId());
  }
}
