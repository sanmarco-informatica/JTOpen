///////////////////////////////////////////////////////////////////////////////
//                                                                             
// JTOpen (IBM Toolbox for Java - OSS version)                                 
//                                                                             
// Filename: JDPreparedStatementProxy.java
//                                                                             
// The source code contained herein is licensed under the IBM Public License   
// Version 1.0, which has been approved by the Open Source Initiative.         
// Copyright (C) 1997-2010 International Business Machines Corporation and     
// others. All rights reserved.                                                
//                                                                             
///////////////////////////////////////////////////////////////////////////////

package com.ibm.as400.access;

import java.lang.reflect.InvocationTargetException;
import java.io.InputStream;
import java.io.Reader;
import java.io.Serializable;
import java.math.BigDecimal;
import java.net.URL;            
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
/* ifdef JDBC40 */
import java.sql.NClob;
/* endif */ 
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
/* ifdef JDBC40 */
import java.sql.RowId;
/* endif */ 
import java.sql.SQLException;
/* ifdef JDBC40 */
import java.sql.SQLXML;
/* endif */ 
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;



class JDPreparedStatementProxy
extends JDStatementProxy
implements PreparedStatement
{
  static final String copyright = "Copyright (C) 1997-2001 International Business Machines Corporation and others.";


  // Copied from JDError:


  private final static String NOT_SERIALIZABLE = "Parameter is not serializable.";
  

  public JDPreparedStatementProxy (JDConnectionProxy jdConnection)
  {
    super (jdConnection);
  }


// JDBC 2.0
    public void addBatch ()
        throws SQLException
    {
      callMethod ("addBatch");
    }



    public void clearParameters ()
      throws SQLException
    {
      callMethod ("clearParameters");
    }


    public boolean execute ()
      throws SQLException
    {
      cachedResultSet_ = null;
      return callMethodRtnBool ("execute");
    }


    public ResultSet executeQuery ()
        throws SQLException
    {
      cachedResultSet_ = null;
      try {
        JDResultSetProxy newResultSet = new JDResultSetProxy (jdConnection_, this);
        cachedResultSet_ = (JDResultSetProxy) connection_.callFactoryMethod (
                                         pxId_, "executeQuery", newResultSet);
      }
      catch (InvocationTargetException e) {
        throw JDConnectionProxy.rethrow1 (e);
      }
      return cachedResultSet_;
    }



    public int executeUpdate ()
      throws SQLException
    {
      cachedResultSet_ = null;
      return callMethodRtnInt ("executeUpdate");
    }

    public long executeLargeUpdate ()
        throws SQLException
      {
        cachedResultSet_ = null;
        return callMethodRtnLong ("executeLargeUpdate");
      }


// JDBC 2.0
    public ResultSetMetaData getMetaData ()
		throws SQLException
    {
      try {
        JDResultSetMetaDataProxy newMetaData = new JDResultSetMetaDataProxy (jdConnection_);
        return (JDResultSetMetaDataProxy) connection_.callFactoryMethod (
                                        pxId_, "getMetaData", newMetaData);
      }
      catch (InvocationTargetException e) {
        throw JDConnectionProxy.rethrow1 (e);
      }
    }



// JDBC 3.0
    public ParameterMetaData getParameterMetaData ()
		throws SQLException
    {
        // Avoid dragging in JDError
        //@K1D throw new SQLException (
        //@K1D                       AS400JDBCDriver.getResource("JD" + EXC_FUNCTION_NOT_SUPPORTED),
        //@K1D                       EXC_FUNCTION_NOT_SUPPORTED, -99999);
        try {    //@K1A
        JDParameterMetaDataProxy newMetaData = new JDParameterMetaDataProxy (jdConnection_);
        return (JDParameterMetaDataProxy) connection_.callFactoryMethod (pxId_, "getParameterMetaData", newMetaData);
      }
      catch (InvocationTargetException e) {
        throw JDConnectionProxy.rethrow1 (e);
      }
    }




// JDBC 2.0
    public void setArray (int parameterIndex, Array parameterValue)
      throws SQLException
    {
      if (parameterValue != null &&
          !(parameterValue instanceof Serializable) ){
        if (JDTrace.isTraceOn())
          JDTrace.logInformation (this, NOT_SERIALIZABLE);
        throw new SQLException ("NOT_SERIALIZABLE: "+parameterValue.getClass().getName());

      }

      callMethod ("setArray",
                  new Class[] { Integer.TYPE, Array.class },
                  new Object[] { Integer.valueOf(parameterIndex),
                                 parameterValue });
    }



    public void setAsciiStream (int parameterIndex,
                                InputStream parameterValue,
                                int length)
        throws SQLException
    {
      InputStream iStream;
      if (parameterValue == null ||
          parameterValue instanceof Serializable)
        iStream = parameterValue;
      else {
        try {
          iStream = new SerializableInputStream (parameterValue);
        }
        catch (java.io.IOException e) {
          SQLException throwException = new SQLException(e.getMessage());
          try {
            throwException.initCause(e); 
          } catch (Throwable t) {} 
          throw throwException;
        }
      }
      callMethod ("setAsciiStream",
                  new Class[] { Integer.TYPE, InputStream.class,
                                Integer.TYPE },
                  new Object[] { Integer.valueOf(parameterIndex),
                                 iStream,
                                 Integer.valueOf(length) });
    }



    public void setBigDecimal (int parameterIndex, BigDecimal parameterValue)
        throws SQLException
    {
      callMethod ("setBigDecimal", 
                  new Class[] { Integer.TYPE, BigDecimal.class },
                  new Object[] { Integer.valueOf(parameterIndex),
                                 parameterValue });
    }



    public void setBinaryStream (int parameterIndex,
                                InputStream parameterValue,
                                int length)
        throws SQLException
    {
      InputStream iStream;
      if (parameterValue == null ||
          parameterValue instanceof Serializable)
        iStream = parameterValue;
      else {
        try {
          iStream = new SerializableInputStream (parameterValue);
        }
        catch (java.io.IOException e) {
          SQLException throwException = new SQLException(e.getMessage());
          try {
            throwException.initCause(e); 
          } catch (Throwable t) {} 
          throw throwException;
        }
      }
      callMethod ("setBinaryStream",
                  new Class[] { Integer.TYPE, InputStream.class,
                                Integer.TYPE },
                  new Object[] { Integer.valueOf(parameterIndex),
                                 iStream,
                                 Integer.valueOf(length) });
    }



// JDBC 2.0
    public void setBlob (int parameterIndex, Blob parameterValue)
        throws SQLException
    {
      if (parameterValue != null &&
          !(parameterValue instanceof Serializable) ){
        if (JDTrace.isTraceOn())
          JDTrace.logInformation (this, NOT_SERIALIZABLE);
        throw new SQLException ("NOT_SERIALIZABLE: "+parameterValue.getClass().getName());

      }

      callMethod ("setBlob",
                  new Class[] { Integer.TYPE, Blob.class },
                  new Object[] { Integer.valueOf(parameterIndex),
                                 parameterValue });
    }



    public void setBoolean (int parameterIndex, boolean parameterValue)
        throws SQLException
    {
      callMethod ("setBoolean",
                  new Class[] { Integer.TYPE, Boolean.TYPE },
                  new Object[] { Integer.valueOf(parameterIndex),
                                 Boolean.valueOf(parameterValue) });
    }



    public void setByte (int parameterIndex, byte parameterValue)
        throws SQLException
    {
      callMethod ("setByte",
                  new Class[] { Integer.TYPE, Byte.TYPE },
                  new Object[] { Integer.valueOf(parameterIndex),
                                 Byte.valueOf(parameterValue) });
    }



    public void setBytes (int parameterIndex, byte[] parameterValue)
        throws SQLException
    {
      callMethod ("setBytes",
                  new Class[] { Integer.TYPE, byte[].class },
                  new Object[] { Integer.valueOf(parameterIndex),
                                 parameterValue });
    }



// JDBC 2.0
    public void setCharacterStream (int parameterIndex,
                                    Reader parameterValue,
                                    int length)
        throws SQLException
    {
      try {
        SerializableReader reader;
        if (parameterValue == null)
          reader = null;
        else
          reader = new SerializableReader (parameterValue, Math.max(0,length));
        callMethod ("setCharacterStream",
                    new Class[] { Integer.TYPE, Reader.class,
                                  Integer.TYPE },
                    new Object[] { Integer.valueOf(parameterIndex),
                                   reader,
                                   Integer.valueOf(length) });
      }
      catch (java.io.IOException e) {
        SQLException throwException = new SQLException(e.getMessage());
        try {
          throwException.initCause(e); 
        } catch (Throwable t) {} 
        throw throwException;
      }
    }



// JDBC 2.0
    public void setClob (int parameterIndex, Clob parameterValue)
        throws SQLException
    {
      if (parameterValue != null &&
          !(parameterValue instanceof Serializable) ){
        if (JDTrace.isTraceOn())
          JDTrace.logInformation (this, NOT_SERIALIZABLE);
        throw new SQLException ("NOT_SERIALIZABLE: "+parameterValue.getClass().getName());

      }

      callMethod ("setClob",
                  new Class[] { Integer.TYPE, Clob.class },
                  new Object[] { Integer.valueOf(parameterIndex),
                                 parameterValue });
    }



    public void setDate (int parameterIndex, Date parameterValue)
        throws SQLException
    {
      callMethod ("setDate",
                  new Class[] { Integer.TYPE, Date.class },
                  new Object[] { Integer.valueOf(parameterIndex),
                                 parameterValue });
    }



// JDBC 2.0
    public void setDate (int parameterIndex,
                         Date parameterValue,
                         Calendar calendar)
        throws SQLException
    {
      callMethod ("setDate",
                  new Class[] { Integer.TYPE, Date.class,
                                Calendar.class },
                  new Object[] { Integer.valueOf(parameterIndex),
                                 parameterValue,
                                 calendar });
    }



    public void setDouble (int parameterIndex, double parameterValue)
        throws SQLException
    {
      callMethod ("setDouble",
                  new Class[] { Integer.TYPE, Double.TYPE },
                  new Object[] { Integer.valueOf(parameterIndex),
                                 Double.valueOf(parameterValue) });
    }



    public void setFloat (int parameterIndex, float parameterValue)
        throws SQLException
    {
      callMethod ("setFloat",
                  new Class[] { Integer.TYPE, Float.TYPE },
                  new Object[] { Integer.valueOf(parameterIndex),
                                 Float.valueOf(parameterValue) });
    }



    public void setInt (int parameterIndex, int parameterValue)
        throws SQLException
    {
      callMethod ("setInt",
                  new Class[] { Integer.TYPE, Integer.TYPE },
                  new Object[] { Integer.valueOf(parameterIndex),
                                 Integer.valueOf(parameterValue) });
    }



    public void setLong (int parameterIndex, long parameterValue)
        throws SQLException
    {
      callMethod ("setLong",
                  new Class[] { Integer.TYPE, Long.TYPE },
                  new Object[] { Integer.valueOf(parameterIndex),
                                 Long.valueOf(parameterValue) });
    }



    public void setNull (int parameterIndex, int sqlType)
      throws SQLException
    {
      callMethod ("setNull",
                  new Class[] { Integer.TYPE, Integer.TYPE },
                  new Object[] { Integer.valueOf(parameterIndex),
                                 Integer.valueOf(sqlType) });
    }


    public void setNull (int parameterIndex, int sqlType, String typeName)
      throws SQLException
    {
      callMethod ("setNull",
                  new Class[] { Integer.TYPE, Integer.TYPE,
                                String.class },
                  new Object[] { Integer.valueOf(parameterIndex),
                                 Integer.valueOf(sqlType),
                                 typeName});
    }

    public void setObject (int parameterIndex, Object parameterValue)
        throws SQLException
    {
      if (parameterValue != null &&
          !(parameterValue instanceof Serializable) ){
        if (JDTrace.isTraceOn())
          JDTrace.logInformation (this, NOT_SERIALIZABLE);
        throw new SQLException ("NOT_SERIALIZABLE: "+parameterValue.getClass().getName());
      }

      callMethod ("setObject",
                  new Class[] { Integer.TYPE, Object.class },
                  new Object[] { Integer.valueOf(parameterIndex),
                                 parameterValue });
    }



    public void setObject (int parameterIndex,
			               Object parameterValue,
			               int sqlType)
      throws SQLException
    {
      if (parameterValue != null &&
          !(parameterValue instanceof Serializable) ){
        if (JDTrace.isTraceOn())
          JDTrace.logInformation (this, NOT_SERIALIZABLE);
        throw new SQLException ("NOT_SERIALIZABLE: "+parameterValue.getClass().getName());
      }

      callMethod ("setObject",
                  new Class[] { Integer.TYPE, Object.class,
                                Integer.TYPE },
                  new Object[] { Integer.valueOf(parameterIndex),
                                 parameterValue,
                                 Integer.valueOf(sqlType) });
    }



    public void setObject (int parameterIndex,
			               Object parameterValue,
			               int sqlType,
			               int scale)
        throws SQLException
    {
      if (parameterValue != null &&
          !(parameterValue instanceof Serializable) ){
        if (JDTrace.isTraceOn())
          JDTrace.logInformation (this, NOT_SERIALIZABLE);
        throw new SQLException ("NOT_SERIALIZABLE: "+parameterValue.getClass().getName());
      }

      callMethod ("setObject",
                  new Class[] { Integer.TYPE, Object.class,
                                Integer.TYPE, Integer.TYPE },
                  new Object[] { Integer.valueOf(parameterIndex),
                                 parameterValue,
                                 Integer.valueOf(sqlType),
                                 Integer.valueOf(scale) });
    }



// JDBC 2.0
    public void setRef (int parameterIndex, Ref parameterValue)
        throws SQLException
    {
      if (parameterValue != null &&
          !(parameterValue instanceof Serializable) ){
        if (JDTrace.isTraceOn())
          JDTrace.logInformation (this, NOT_SERIALIZABLE);
        throw new SQLException ("NOT_SERIALIZABLE: "+parameterValue.getClass().getName());
     }

      callMethod ("setRef",
                  new Class[] { Integer.TYPE, Ref.class },
                  new Object[] { Integer.valueOf(parameterIndex),
                                 parameterValue });
    }



    public void setShort (int parameterIndex, short parameterValue)
        throws SQLException
    {
      callMethod ("setShort",
                  new Class[] { Integer.TYPE, Short.TYPE },
                  new Object[] { Integer.valueOf(parameterIndex),
                                 Short.valueOf(parameterValue) });
    }



    public void setString (int parameterIndex, String parameterValue)
        throws SQLException
    {
      callMethod ("setString",
                  new Class[] { Integer.TYPE, String.class },
                  new Object[] { Integer.valueOf(parameterIndex),
                                 parameterValue });
    }



    public void setTime (int parameterIndex, Time parameterValue)
        throws SQLException
    {
      callMethod ("setTime",
                  new Class[] { Integer.TYPE, Time.class },
                  new Object[] { Integer.valueOf(parameterIndex),
                                 parameterValue });
    }



// JDBC 2.0
    public void setTime (int parameterIndex,
                         Time parameterValue,
                         Calendar calendar)
        throws SQLException
    {
      callMethod ("setTime",
                  new Class[] { Integer.TYPE, Time.class,
                                Calendar.class },
                  new Object[] { Integer.valueOf(parameterIndex),
                                 parameterValue, calendar });
    }



    public void setTimestamp (int parameterIndex, Timestamp parameterValue)
        throws SQLException
    {
      callMethod ("setTimestamp",
                  new Class[] { Integer.TYPE, Timestamp.class },
                  new Object[] { Integer.valueOf(parameterIndex),
                                 parameterValue });
    }



// JDBC 2.0
    public void setTimestamp (int parameterIndex,
                              Timestamp parameterValue,
                              Calendar calendar)
        throws SQLException
    {
      callMethod ("setTimestamp",
                  new Class[] { Integer.TYPE, Timestamp.class,
                                Calendar.class },
                  new Object[] { Integer.valueOf(parameterIndex),
                                 parameterValue, calendar });
    }



/**
@exception  SQLException    If a SQL error occurs.
@deprecated Use setCharacterStream(int, Reader, int) instead.
@see #setCharacterStream
**/
    public void setUnicodeStream (int parameterIndex,
                                  InputStream parameterValue,
                                  int length)
        throws SQLException
    {
      InputStream iStream;
      if (parameterValue == null ||
          parameterValue instanceof Serializable)
        iStream = parameterValue;
      else {
        try {
          iStream = new SerializableInputStream (parameterValue);
        }
        catch (java.io.IOException e) {
          SQLException throwException = new SQLException(e.getMessage());
          try {
            throwException.initCause(e); 
          } catch (Throwable t) {} 
          throw throwException;
        }
      }
      callMethod ("setUnicodeStream",
                  new Class[] { Integer.TYPE, InputStream.class,
                                Integer.TYPE },
                  new Object[] { Integer.valueOf(parameterIndex),
                                 iStream,
                                 Integer.valueOf(length) });
    }


// JDBC 3.0
    public void setURL (int parameterIndex,
                              URL parameterValue)
        throws SQLException
    {
      callMethod ("setURL",
                  new Class[] { Integer.TYPE, URL.class},
                  new Object[] { Integer.valueOf(parameterIndex),
                                 parameterValue });
    }


/* ifdef JDBC40 */
    //@PDA jdbc40
    public void setRowId(int parameterIndex, RowId x) throws SQLException
    {
        callMethod ("setRowId",
                new Class[] { Integer.TYPE, RowId.class},
                new Object[] { Integer.valueOf(parameterIndex), x });        
    }
/* endif */ 
    
    //@PDA jdbc40
    public void setNString(int parameterIndex, String value) throws SQLException
    {
        callMethod ("setNString",
                new Class[] { Integer.TYPE, String.class },
                new Object[] { Integer.valueOf(parameterIndex), value });                
    }
    
    //@PDA jdbc40
    public void setNCharacterStream(int parameterIndex, Reader value, long length) throws SQLException
    {            
        try {
            SerializableReader reader;
            if (value == null)
                reader = null;
            else
                reader = new SerializableReader (value, (int) Math.max(0,length));
            callMethod ("setNCharacterStream",
                    new Class[] { Integer.TYPE, Reader.class,
                    Long.TYPE },
                    new Object[] { Integer.valueOf(parameterIndex),
                    reader,
                    Long.valueOf(length) });
        }
        catch (java.io.IOException e) {
          SQLException throwException = new SQLException(e.getMessage());
          try {
            throwException.initCause(e); 
          } catch (Throwable t) {} 
          throw throwException;
        }
    }
    
    //@PDA jdbc40
/* ifdef JDBC40 */
    public void setNClob(int parameterIndex, NClob value) throws SQLException
    {
        if (value != null &&
                !(value instanceof Serializable) ){
            if (JDTrace.isTraceOn())
                JDTrace.logInformation (this, NOT_SERIALIZABLE);
                    throw new SQLException ("NOT_SERIALIZABLE: "+value.getClass().getName());

        }
        
        callMethod ("setNClob",
                new Class[] { Integer.TYPE, NClob.class },
                new Object[] { Integer.valueOf(parameterIndex),
                value });
    }
/* endif */ 
    
    //@PDA jdbc40
    public void setClob(int parameterIndex, Reader reader, long length) throws SQLException
    {
        try {
            if (length > Integer.MAX_VALUE) {
              JDError.throwSQLException(this, JDError.EXC_BUFFER_LENGTH_INVALID);
            }
            SerializableReader serialRreader;
            if (reader == null)
                serialRreader = null;
            else
                serialRreader = new SerializableReader (reader, (int) Math.max(0,length));
            callMethod ("setClob",
                    new Class[] { Integer.TYPE, Reader.class,
                    Long.TYPE },
                    new Object[] { Integer.valueOf(parameterIndex),
                    serialRreader,
                    Long.valueOf(length) });
        }
        catch (java.io.IOException e) {
          SQLException throwException = new SQLException(e.getMessage());
          try {
            throwException.initCause(e); 
          } catch (Throwable t) {} 
          throw throwException;
        }
    }
    
    //@PDA jdbc40
    public void setBlob(int parameterIndex, InputStream inputStream, long length) throws SQLException
    {
        InputStream iStream;
        if (inputStream == null ||
                inputStream instanceof Serializable)
            iStream = inputStream;
        else {
            try {
                iStream = new SerializableInputStream (inputStream);
            }
            catch (java.io.IOException e) {
              SQLException throwException = new SQLException(e.getMessage());
              try {
                throwException.initCause(e); 
              } catch (Throwable t) {} 
              throw throwException;
            }
        }
        callMethod ("setBlob",
                new Class[] { Integer.TYPE, InputStream.class,
                Long.TYPE },
                new Object[] { Integer.valueOf(parameterIndex),
                iStream,
                Long.valueOf(length) });
    }
    
    //@PDA jdbc40
    public void setNClob(int parameterIndex, Reader reader, long length) throws SQLException
    {
        try {
            SerializableReader serialRreader;
            if (reader == null)
                serialRreader = null;
            else
                serialRreader = new SerializableReader (reader, (int) Math.max(0,length));
            callMethod ("setNClob",
                    new Class[] { Integer.TYPE, Reader.class,
                    Long.TYPE },
                    new Object[] { Integer.valueOf(parameterIndex),
                    serialRreader,
                    Long.valueOf(length) });
        }
        catch (java.io.IOException e) {
          SQLException throwException = new SQLException(e.getMessage());
          try {
            throwException.initCause(e); 
          } catch (Throwable t) {} 
          throw throwException;
        }
    }
    
    //@PDA jdbc40
/* ifdef JDBC40 */
    public void setSQLXML(int parameterIndex, SQLXML xmlObject) throws SQLException
    {                                    
        if (xmlObject != null &&
                !(xmlObject instanceof Serializable) ){
            if (JDTrace.isTraceOn())
                JDTrace.logInformation (this, NOT_SERIALIZABLE);
                   throw new SQLException ("NOT_SERIALIZABLE: "+xmlObject.getClass().getName());

        }
        
        callMethod ("setSQLXML",
                new Class[] { Integer.TYPE, SQLXML.class },
                new Object[] { Integer.valueOf(parameterIndex),
                xmlObject });
    }
/* endif */ 
    
    //@pda jdbc40
    protected String[] getValidWrappedList()
    {
        return new String[] {  "java.sql.PreparedStatement" };
    } 
   
    
    //@PDA jdbc40
    public void setAsciiStream(int parameterIndex, InputStream x, long length) throws SQLException
    {
        InputStream iStream;
        if (x == null ||
                x instanceof Serializable)
            iStream = x;
        else {
            try {
                iStream = new SerializableInputStream (x);
            }
            catch (java.io.IOException e) {
              SQLException throwException = new SQLException(e.getMessage());
              try {
                throwException.initCause(e); 
              } catch (Throwable t) {} 
              throw throwException;
            }
        }
        callMethod ("setAsciiStream",
                new Class[] { Integer.TYPE, InputStream.class,
                Long.TYPE },
                new Object[] { Integer.valueOf(parameterIndex),
                iStream,
                Long.valueOf(length) });
    }

    //@PDA jdbc40
    public void setBinaryStream(int parameterIndex, InputStream x, long length) throws SQLException
    {
        InputStream iStream;
        if (x == null ||
                x instanceof Serializable)
            iStream = x;
        else {
            try {
                iStream = new SerializableInputStream (x);
            }
            catch (java.io.IOException e) {
              SQLException throwException = new SQLException(e.getMessage());
              try {
                throwException.initCause(e); 
              } catch (Throwable t) {} 
              throw throwException;
            }
        }
        callMethod ("setBinaryStream",
                new Class[] { Integer.TYPE, InputStream.class,
                Long.TYPE },
                new Object[] { Integer.valueOf(parameterIndex),
                iStream,
                Long.valueOf(length) });
    }
    
    //@PDA jdbc40
    public void setCharacterStream(int parameterIndex, Reader reader, long length) throws SQLException
    {
        try {
            SerializableReader serialReader;
            if (reader == null)
                serialReader = null;
            else
                serialReader = new SerializableReader (reader, (int)Math.max(0,length));
            callMethod ("setCharacterStream",
                    new Class[] { Integer.TYPE, Reader.class,
                    Long.TYPE },
                    new Object[] { Integer.valueOf(parameterIndex),
                    serialReader,
                    Long.valueOf(length) });
        }
        catch (java.io.IOException e) {
          SQLException throwException = new SQLException(e.getMessage());
          try {
            throwException.initCause(e); 
          } catch (Throwable t) {} 
          throw throwException;
        }
    }

    //@PDA jdbc40
    public void setAsciiStream(int parameterIndex, InputStream x) throws SQLException
    {
        InputStream iStream;
        if (x == null ||
                x instanceof Serializable)
            iStream = x;
        else {
            try {
                iStream = new SerializableInputStream (x);
            }
            catch (java.io.IOException e) {
              SQLException throwException = new SQLException(e.getMessage());
              try {
                throwException.initCause(e); 
              } catch (Throwable t) {} 
              throw throwException;
            }
        }
        callMethod ("setAsciiStream",
                new Class[] { Integer.TYPE, InputStream.class },
                new Object[] { Integer.valueOf(parameterIndex),
                iStream });
    }


    //@PDA jdbc40
    public void setBinaryStream(int parameterIndex, InputStream x) throws SQLException
    {
        InputStream iStream;
        if (x == null ||
                x instanceof Serializable)
            iStream = x;
        else {
            try {
                iStream = new SerializableInputStream (x);
            }
            catch (java.io.IOException e) {
              SQLException throwException = new SQLException(e.getMessage());
              try {
                throwException.initCause(e); 
              } catch (Throwable t) {} 
              throw throwException;
            }
        }
        callMethod ("setBinaryStream",
                new Class[] { Integer.TYPE, InputStream.class },
                new Object[] { Integer.valueOf(parameterIndex),
                iStream });
    }


    //@PDA jdbc40
    public void setBlob(int parameterIndex, InputStream inputStream) throws SQLException
    {
        InputStream iStream;
        if (inputStream == null ||
                inputStream instanceof Serializable)
            iStream = inputStream;
        else {
            try {
                iStream = new SerializableInputStream (inputStream);
            }
            catch (java.io.IOException e) {
              SQLException throwException = new SQLException(e.getMessage());
              try {
                throwException.initCause(e); 
              } catch (Throwable t) {} 
              throw throwException;
            }
        }
        callMethod ("setBlob",
                new Class[] { Integer.TYPE, InputStream.class },
                new Object[] { Integer.valueOf(parameterIndex),
                iStream });
    }


    //@PDA jdbc40
    public void setCharacterStream(int parameterIndex, Reader reader) throws SQLException
    {
        try {
            SerializableReader serialReader;
            if (reader == null)
                serialReader = null;
            else
                serialReader = new SerializableReader (reader);
            callMethod ("setCharacterStream",
                    new Class[] { Integer.TYPE, Reader.class },
                    new Object[] { Integer.valueOf(parameterIndex),
                    serialReader });
        }
        catch (java.io.IOException e) {
          SQLException throwException = new SQLException(e.getMessage());
          try {
            throwException.initCause(e); 
          } catch (Throwable t) {} 
          throw throwException;
        }
    }


    //@PDA jdbc40
    public void setClob(int parameterIndex, Reader reader) throws SQLException
    {
        try {
            SerializableReader serialReader;
            if (reader == null)
                serialReader = null;
            else
                serialReader = new SerializableReader (reader);
            callMethod ("setClob",
                    new Class[] { Integer.TYPE, Reader.class },
                    new Object[] { Integer.valueOf(parameterIndex),
                    serialReader });
        }
        catch (java.io.IOException e) {
          SQLException throwException = new SQLException(e.getMessage());
          try {
            throwException.initCause(e); 
          } catch (Throwable t) {} 
          throw throwException;
        }
    }


    //@PDA jdbc40
    public void setNCharacterStream(int parameterIndex, Reader value) throws SQLException
    {
        try {
            SerializableReader serialReader;
            if (value == null)
                serialReader = null;
            else
                serialReader = new SerializableReader (value);
            callMethod ("setNCharacterStream",
                    new Class[] { Integer.TYPE, Reader.class },
                    new Object[] { Integer.valueOf(parameterIndex),
                    serialReader });
        }
        catch (java.io.IOException e) {
          SQLException throwException = new SQLException(e.getMessage());
          try {
            throwException.initCause(e); 
          } catch (Throwable t) {} 
          throw throwException;
        }
    }


    //@PDA jdbc40
    public void setNClob(int parameterIndex, Reader reader) throws SQLException
    {
        try {
            SerializableReader serialReader;
            if (reader == null)
                serialReader = null;
            else
                serialReader = new SerializableReader (reader);
            callMethod ("setNClob",
                    new Class[] { Integer.TYPE, Reader.class },
                    new Object[] { Integer.valueOf(parameterIndex),
                    serialReader });
        }
        catch (java.io.IOException e) {
          SQLException throwException = new SQLException(e.getMessage());
          try {
            throwException.initCause(e); 
          } catch (Throwable t) {} 
          throw throwException;
        }
    }
    
    
    public void setDBDefault(int parameterIndex) throws SQLException
    {
        setDB2Default(parameterIndex);         
    }

    public void setDB2Default(int parameterIndex) throws SQLException {
        callMethod("setDB2Default", 
            new Class[] { Integer.TYPE},
            new Object[] { Integer.valueOf(parameterIndex)}); 
    }

    public void setDB2Unassigned(int parameterIndex) throws SQLException {
      callMethod("setDB2Unassigned", 
          new Class[] { Integer.TYPE},
          new Object[] { Integer.valueOf(parameterIndex)}); 
    }
    
    public void setDBUnassigned(int parameterIndex) throws SQLException
    {
        setDB2Unassigned(parameterIndex); //2 is unassigned   
    }
    
    
    
}
