package de.diavololoop.util.yml;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Set;

public class Setting extends SettingNode{
	
	public static int INDENTION = 4;
	
	
	private LinkedHashMap<String, SettingNode> nodes = new LinkedHashMap<String, SettingNode>();	
	
	Setting parent;
	
	public Setting(String nodeName) {
		super(nodeName);
	}
	
	public Setting(){
		super("root");
	}


	
	public double getDouble(String key){
		return getDouble(key.split("\\."), 0);
	}
	private double getDouble(String[] key, int pos){
		SettingNode node = nodes.get(key[pos]);
		
		if(key.length-pos == 1){	
			
			if(node instanceof SettingValue){
				return ((SettingValue)node).getDoubleValue();
			}
			return 0;
			
		}
		if(node instanceof SettingValue){
			return 0;
		}
		if(node == null){
			return 0;
		}
		return ((Setting)node).getDouble(key, pos+1);
	}
	
	public double[] getDoubleArray(String key){
		return getDoubleArray(key.split("\\."), 0);
	}
	private double[] getDoubleArray(String[] key, int pos){
		SettingNode node = nodes.get(key[pos]);
		
		if(key.length-pos == 1){	
			
			if(node instanceof SettingValue){
				return ((SettingValue)node).getDoubleArray();
			}
			return new double[]{};
			
		}
		if(node instanceof SettingValue){
			return new double[]{};
		}
		return ((Setting)node).getDoubleArray(key, pos+1);
	}
	
	
	public int getInt(String key){
		return getInt(key.split("\\."), 0);
	}
	private int getInt(String[] key, int pos){
		SettingNode node = nodes.get(key[pos]);
		
		if(key.length-pos == 1){	
			
			if(node instanceof SettingValue){
				return ((SettingValue)node).getIntValue();
			}
			return 0;
			
		}
		if(node instanceof SettingValue){
			return 0;
		}
		return ((Setting)node).getInt(key, pos+1);
	}
	
	public String getString(String key){
		return getString(key.split("\\."), 0);
	}
	private String getString(String[] key, int pos){
		SettingNode node = nodes.get(key[pos]);
		
		if(key.length-pos == 1){	
			
			if(node instanceof SettingValue){
				return ((SettingValue)node).getStringValue();
			}
			return "";
			
		}
		if(node instanceof SettingValue){
			return "";
		}
		return ((Setting)node).getString(key, pos+1);
	}
	
	public boolean getBoolean(String key){
		return getBoolean(key.split("\\."), 0);
	}
	private boolean getBoolean(String[] key, int pos){
		SettingNode node = nodes.get(key[pos]);
		
		if(key.length-pos == 1){	
			
			if(node instanceof SettingValue){
				return ((SettingValue)node).getBooleanValue();
			}
			return false;
			
		}
		if(node instanceof SettingValue){
			return false;
		}
		return ((Setting)node).getBoolean(key, pos+1);
	}
	
	
	public void createDouble(String key, double value){
		createDouble(key.split("\\."), 0, value);
	}
	private void createDouble(String[] key, int pos, double value){
		String keyname = key[pos];
		SettingNode node = nodes.get(keyname);
		
		if(key.length-pos == 1){	
			
			if(node == null){
				node = new SettingValue(keyname);
				((SettingValue)node).setDoubleValue(value);
				nodes.put(keyname, node);
				return;
			}
			
			if(node instanceof SettingValue){
				((SettingValue)node).setDoubleValue(value);
				return;
			}
			return;
			
		}
		if(node == null){
			node = new Setting(key[pos]);
			((Setting)node).createDouble(key, pos+1, value);
			nodes.put(keyname, node);
			return;
		}
		if(node instanceof SettingValue){
			return;
		}
		
		((Setting)node).createDouble(key, pos+1, value);
	}
	
	public void createInt(String key, int value){
		createInt(key.split("\\."), 0, value);		
	}
	private void createInt(String[] key, int pos, int value){
		String keyname = key[pos];
		SettingNode node = nodes.get(keyname);
		
		if(key.length-pos == 1){	
			
			if(node == null){
				node = new SettingValue(keyname);
				((SettingValue)node).setIntValue(value);
				nodes.put(keyname, node);
				return;
			}
			
			if(node instanceof SettingValue){
				((SettingValue)node).setIntValue(value);
				return;
			}
			return;
			
		}
		if(node == null){
			node = new Setting(key[pos]);
			((Setting)node).createInt(key, pos+1, value);
			nodes.put(keyname, node);
			return;
		}
		if(node instanceof SettingValue){
			return;
		}
		
		((Setting)node).createInt(key, pos+1, value);
	}
	
	public void createString(String key, String value){
		createString(key.split("\\."), 0, value);
	}
	private void createString(String[] key, int pos, String value){
		String keyname = key[pos];
		SettingNode node = nodes.get(keyname);
		
		if(key.length-pos == 1){	
			
			if(node == null){
				node = new SettingValue(keyname);
				((SettingValue)node).setStringValue(value);
				nodes.put(keyname, node);
				return;
			}
			
			if(node instanceof SettingValue){
				((SettingValue)node).setStringValue(value);
				return;
			}
			return;
			
		}
		if(node == null){
			node = new Setting(key[pos]);
			((Setting)node).createString(key, pos+1, value);
			nodes.put(keyname, node);
			return;
		}
		if(node instanceof SettingValue){
			return;
		}
		
		((Setting)node).createString(key, pos+1, value);		
	}
	
	public void createDoubleArray(String key, double[] arr){
		this.createDoubleArray(key.split("\\."), 0, arr);
	}
	private void createDoubleArray(String[] key, int pos, double[] arr){
		String keyname = key[pos];
		SettingNode node = nodes.get(keyname);
		
		if(key.length-pos == 1){	
			
			if(node == null){
				node = new SettingValue(keyname);
				((SettingValue)node).setDoubleArray(arr);
				nodes.put(keyname, node);
				return;
			}
			
			if(node instanceof SettingValue){
				((SettingValue)node).setDoubleArray(arr);
				return;
			}
			return;
			
		}
		if(node == null){
			node = new Setting(key[pos]);
			((Setting)node).createDoubleArray(key, pos+1, arr);
			nodes.put(keyname, node);
			return;
		}
		if(node instanceof SettingValue){
			return;
		}
		
		((Setting)node).createDoubleArray(key, pos+1, arr);
	}
	
	public void createBoolean(String key, boolean value){
		this.createBoolean(key.split("\\."), 0, value);
	}
	private void createBoolean(String[] key, int pos, boolean value){
		String keyname = key[pos];
		SettingNode node = nodes.get(keyname);
		
		if(key.length-pos == 1){	
			
			if(node == null){
				node = new SettingValue(keyname);
				((SettingValue)node).setBooleanValue(value);
				nodes.put(keyname, node);
				return;
			}
			
			if(node instanceof SettingValue){
				((SettingValue)node).setBooleanValue(value);
				return;
			}
			return;
			
		}
		if(node == null){
			node = new Setting(key[pos]);
			((Setting)node).createBoolean(key, pos+1, value);
			nodes.put(keyname, node);
			return;
		}
		if(node instanceof SettingValue){
			return;
		}
		
		((Setting)node).createBoolean(key, pos+1, value);
	}
	
	public Setting getSubSetting(String key){
		return this.getSubSetting(key.split("\\."), 0);
	}
	
	private Setting getSubSetting(String key[], int pos){
		SettingNode node = nodes.get(key[pos]);
		
		if(node == null){
			throw new IllegalStateException(String.format("element not found: %s in %s", Arrays.toString(key), nodeName));
		}
		if(!(node instanceof Setting)){
			throw new IllegalStateException(String.format("element not found: %s in %s", Arrays.toString(key), nodeName));
		}
		if(key.length-pos == 1){
			return (Setting)node;
		}
		return ((Setting)node).getSubSetting(key, pos+1);
		
	}
	

	
	public Set<String> getKeys(String key){
		Setting sub = getSubSetting(key);
		if(sub == null){
			return null;
		}
		return sub.getKeys();
		
	}
	
	public Set<String> getKeys(){
		return nodes.keySet();
	}
	

	public static Setting read(File file) throws IOException{
		BufferedReader reader = null;
		try {
			
			
			reader = new BufferedReader(new FileReader(file));
			Setting result = Setting.read(reader);
			reader.close();
			return result;
			
			
		} catch (IOException e) {
			throw e;
		} finally{
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
	}
	public static Setting read(InputStream stream) throws IOException{
		BufferedReader reader = null;
		try {
			
			
			reader = new BufferedReader(new InputStreamReader(stream));
			Setting result = Setting.read(reader);
			reader.close();
			return result;
			
			
		} catch (IOException e) {
			throw e;
		} finally{
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
	}
	
	private static Setting read(BufferedReader reader) throws IOException{
		Setting result = new Setting("root");
			
		int lastIndent = 0;
		Setting currentNode = result;
		int lineNr = 0;
			
		for(String line = reader.readLine(); line != null; line = reader.readLine()){
			int splitter = line.indexOf(":");
			
			if(splitter == -1){
				++lineNr;
				continue;
			}
			
			
			String first = line.substring(0, splitter).trim();
			String last  = line.substring(splitter+1).trim();
			int indent = Setting.getIndention(line);
			boolean isNode = last.isEmpty();
				
			if(first.startsWith("#") || (first.isEmpty() && isNode) ){
				++lineNr;
				continue;	
			}
				
			if(indent == lastIndent){
				if(isNode){
					Setting newNode = new Setting(first);
					newNode.parent = currentNode;
					currentNode.nodes.put(first, newNode);
					currentNode = newNode;
					lastIndent = indent + INDENTION;
				}else{
					SettingValue newValue = new SettingValue(first);
					newValue.setStringValue(last);
					currentNode.nodes.put(first, newValue);				
				}
			}else if(indent > lastIndent){
				throw new IOException("YML Syntax Error at "+lineNr);
			}else{
				int difference = lastIndent - indent;
				for(int i = 0; i < difference/INDENTION; ++i){
					currentNode = currentNode.parent;
				}
				lastIndent = indent;
				
				if(isNode){
					Setting newNode = new Setting(first);
					newNode.parent = currentNode;
					currentNode.nodes.put(first, newNode);
					currentNode = newNode;
					lastIndent = indent + INDENTION;
				}else{
					SettingValue newValue = new SettingValue(first);
					newValue.setStringValue(last);
					currentNode.nodes.put(first, newValue);				
				}
				
			}
				
			++lineNr;
		}
			
		return result;

	}
	
	public void write(File file) throws IOException{
		BufferedWriter writer = null;
		
		try{
			writer = new BufferedWriter(new FileWriter(file));
			this.write(writer, -INDENTION, false);
			writer.flush();
			writer.close();
		} catch(IOException e){
			throw e;
		} finally{
			if(writer != null){
				writer.close();	
			}
		}
		
	}
	
	private void write(BufferedWriter writer, int indent, boolean showFirst) throws IOException{
		
		if(showFirst){
			Setting.writeIndent(writer, indent);
			writer.write(nodeName);
			writer.write(':');
			writer.newLine();
		}
		
		for(SettingNode s: nodes.values()){
			if(s instanceof SettingValue){
				SettingValue value = (SettingValue)s;
				Setting.writeIndent(writer, indent+INDENTION);
				writer.write(value.nodeName);
				writer.write(": ");
				writer.write(value.getStringValue());
				writer.newLine();
			}else if(s instanceof Setting){
				Setting setting = (Setting)s;
				setting.write(writer, indent+INDENTION, true);
			}
		}
	}
	
	@Override
	public String toString(){
		String result = this.nodeName+":\r\n";
		for(SettingNode s: nodes.values()){
			if(s instanceof Setting){
				result = result + s.toString();
			}else if(s instanceof SettingValue){
				result = result + s.toString();
			}
		}
		result = result + "\r\n";
		return result;
		
	}
	
	private static void writeIndent(BufferedWriter writer, int indent) throws IOException{
		for(int i = 0; i < indent; ++i){
			writer.write(' ');
		}
	}
	
	private static int getIndention(String line){
		for(int i = 0; i < line.length(); ++i){
			if(line.charAt(i) != ' '){
				return i;
			}
		}
		return line.length();
	}


}
