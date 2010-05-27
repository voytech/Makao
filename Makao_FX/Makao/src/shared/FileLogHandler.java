package shared;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.StreamHandler;

public class FileLogHandler extends Handler{
	private String filename = null;
	public void setFileName(String name)
	{
		filename = name;
	}
	private String format(LogRecord record)
	{
		String ret = "";
		if (record.getLevel().equals(Level.WARNING)) ret = "<p style=\"font-family:courier new; color:orange\">"+new Date().toString()+" : "+record.getMessage()+"</p>\n";
		else 
			if (record.getLevel().equals(Level.SEVERE)) ret = "<p style=\"font-family:courier new; color:red\">"+new Date().toString()+" : "+record.getMessage()+"</p>\n";
			else 
				if (record.getLevel().equals(Level.INFO)) ret = "<p style=\"font-family:courier new; color:black\">"+new Date().toString()+" : "+record.getMessage()+"</p>\n";
		return ret;
	}
	public void publish(LogRecord record)
	{
		if (filename!=null)
		{
			String message = record.getMessage();
			FileWriter file = null;
			try {
				file = new FileWriter(filename+".html",true);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				file.write(format(record));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		    try {
				file.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void close() throws SecurityException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void flush() {
		// TODO Auto-generated method stub
		
	}
}
