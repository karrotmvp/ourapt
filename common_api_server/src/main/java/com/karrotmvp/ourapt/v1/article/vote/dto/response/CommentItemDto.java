package com.karrotmvp.ourapt.v1.article.vote.dto.response;

import com.karrotmvp.ourapt.v1.article.question.dto.model.QuestionDto;
import com.karrotmvp.ourapt.v1.article.comment.dto.model.CommentDto;

import java.util.List;

public class CommentItemDto {
  private QuestionDto comment;
  private List<CommentDto> subComments;
}
