package com.karrotmvp.ourapt.v1.comment.dto.response;

import com.karrotmvp.ourapt.v1.comment.dto.model.CommentDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class GetCommentsOfQuestionDto {
  private List<CommentDto> comments;
}
