import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.IOException;
import java.util.ArrayList;
/**
A one pass macro pre processor
for IBM 360
**/
public class MacroPreProcessor{
	private final static HashMap<String, MacroNameEntry> mnt;
	private final static ArrayList<String> macroDefinitionTable;
	static{
		mnt = new HashMap();
		macroDefinitionTable = new ArrayList();
	}

	private File file;

	public MacroPreProcessor(File macroFile){
		this.file = macroFile;
	}


	public void exec(){
		try{

			Scanner sc = new Scanner(this.file);

			int currentChar = 0;
			StringBuilder sb = new StringBuilder("");
			boolean foundMacroKeyword = false;
			boolean mdtEntry = false;

			// make this a function and add macro count to add macro within macro

			while (sc.hasNextLine()){
				String line = sc.nextLine();

				if( foundMacroKeyword ){
					MacroNameEntry m = MacroHeaderParser.processMacroHeader(line, MacroPreProcessor.macroDefinitionTable.size()+1);
					foundMacroKeyword = false;
					mnt.put(m.getMacroName(), m);
					mdtEntry = true;
					//macro definition starts from current line
				}


				if(mdtEntry){

					this.macroDefinitionTable.add(line);

				}
				if(line.contains("MACRO")){
					foundMacroKeyword = true;
					continue;
				}

				else if(line.contains("MEND")){

					mdtEntry = false;
				}
				else{
					if(!mdtEntry)
						this.processLine(line);
				}
				





				



			}

		}catch(IOException ioe){
			ioe.printStackTrace();
		}
	}




	private void processLine(String line){
		/**
			This method is searching for a macro call.
			Will instantiate a MacroCallExpander.
		**/
		String str="", previousWord;
		boolean macroCallLine;
		int strLen = line.length();
		String arr[]=new String[1];
		String macroName ="";
		for(int i=0; i<strLen; i++){
			char current = line.charAt(i);

			if(mnt.containsKey(str)){
				macroCallLine = true;
			}

			if(current == 32 || current == ','){
				// word search in mnt
				// or 
				// ala entry
				str = str + current;
				if(mnt.containsKey(str)){
					//this line contains a macro call ... must expand
					macroName = str;
					arr = line.trim().split(" ");

					System.out.println("found macro call "+line);
					ArrayList<String> list = new ArrayList();
					for(String st: arr){
						if(st != null && !st.equals(str.trim())){
							list.add(st);
						}
					}

						MacroNameEntry entry = mnt.get(macroName);

					MacroCallExpander callExpander = new MacroCallExpander(line, macroDefinitionTable, entry);
					System.out.println("EXPANSION \n"+callExpander.expand(list));

					break;
				}
				previousWord = str;
				str = "";
				continue;
			}
			str = str + current;

		}


		//todo call the MacroExpander.


	}


	

	public static void main(String ar[]){
		MacroPreProcessor p = new MacroPreProcessor(new File("input.txt"));
		p.exec();
	}


}