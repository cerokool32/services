package org.cerokool.commons.lpojo.base;

import java.math.BigInteger;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class LightPojo extends HashMap<String, Object> 
{
	private static final long serialVersionUID = 1L;
	
	public LightPojo() 
	{
		super();
	}
	
	public LightPojo(Map<String, Object> data) 
	{
		super();
		super.putAll(data);
	}
	
	public String getString(Object key) 
	{
		Object obj = super.get(key);
		if(obj != null)
		{
			if(obj instanceof String)
			{
				return (String) obj;
			}
		}		
		return null;
	}
	
	public Date getDate(Object key) 
	{
		Object obj = super.get(key);
		if(obj != null)
		{
			if(obj instanceof Date)
			{
				return (Date) obj;
			}
		}		
		return null;
	}
	
	public Timestamp getTimeStamp(Object key) 
	{
		Object obj = super.get(key);
		if(obj != null)
		{
			if(obj instanceof Timestamp)
			{
				return (Timestamp) obj;
			}
		}		
		return null;
	}
	
	public Time getTime(Object key) 
	{
		Object obj = super.get(key);
		if(obj != null)
		{
			if(obj instanceof Time)
			{
				return (Time) obj;
			}
		}		
		return null;
	}
	
	public Integer getInteger(Object key) 
	{
		Object obj = super.get(key);
		if(obj != null)
		{
			if(obj instanceof String)
			{
				return Integer.valueOf((String) obj);
			}
		}		
		return null;
	}
	
	public BigInteger getBigInteger(Object key) 
	{
		Object obj = super.get(key);
		if(obj != null)
		{
			if(obj instanceof String)
			{
				return new BigInteger((String) obj);
			}
		}		
		return null;
	}
	
	public Double getDouble(Object key) 
	{
		Object obj = super.get(key);
		if(obj != null)
		{
			if(obj instanceof String)
			{
				return new Double((String) obj);
			}
		}		
		return null;
	}
	
	public Long getLong(Object key) 
	{
		Object obj = super.get(key);
		if(obj != null)
		{
			if(obj instanceof String)
			{
				return new Long((String) obj);
			}
		}		
		return null;
	}
	
	public Boolean getBoolean(Object key) 
	{
		Object obj = super.get(key);
		if(obj != null)
		{
			if(obj instanceof String)
			{
				return new Boolean((String) obj);
			}
		}		
		return null;
	}
}