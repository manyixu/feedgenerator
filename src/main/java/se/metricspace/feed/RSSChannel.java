package se.metricspace.feed;

public class RSSChannel {
  private int itsId=-1;
  private int itsTTLMinutes = -1;

  private java.util.Date itsPubDate = null;

  private String itsAuthor = null;
  private String itsCategory = null;
  private String itsDescription = null;
  private String itsGenerator = null;
  private String itsImageLink = null;
  private String itsImageTitle = null;
  private String itsImageURL = null;
  private String itsTitle = null;
  private String itsURL = null;

  public RSSChannel() {
    itsId=-1;
  }
  
  @Override
  public boolean equals(Object someObject) {
    boolean eq = false;
    
    if(null!=someObject && someObject instanceof RSSChannel) {
      eq = (itsId == ((RSSChannel) someObject).itsId);
    }

    return eq;
  }

  public static java.util.Collection<RSSChannel> findAll(java.sql.Connection theConnection) throws java.sql.SQLException {
    java.util.Collection<RSSChannel> rssChannels = new java.util.ArrayList<>();
    if(null!=theConnection && !theConnection.isClosed()) {
      java.sql.PreparedStatement statement = null;
      java.sql.ResultSet resultset = null;
      try {
        statement = theConnection.prepareStatement("select * from channel", java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
        resultset = statement.executeQuery();
        while(resultset.next()) {
          RSSChannel item = parseDBRow(resultset);
          if(null!=item) {
            rssChannels.add(item);
          }
        }
      } finally {
        if(null!=resultset) {
          try {
            resultset.close();
          } catch (Throwable exception) {
          }
          resultset = null;
        }
        if(null!=statement) {
          try {
            statement.close();
          } catch (Throwable exception) {
          }
          statement = null;
        }
      }
    }
    return rssChannels;
  }


  public static RSSChannel findById(java.sql.Connection theConnection, int theId) throws java.sql.SQLException {
    RSSChannel rssChannel = null;
    if(null!=theConnection && !theConnection.isClosed()) {
      java.sql.PreparedStatement statement = null;
      java.sql.ResultSet resultset = null;
      try {
        statement = theConnection.prepareStatement("select * from channel where id=? limit 1", java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
        statement.setInt(1, theId);
        resultset = statement.executeQuery();
        while(resultset.next()) {
          rssChannel = parseDBRow(resultset);
        }
      } finally {
        if(null!=resultset) {
          try {
            resultset.close();
          } catch (Throwable exception) {
          }
          resultset = null;
        }
        if(null!=statement) {
          try {
            statement.close();
          } catch (Throwable exception) {
          }
          statement = null;
        }
      }
    }
    return rssChannel;
  }

  public String getAuthor() {
    return itsAuthor;
  }

  public String getCategory() {
    return itsCategory;
  }

  public String getDescription() {
    return itsDescription;
  }

  public String getGenerator() {
    return itsGenerator;
  }

  public int getId() {
    return itsId;
  }

  public String getImageLink() {
    return itsImageLink;
  }

  public String getImageTitle() {
    return itsImageTitle;
  }

  public String getImageURL() {
    return itsImageURL;
  }

  public java.util.Date getPubDate() {
    return itsPubDate;
  }

  public String getTitle() {
    return itsTitle;
  }

  public int getTTLMinutes() {
    return itsTTLMinutes;
  }

  private static String getValue(java.sql.ResultSet theResultSet, String theName) throws java.sql.SQLException {
    String value = theResultSet.getString(theName);
    if(theResultSet.wasNull()) {
      value = null;
    }
    return value;
  }

  public String getURL() {
    return itsURL;
  }

  @Override
  public int hashCode() {
    return itsId;
  }

  public boolean insert(java.sql.Connection theConnection) throws java.sql.SQLException {
    boolean inserted = false;

    if(null!=theConnection && !theConnection.isClosed()) {
      java.sql.PreparedStatement statement = null;
      try {
        statement = theConnection.prepareStatement("insert into channel(title,description, url, author, category, generator, ttlminutes, pub_date_yyyymmddhhmmss, image_url, image_title, image_link) values(?,?,?,?,?,?,?,?,?,?,?);");
        if(null!=itsTitle) {
          statement.setString(1, itsTitle);
        } else {
          statement.setNull(1, java.sql.Types.VARCHAR);
        }
        if(null!=this.itsDescription) {
          statement.setString(2, itsDescription);
        } else {
          statement.setNull(2, java.sql.Types.VARCHAR);
        }
        if(null!=this.itsURL) {
          statement.setString(3, itsURL);
        } else {
          statement.setNull(3, java.sql.Types.VARCHAR);
        }
        if(null!=this.itsAuthor) {
          statement.setString(4, itsAuthor);
        } else {
          statement.setNull(4, java.sql.Types.VARCHAR);
        }
        if(null!=this.itsCategory) {
          statement.setString(5, itsCategory);
        } else {
          statement.setNull(5, java.sql.Types.VARCHAR);
        }
        if(null!=this.itsGenerator) {
          statement.setString(6, itsGenerator);
        } else {
          statement.setNull(6, java.sql.Types.VARCHAR);
        }
        if(this.itsTTLMinutes>0) {
          statement.setInt(7, itsTTLMinutes);
        } else {
          statement.setNull(7, java.sql.Types.INTEGER);
        }
        if(null!=this.itsPubDate) {
          java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyyMMddHHmmss");
          statement.setString(8, format.format(itsPubDate));
        } else {
          statement.setNull(8, java.sql.Types.VARCHAR);
        }
        if(null!=this.itsImageURL) {
          statement.setString(9, itsImageURL);
        } else {
          statement.setNull(9, java.sql.Types.VARCHAR);
        }
        if(null!=this.itsImageURL) {
          statement.setString(10, itsImageTitle);
        } else {
          statement.setNull(10, java.sql.Types.VARCHAR);
        }
        if(null!=this.itsImageLink) {
          statement.setString(11, itsImageLink);
        } else {
          statement.setNull(11, java.sql.Types.VARCHAR);
        }
        
        statement.execute();
      } finally {
        if(null!=statement) {
          try {
            statement.close();
          } catch (Throwable exception) {
          }
          statement = null;
        }
      }
    }

    return inserted;
  }

  private static RSSChannel parseDBRow(java.sql.ResultSet theResultSet) throws java.sql.SQLException {
    RSSChannel item = new RSSChannel();
    item.itsId = theResultSet.getInt("id");
    item.itsAuthor = getValue(theResultSet,"author");
    item.itsCategory = getValue(theResultSet,"category");
    item.itsDescription = getValue(theResultSet,"description");
    item.itsGenerator = getValue(theResultSet,"generator");
    item.itsImageLink = getValue(theResultSet,"image_link");
    item.itsImageTitle = getValue(theResultSet,"image_title");
    item.itsImageURL = getValue(theResultSet,"image_url");
    item.itsTitle = getValue(theResultSet,"title");
    item.itsURL = getValue(theResultSet,"url");
    
    String tmpttl = getValue(theResultSet,"ttlminutes");
    if(null!=tmpttl) {
      try {
        item.itsTTLMinutes = Integer.parseInt(tmpttl);
      } catch (NumberFormatException exception) {
        item.itsTTLMinutes = -1;
      }
    } else {
      item.itsTTLMinutes = -1;
    }

    String tmpdate = getValue(theResultSet,"pub_date_yyyymmddhhmmss");
    if(null!=tmpdate) {
      try {
        java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyyMMddHHmmss");
        item.itsPubDate = formatter.parse(tmpdate);
      } catch (java.text.ParseException exception) {
        item.itsPubDate = null;
      }
    } else {
      item.itsPubDate = null;
    }

    return item;
  }

  public void setAuthor(String theValue) {
    itsAuthor = (null!=theValue && theValue.trim().length()>0) ? theValue.trim() : null;
  }

  public void setCategory(String theValue) {
    itsCategory = (null!=theValue && theValue.trim().length()>0) ? theValue.trim() : null;
  }

  public void setDescription(String theValue) {
    itsDescription = (null!=theValue && theValue.trim().length()>0) ? theValue.trim() : null;
  }

  public void setGenerator(String theValue) {
    itsGenerator = (null!=theValue && theValue.trim().length()>0) ? theValue.trim() : null;
  }

  public void setImageLink(String theValue) {
    itsImageLink = (null!=theValue && theValue.trim().length()>0) ? theValue.trim() : null;
  }

  public void setImageTitle(String theValue) {
    itsImageTitle = (null!=theValue && theValue.trim().length()>0) ? theValue.trim() : null;
  }

  public void setImageURL(String theValue) {
    itsImageURL = (null!=theValue && theValue.trim().length()>0) ? theValue.trim() : null;
  }

  public void setPubDate(String thePubDate) {
    itsPubDate = null;
    if(null!=thePubDate && 14==thePubDate.trim().length()) {
      java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyyMMddHHmmss");
      try {
        itsPubDate = formatter.parse(thePubDate);
      } catch (java.text.ParseException exception) {
        itsPubDate = null;
      }
    }
  }

  public void setTitle(String theValue) {
    itsTitle = (null!=theValue && theValue.trim().length()>0) ? theValue.trim() : null;
  }

  public void setMinutes(int theMinutes) {
    this.itsTTLMinutes = (theMinutes>0) ? theMinutes : -1;
  }

  public void setURL(String theValue) {
    itsURL = (null!=theValue && theValue.trim().length()>0) ? theValue.trim() : null;
  }

  @Override
  public String toString() {
    return "[ RSSChannel: id="+itsId+", title="+itsTitle+" ]";
  }

  public static void truncateTable(java.sql.Connection theConnection) throws java.sql.SQLException {
    if(null!=theConnection && !theConnection.isClosed()) {
      java.sql.PreparedStatement statement = null;
      try {
        statement = theConnection.prepareStatement("delete from channel");
        statement.execute();
      } finally {
        if(null!=statement) {
          try {
            statement.close();
          } catch (Throwable exception) {
          }
          statement = null;
        }
      }
    }
  }
}
