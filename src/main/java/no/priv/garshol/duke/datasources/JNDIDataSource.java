
package no.priv.garshol.duke.datasources;

import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

import no.priv.garshol.duke.RecordIterator;
import no.priv.garshol.duke.utils.JDBCUtils;

/**
 * Data source which retrieves a JDBC connection from JNDI.
 * @since 0.4
 */
public class JNDIDataSource extends JDBCDataSource {
  private String jndipath;

  public JNDIDataSource() {
    super();
  }

  @Override
  public RecordIterator getRecords() {
    verifyProperty(jndipath, "jndi-path");
    
    try {
      Statement stmt = JDBCUtils.open(jndipath);
      ResultSet rs = stmt.executeQuery(this.getQuery());
      // iterator takes care of closing the connection
      return new JDBCIterator(rs);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  protected String getSourceName() {
    return "JNDI";
  }

  public void setJndiPath(String path) {
    this.jndipath = path;
  }

  public String getJndiPath() {
    return jndipath;
  }

}
