package com.amido.stacks.menu.api.v1.dto.response;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("Unit")
public class ItemDTOTest {

  @Test
  public void equalsContract() {
    EqualsVerifier.simple().forClass(ItemDTO.class).verify();
  }
}