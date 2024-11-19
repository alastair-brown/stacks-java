package com.amido.stacks.workloads.menu.cqrs.mappers.commands;

import com.amido.stacks.core.mapping.BaseMapper;
import com.amido.stacks.workloads.menu.api.v1.dto.request.UpdateCategoryRequest;
import com.amido.stacks.workloads.menu.cqrs.commands.UpdateCategoryCommand;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(
    componentModel = "spring",
    uses = {},
    nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface UpdateCategoryMapper
    extends BaseMapper<UpdateCategoryRequest, UpdateCategoryCommand> {}
