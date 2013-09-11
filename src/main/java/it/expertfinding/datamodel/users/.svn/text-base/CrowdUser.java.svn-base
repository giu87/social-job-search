package it.expertfinding.datamodel.users;

import it.expertfinding.datamodel.users.facebook.FacebookUser;
import it.expertfinding.datamodel.users.linkedin.LinkedinUser;
import it.expertfinding.datamodel.users.twitter.TwitterUser;

import org.bson.types.ObjectId;

public class CrowdUser {

   private ObjectId _id;

   private FacebookUser facebook;
   private LinkedinUser linkedin;
   private TwitterUser twitter;

   /**
    * @return Returns the _id.
    */
   public ObjectId get_id() {
      return this._id;
   }

   /**
    * @param _id
    *           The _id to set.
    */
   public void set_id(ObjectId _id) {
      this._id = _id;
   }

   /**
    * @return Returns the facebook.
    */
   public FacebookUser getFacebook() {
      return this.facebook;
   }

   /**
    * @param facebook
    *           The facebook to set.
    */
   public void setFacebook(FacebookUser facebook) {
      this.facebook = facebook;
   }

   /**
    * @return Returns the linkedin.
    */
   public LinkedinUser getLinkedin() {
      return this.linkedin;
   }

   /**
    * @param linkedin
    *           The linkedin to set.
    */
   public void setLinkedin(LinkedinUser linkedin) {
      this.linkedin = linkedin;
   }

   /**
    * @return Returns the twitter.
    */
   public TwitterUser getTwitter() {
      return this.twitter;
   }

   /**
    * @param twitter
    *           The twitter to set.
    */
   public void setTwitter(TwitterUser twitter) {
      this.twitter = twitter;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((_id == null) ? 0 : _id.hashCode());
      return result;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public boolean equals(Object obj) {
      if (this == obj)
         return true;
      if (obj == null)
         return false;
      if (getClass() != obj.getClass())
         return false;
      CrowdUser other = (CrowdUser) obj;
      if (_id == null) {
         if (other._id != null)
            return false;
      } else if (!_id.equals(other._id))
         return false;
      return true;
   }
}
