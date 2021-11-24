package com.karrotmvp.ourapt.v1.article.vote.dto.model;

import com.karrotmvp.ourapt.v1.apartment.dto.model.ApartmentDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class VoteWithWhereCreatedDto extends VoteDto {
  private ApartmentDto apartmentWhereCreated;
}
