package com.amido.stacks.workloads.menu.cqrs;


import com.amido.stacks.workloads.menu.cqrs.commands.UpdateMenuCommand;
import com.amido.stacks.workloads.menu.domain.Menu;
import com.amido.stacks.workloads.menu.cqrs.service.MenuService;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class UpdateMenuHandler extends MenuBaseCommandHandler<UpdateMenuCommand> {

  public UpdateMenuHandler(MenuService menuService) {
    super(menuService);
  }

  @Override
  Optional<UUID> handleCommand(Menu menu, UpdateMenuCommand command) {

    menuService.update(menu, command);

    return Optional.of(command.getMenuId());
  }
}
