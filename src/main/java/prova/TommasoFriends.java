/*
 * @(#)TommasoFriends.java   1.0   12/lug/2012
 *
 * Copyright 2012-2012 Politecnico di Milano. All Rights Reserved.
 *
 * This software is the proprietary information of Politecnico di Milano.
 * Use is subject to license terms.
 *
 * @(#) $Id$
 */
package prova;

import it.expertfinding.utils.facebook.FacebookUtils;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.types.Post;
import com.restfb.types.User;


public class TommasoFriends {

   public static void main(String [] args ){
      
      FacebookClient fc = new DefaultFacebookClient("AAABjAfwdPNcBAFlYc7VLDqCIHroh2f7qCAWhySuk6f1z1OokA6rqaBJvA7ic6ap5bVD6y85Bh6QWthMcr0Fyg1lQxDoZD");
      FacebookUtils facebook = new FacebookUtils();
      facebook.setFc(fc);

      Connection<User> myFriends = fc.fetchConnection("me/friends", User.class);
      
      for(User friend: myFriends.getData()){
       
         Connection<Post> posts = fc.fetchConnection("/"+friend.getId()+"/posts", Post.class);
         System.out.println(friend.getName()+": "+posts.getData().size());
         
      }

   }
}
