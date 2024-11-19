package com.amido.stacks.workloads.menu.cqrs;

import com.amido.stacks.core.cqrs.handler.CommandHandler;
import com.amido.stacks.workloads.menu.cqrs.commands.CreateMenuCommand;
import com.amido.stacks.workloads.menu.domain.Menu;
import com.amido.stacks.workloads.menu.cqrs.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class CreateMenuHandler implements CommandHandler<CreateMenuCommand> {

  private final MenuService menuService;

  @Override
  public Optional<UUID> handle(CreateMenuCommand command) {
    UUID id = UUID.randomUUID();

    Menu menu =
        new Menu(
            id.toString(),
            command.getRestaurantId().toString(),
            command.getName(),
            command.getDescription(),
            new ArrayList<>(),
            command.getEnabled());

    menuService.verifyMenuNotAlreadyExisting(command);

    menuService.create(menu);
    return Optional.of(id);
  }
}
