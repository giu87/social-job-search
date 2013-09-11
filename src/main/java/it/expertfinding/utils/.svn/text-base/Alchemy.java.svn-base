/*
 * @(#)Alchemy.java   1.0   26/apr/2012
 *
 * Copyright 2012-2012 Politecnico di Milano. All Rights Reserved.
 *
 * This software is the proprietary information of Politecnico di Milano.
 * Use is subject to license terms.
 *
 * @(#) $Id$
 */
package it.expertfinding.utils;

import it.expertfinding.utils.alchemy.AlchemyAPI;
import it.expertfinding.utils.alchemy.AlchemyAPI_NamedEntityParams;
import it.expertfinding.utils.alchemy.EntityResponse;
import it.expertfinding.utils.alchemy.EntityResponse.Entity;
import it.expertfinding.utils.tagme.TagMeAnnotation;
import it.expertfinding.utils.tagme.TagMeUtils;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.apache.commons.configuration.Configuration;
import org.xml.sax.SAXException;

public class Alchemy {

   protected static Configuration conf = Facade.conf;

   /**
    * @param args
    * @throws ParserConfigurationException
    * @throws SAXException
    * @throws IOException
    * @throws XPathExpressionException
    */
   public static void main(String[] args) throws XPathExpressionException, IOException,
         SAXException, ParserConfigurationException {

      AlchemyAPI api = AlchemyAPI.GetInstanceFromString(conf.getString("alchemy.key"));
      // CategorizationResponse response = api
      // .URLGetCategory("https://www.facebook.com/HowIMetYourMotherCBS/info"/*
      // "http://tinyurl.com/36pshne" */);
      //
      // System.out.println(response.getCategory());
      // System.out.println(response.getLanguage());
      // System.out.println(response.getScore());

      AlchemyAPI_NamedEntityParams params = new AlchemyAPI_NamedEntityParams();
      params.setShowSourceText(true);

      EntityResponse resp = api
            .HTMLGetRankedNamedEntities(
                  "<h2>Carriera</h2><h3>Giocatore</h3><h4>Club</h4><p>Diventa particolarmente famoso in <a href=\\\"/pages/w/108756372481627\\\">Italia</a> nel <a href=\\\"http://it.wikipedia.org/wiki/1990\\\" class=\\\"wikipedia\\\">1990</a> quando viene ingaggiato dal <a href=\\\"/pages/w/107635185925618\\\">Cesena</a> dopo esser cresciuto nel <a href=\\\"/pages/w/112261402119785\\\">San Paolo</a> e aver giocato con <a href=\\\"/pages/w/107745102586436\\\">Sporting Lisbona</a> e <a href=\\\"/pages/w/103153419725391\\\">Central Espa\u00f1ol</a>. L\'anno successivo \u00e8 alla <a href=\\\"/pages/w/108567139167269\\\">Sampdoria</a>, poi il ritorno in patria dove gioca con <a href=\\\"/pages/w/107954912565895\\\">International</a> e <a href=\\\"/pages/w/112184972131602\\\">Vasco da Gama</a>. Nel <a href=\\\"http://it.wikipedia.org/wiki/1995\\\" class=\\\"wikipedia\\\">1995</a> batte la pista asiatica con una prima esperienza coi giapponesi del <a href=\\\"/pages/w/105618209471847\\\">Kashima Antlers</a>, cui segue una parentesi argentina con il <a href=\\\"/pages/w/111997652149607\\\">San Lorenzo</a>. Nel <a href=\\\"http://it.wikipedia.org/wiki/1999\\\" class=\\\"wikipedia\\\">1999</a> nuova esperienza in terra nipponica con il <a href=\\\"/pages/w/112470992099015\\\">Kyoto Sanga</a>, per poi tornare in patria dove conclude la sua carriera cambiando sei squadre nel giro di quattro anni</p><h4>Nazionale</h4><p>Dopo la partecipazione al <a href=\\\"/pages/w/107817452580861\\\">Mondiale Under-20</a> del <a href=\\\"http://it.wikipedia.org/wiki/1985\\\" class=\\\"wikipedia\\\">1985</a> dove ottiene il riconoscimento di miglior giocatore del torneo, Silas gioca con la <i>Sele\u00e7ao</i> 38 volte, andando a segno in una sola occasione e prendendo parte alle spedizioni verde-oro ai <a href=\\\"/pages/w/108279902533995\\\">Mondiale</a> del <a href=\\\"/pages/w/112692068742840\\\">1986</a> e del <a href=\\\"/pages/w/111712502178235\\\">1990</a>.</p>\n",
                  null, params);
      List<TagMeAnnotation> annotations = TagMeUtils
            .getNamedEntities(
                  "Diventa particolarmente famoso in Italia nel 1990 quando viene ingaggiato dal Cesena dopo esser cresciuto nel San Paolo e aver giocato con Sporting Lisbona e Central Espa\u00f1ol. L\'anno successivo \u00e8 alla Sampdoria, poi il ritorno in patria dove gioca con International e Vasco da Gama. Nel 1995 batte la pista asiatica con una prima esperienza coi giapponesi del Kashima Antlers, cui segue una parentesi argentina con il San Lorenzo. Nel 1999 nuova esperienza in terra nipponica con il Kyoto Sanga, per poi tornare in patria dove conclude la sua carriera cambiando sei squadre nel giro di quattro anni\nNazionale\nDopo la partecipazione al Mondiale Under-20 del 1985 dove ottiene il riconoscimento di miglior giocatore del torneo, Silas gioca con la Sele\u00e7ao 38 volte, andando a segno in una sola occasione e prendendo parte alle spedizioni verde-oro ai Mondiale del 1986 e del 1990.\n",
                  0.1f);

      System.out.println(resp.getText());
      for (Entity ent : resp.getEntities()) {
         System.out.println(ent.getText());
         System.out.println(ent.getType());
         System.out.println(ent.getRelevance());
      }

      for (TagMeAnnotation an : annotations) {
         System.out.println(an.getTitle());
         System.out.println(an.getRho());
         System.out.println(an.getId());
      }
   }
}
