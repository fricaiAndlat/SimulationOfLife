package de.diavololoop.util.yml;


public class SettingValue extends SettingNode{
	
	private String value;
	
	
	public SettingValue(String nodeName) {
		super(nodeName);
	}
	
	public String getStringValue(){
		return value;
	}

	public int getIntValue(){
		if(!value.matches("[+-]?\\d+")){
			return 0;
		}
		return Integer.parseInt(value);
	}
	
	public double getDoubleValue(){
		if(!value.matches("[+-]?\\d*\\.\\d*")){
			return 0;
		}
		return Double.parseDouble(value);
	}
	public double[] getDoubleArray() {
		String[] values = value.split(" ");
		double[] result = new double[values.length];
		
		for(int i = 0; i < values.length; ++i){
			if(!values[i].matches("[+-]?\\d*\\.\\d*")){
				result[i] = 0;
			}else{
				result[i] = Double.parseDouble(values[i]);
			}
		}
		return result;
	}
	public boolean getBooleanValue(){
		return Boolean.parseBoolean(value);
	}
	
	
	public void setStringValue(String value){
		this.value = value;
	}
	public void setIntValue(int value){
		this.value = Integer.toString(value);
	}
	public void setDoubleValue(double value){
		this.value = Double.toString(value);
	}
	public void setDoubleArray(double[] arr){
		StringBuilder builder = new StringBuilder();
		for(double v: arr){
			builder.append(v);
			builder.append(' ');
		}
		this.value = builder.toString();
	}
	public void setBooleanValue(boolean value){
		this.value = Boolean.toString(value);
	}
	@Override
	public String toString(){
		return this.nodeName + ": " + value + "\r\n";
	}

	
}
