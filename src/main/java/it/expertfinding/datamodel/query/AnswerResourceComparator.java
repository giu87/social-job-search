/*
 * @(#)AnswerResourceComparator.java   1.0   15/mag/2012
 *
 * Copyright 2012-2012 Politecnico di Milano. All Rights Reserved.
 *
 * This software is the proprietary information of Politecnico di Milano.
 * Use is subject to license terms.
 *
 * @(#) $Id$
 */
package it.expertfinding.datamodel.query;

import java.util.Comparator;

public class AnswerResourceComparator implements Comparator<AnswerResource> {

   @Override
   public int compare(AnswerResource r1, AnswerResource r2) {

      Double rank1 = r1.getScore();
      Double rank2 = r2.getScore();

      if (rank1 < rank2) {
         return +1;
      } else if (rank1 > rank2) {
         return -1;
      } else {
         return 0;
      }
   }
}
