/*
 * @(#)Answer.java   1.0   30/mag/2012
 *
 * Copyright 2012-2012 Politecnico di Milano. All Rights Reserved.
 *
 * This software is the proprietary information of Politecnico di Milano.
 * Use is subject to license terms.
 *
 * @(#) $Id$
 */
package it.expertfinding.groundtruth;

public class Answer {

   private Integer questionId;
   private String questionText;
   private Integer answer;

   public Answer() {

   }

   public Answer(Integer questionId, String questionText, Integer answer) {

      this.questionId = questionId;
      this.questionText = questionText;
      this.answer = answer;
   }

   public Integer getQuestionId() {
      return this.questionId;
   }

   public void setQuestionId(Integer questionId) {
      this.questionId = questionId;
   }

   public String getQuestionText() {
      return this.questionText;
   }

   public void setQuestionText(String questionText) {
      this.questionText = questionText;
   }

   public Integer getAnswer() {
      return this.answer;
   }

   public void setAnswer(Integer answer) {
      this.answer = answer;
   }
}
