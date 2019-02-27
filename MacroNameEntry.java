import java.util.ArrayList;
import java.util.HashMap;
public class MacroNameEntry{
	private String macroName;
	private int mdtOffset;
	private ArrayList<String> argumentListArray;
	public MacroNameEntry(String macroName, int mdtOffset, ArrayList<String> ala){
		this.macroName = macroName;
		this.mdtOffset = mdtOffset;		
		this.argumentListArray = ala;
	}
	
	public HashMap<String, String> prepareInflationArray(ArrayList<String> paramList){
		HashMap hm = new HashMap();
		for(int i=0 ; i<paramList.size(); i++){
			String key = this.argumentListArray.get(i);
			String val = paramList.get(i);
			hm.put(key, val);
		}
		return hm;
	}


	/**
	This method returns an array
	**/
/*	public String replaceAlaDataAsIs(String arr[]){
		return this.
	}*/

	public String replaceAllAlaEntries(String line, ArrayList<String> ala){
		int i =0 ;
		String str;
		for(String alaArg: this.argumentListArray){
			str = ala.get(i++);
			line = line.replace(alaArg.trim(), str);


		}
		return line;
	}
	public String getMacroName(){return this.macroName;}
	public int getMdtOffset(){return this.mdtOffset;}

}