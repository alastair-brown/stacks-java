package com.amido.stacks.workloads.menu.cqrs.service;

import com.amido.stacks.workloads.menu.cqrs.commands.CreateCategoryCommand;
import com.amido.stacks.workloads.menu.cqrs.commands.DeleteCategoryCommand;
import com.amido.stacks.workloads.menu.cqrs.commands.UpdateCategoryCommand;
import com.amido.stacks.workloads.menu.cqrs.exception.CategoryDoesNotExistException;
import com.amido.stacks.workloads.menu.cqrs.mappers.cqrs.CreateCategoryCommandMapper;
import com.amido.stacks.workloads.menu.cqrs.mappers.cqrs.UpdateCategoryCommandMapper;
import com.amido.stacks.workloads.menu.cqrs.service.utility.MenuHelperService;
import com.amido.stacks.workloads.menu.domain.Category;
import com.amido.stacks.workloads.menu.domain.Menu;
import java.util.Optional;
import java.util.UUID;

import com.amido.stacks.workloads.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {

  private final MenuHelperService menuHelperService;
  private final MenuRepository menuRepository;
  private final CreateCategoryCommandMapper createCategoryCommandMapper;
  private final UpdateCategoryCommandMapper updateCategoryCommandMapper;

  public Optional<UUID> create(Menu menu, CreateCategoryCommand command) {

    menuHelperService.verifyCategoryNameNotAlreadyExisting(
        command, menu, command.getCategoryId(), command.getName());

    UUID id = UUID.randomUUID();

    Category category = createCategoryCommandMapper.fromDto(command);
    category.setId(id.toString());
    command.setCategoryId(id);

    menuHelperService.addOrUpdateCategory(menu, category);
    menuRepository.save(menu);

    return Optional.of(command.getCategoryId());
  }

  public void delete(Menu menu, DeleteCategoryCommand command) {

    Optional<Category> optCategory =
        menu.getCategories().stream()
            .filter(c -> command.getCategoryId().toString().equals(c.getId()))
            .findFirst();

    if (optCategory.isEmpty()) {
      throw new CategoryDoesNotExistException(command, command.getCategoryId());
    }

    menuHelperService.removeCategory(menu, command.getCategoryId());
    menuRepository.save(menu);
  }

  public void update(Menu menu, UpdateCategoryCommand command) {

    // check by Id
    Category category =
        menuHelperService.checkCategoryExistsById(command, menu, command.getCategoryId());

    // Check By name
    menuHelperService.verifyCategoryNameNotAlreadyExisting(
        command, menu, command.getCategoryId(), command.getName());

    Category updatedCategory = updateCategoryCommandMapper.fromDto(command);
    updatedCategory.setItems(category.getItems());

    menuHelperService.addOrUpdateCategory(menu, updatedCategory);
    menuRepository.save(menu);
  }

  public Optional<Menu> findById(String id) {
    return menuRepository.findById(id);
  }
}
