package com.amido.stacks.workloads.menu.cqrs.mappers.commands;

import com.amido.stacks.core.mapping.BaseMapper;
import com.amido.stacks.workloads.menu.api.v1.dto.request.CreateItemRequest;

import com.amido.stacks.workloads.menu.cqrs.commands.CreateItemCommand;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(
    componentModel = "spring",
    uses = {},
    nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface CreateItemMapper extends BaseMapper<CreateItemRequest, CreateItemCommand> {}
