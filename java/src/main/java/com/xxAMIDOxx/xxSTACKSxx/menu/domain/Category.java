package com.xxAMIDOxx.xxSTACKSxx.menu.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class Category {

  private String id;

  private String name;

  private String description;

  @Builder.Default
  private List<Item> items = new ArrayList<>();

  public Category addUpdateItem(Item item) {
    if (this.items == null) {
      this.items = new ArrayList<>();
    }
    this.items =
            this.items.stream()
                    .filter(c -> !c.getId().equals(item.getId()))
                    .collect(Collectors.toList());
    this.items.add(item);
    return this;
  }


}