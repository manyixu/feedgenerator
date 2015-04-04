package se.metricspace.feed;

public class RSSItem {
  private String itsCategory = null;
  private int itsChannelId = -1;
  private String itsDescription = null;
  private int itsId = -1;
  private java.util.Date itsPubDate = null;
  private String itsTitle = null;
  private String itsURL = null;

  public RSSItem() {
    itsId = -1;
  }

  @Override
  public boolean equals(Object someObject) {
    boolean eq = false;
    
    if(null!=someObject && someObject instanceof RSSItem) {
      eq = (itsId == ((RSSItem) someObject).itsId);
    }

    return eq;
  }

  public static java.util.Collection<RSSItem> findAll(java.sql.Connection theConnection) throws java.sql.SQLException {
    java.util.Collection<RSSItem> rssItems = new java.util.ArrayList<>();
    if(null!=theConnection && !theConnection.isClosed()) {
      java.sql.PreparedStatement statement = null;
      java.sql.ResultSet resultset = null;
      try {
        statement = theConnection.prepareStatement("select * from rssitem", java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
        resultset = statement.executeQuery();
        while(resultset.next()) {
          
          RSSItem item = RSSItem.parseDBRow(resultset);
          if(null!=item) {
            rssItems.add(item);
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
    return rssItems;
  }

  public static java.util.List<RSSItem> findAllByChannel(java.sql.Connection theConnection, int theChannelId) throws java.sql.SQLException {
    java.util.List<RSSItem> rssItems = new java.util.ArrayList<>();
    if(null!=theConnection && !theConnection.isClosed()) {
      java.sql.PreparedStatement statement = null;
      java.sql.ResultSet resultset = null;
      try {
        statement = theConnection.prepareStatement("select * from rssitem where channel_id=? order by pub_date_yyyymmddhhmmss", java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
        statement.setInt(1, theChannelId);
        resultset = statement.executeQuery();
        while(resultset.next()) {
          
          RSSItem item = RSSItem.parseDBRow(resultset);
          if(null!=item) {
            rssItems.add(item);
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
    return rssItems;
  }

  public String getCategory() {
    return itsCategory;
  }

  public int getChannelId() {
    return itsChannelId;
  }

  public String getDescription() {
    return itsDescription;
  }

  public int getId() {
    return itsId;
  }

  public java.util.Date getPubDate() {
    return itsPubDate;
  }

  public String getTitle() {
    return itsTitle;
  }

  public String getURL() {
    return itsURL;
  }

  private static String getValue(java.sql.ResultSet theResultSet, String theName) throws java.sql.SQLException {
    String value = theResultSet.getString(theName);
    if(theResultSet.wasNull()) {
      value = null;
    }
    return value;
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
          

        statement = theConnection.prepareStatement("insert into rssitem(title, description, url, category, pub_date_yyyymmddhhmmss, channel_id) values(?,?,?,?,?,?);");
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
        if(null!=this.itsCategory) {
          statement.setString(4, itsCategory);
        } else {
          statement.setNull(4, java.sql.Types.VARCHAR);
        }
        if(null!=this.itsPubDate) {
          java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyyMMddHHmmss");
          statement.setString(5, format.format(itsPubDate));
        } else {
          statement.setNull(5, java.sql.Types.VARCHAR);
        }
        if(this.itsChannelId>0) {
          statement.setInt(6, itsChannelId);
        } else {
          statement.setNull(6, java.sql.Types.INTEGER);
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

  private static RSSItem parseDBRow(java.sql.ResultSet theResultSet) throws java.sql.SQLException {
    RSSItem item = new RSSItem();
    item.itsId = theResultSet.getInt("id");
    item.itsCategory = getValue(theResultSet,"category");
    item.itsDescription = getValue(theResultSet,"description");
    item.itsTitle = getValue(theResultSet,"title");
    item.itsURL = getValue(theResultSet,"url");
    item.itsChannelId = theResultSet.getInt("channel_id");
    if(theResultSet.wasNull()) {
      item.itsChannelId = -1;
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

  public void setCategory(String value) {
    itsCategory = (null!=value && value.trim().length()>0) ? value.trim() : null;
  }

  public void setChannelId(int theChannelId) {
    itsChannelId = (theChannelId > 0) ? theChannelId : -1;
  }

  public void setDescription(String value) {
    itsDescription = (null!=value && value.trim().length()>0) ? value.trim() : null;
  }

  public void setPubDate(String value) {
    itsPubDate = null;
    if(null!=value && 14==value.trim().length()) {
      java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyyMMddHHmmss");
      try {
        itsPubDate = formatter.parse(value);
      } catch (java.text.ParseException exception) {
        itsPubDate = null;
      }
    }
  }

  public void setTitle(String value) {
    itsTitle = (null!=value && value.trim().length()>0) ? value.trim() : null;
  }

  public void setURL(String value) {
    itsURL = (null!=value && value.trim().length()>0) ? value.trim() : null;
  }

  @Override
  public String toString() {
    return "[ RSSItem: id = "+itsId+" ]";
  }

  public static void truncateTable(java.sql.Connection theConnection) throws java.sql.SQLException {
    if(null!=theConnection && !theConnection.isClosed()) {
      java.sql.PreparedStatement statement = null;
      try {
        statement = theConnection.prepareStatement("delete from rssitem");
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
