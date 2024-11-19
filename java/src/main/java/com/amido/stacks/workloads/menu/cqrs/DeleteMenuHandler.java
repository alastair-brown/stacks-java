package com.amido.stacks.workloads.menu.cqrs;

import com.amido.stacks.workloads.menu.cqrs.commands.DeleteMenuCommand;
import com.amido.stacks.workloads.menu.domain.Menu;
import com.amido.stacks.workloads.menu.cqrs.service.MenuService;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

/**
 * Handler for deleting menu
 *
 * @author ArathyKrishna
 */
@Component
public class DeleteMenuHandler extends MenuBaseCommandHandler<DeleteMenuCommand> {

  public DeleteMenuHandler(MenuService menuService) {
    super(menuService);
  }

  @Override
  Optional<UUID> handleCommand(Menu menu, DeleteMenuCommand command) {
    menuService.delete(menu);
    return Optional.empty();
  }
}
