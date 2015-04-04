package se.metricspace.feed;

public class RSSBuilder {
  public static void main(String[] args) {
    if(null!=args && (1==args.length||2==args.length)) {
      java.sql.Connection connection = null;
      java.io.PrintWriter writer = null;
      java.io.FileOutputStream targetStream = null;
      
      try {
        int channelid = Integer.parseInt(args[0]);
        Class.forName("com.mysql.jdbc.Driver");
        connection = java.sql.DriverManager.getConnection("jdbc:mysql://localhost:3306/feed?connectTimeout=250&socketTimeout=500&autoReconnect=true&useUnicode=true&characterEncoding=UTF-8", "feed", "feed");
        se.metricspace.feed.RSSChannel rssChannel = se.metricspace.feed.RSSChannel.findById(connection, channelid);
        java.util.List<RSSItem> rssItems = se.metricspace.feed.RSSItem.findAllByChannel(connection, channelid);
        if(null!=rssChannel && null!=rssItems && rssItems.size()>0) {
          if(2==args.length) {
            targetStream = new java.io.FileOutputStream(args[1]);
            writer = new java.io.PrintWriter(targetStream);
          } else {
            writer = new java.io.PrintWriter(System.out);
          }
          RSSBuilder.createRSSFeed(writer, rssChannel, rssItems);
        } else {
          System.out.println("No items found!");
        }
      } catch (java.io.IOException exception) {
        System.out.println("IOException: "+exception.getMessage());
        exception.printStackTrace(System.out);
      } catch (java.sql.SQLException exception) {
        System.out.println("SQLException: "+exception.getMessage());
        exception.printStackTrace(System.out);
      } catch (java.lang.NumberFormatException exception) {
        System.out.println("NumberFormatException: "+exception.getMessage());
        exception.printStackTrace(System.out);
      } catch (ClassNotFoundException exception) {
        System.out.println("ClassNotFoundException: "+exception.getMessage());
        exception.printStackTrace(System.out);
      } finally {
        if(null!=writer) {
          try {
            writer.close();
          } catch (Throwable exception) {
          }
          writer = null;
        }
        if(null!=targetStream) {
          try {
            targetStream.close();
          } catch (Throwable exception) {
          }
          targetStream = null;
        }
        if(null!=connection) {
          try {
            connection.close();
          } catch (Throwable exception) {
          }
          connection = null;
        }
      }
    } else {
      System.out.println("se.metricspace.feed.RSSBuilder expects one or two arguments: <channelid> and <targetfilename>");
    }
  }

  private static void createRSSFeed(java.io.PrintWriter theWriter, RSSChannel theChannel, java.util.List<RSSItem> theItems) {
    theWriter.println("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
    theWriter.println("<rss version=\"2.0\" xmlns:atom=\"http://www.w3.org/2005/Atom\">");
    theWriter.println("  <channel>");
    theWriter.println("    <atom:link href=\"http://some.example.url.org/rss.xml\" rel=\"self\" type=\"application/rss+xml\" />");
    if(null!=theChannel.getTitle()) {
      theWriter.println("    <title>"+theChannel.getTitle()+"</title>");
    }
    if(null!=theChannel.getDescription()) {
      theWriter.println("    <description>"+theChannel.getDescription()+"</description>");
    }
    if(null!=theChannel.getURL()) {
      theWriter.println("    <link>"+theChannel.getURL()+"</link>");
    }
    if(theChannel.getTTLMinutes()>0) {
      theWriter.println("    <ttl>"+theChannel.getTTLMinutes()+"</ttl>");
    }
    theWriter.println("    <lastBuildDate>"+formatDate(new java.util.Date())+"</lastBuildDate>");
    if(null!=theChannel.getPubDate()) {
      theWriter.println("    <pubDate>"+formatDate(theChannel.getPubDate())+"</pubDate>");
    }
    for(RSSItem rssItem:theItems) {
      theWriter.println("    <item>");
      if(null!=rssItem.getTitle()) {
        theWriter.println("      <title>"+rssItem.getTitle()+"</title>");
      }
      if(null!=rssItem.getDescription()) {
        theWriter.println("      <description>"+rssItem.getDescription()+"</description>");
      }
      if(null!=rssItem.getURL()) {
        theWriter.println("      <link>"+rssItem.getURL()+"</link>");
        theWriter.println("      <guid isPermaLink=\"true\">"+rssItem.getURL()+"</guid>");
      }
      if(null!=theChannel.getPubDate()) {
        theWriter.println("      <pubDate>"+formatDate(rssItem.getPubDate())+"</pubDate>");
      }
      theWriter.println("    </item>");
    }
    theWriter.println("  </channel>");
    theWriter.println("</rss>");
  }

  private static String formatDate(java.util.Date theDate) {
    java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss", java.util.Locale.ENGLISH);
    return formatter.format(theDate)+" 0000";
  }
}
