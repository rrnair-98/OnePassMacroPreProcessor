import java.util.ArrayList;
public class MacroHeaderParser{

	public static MacroNameEntry processMacroHeader(String line, int mdtLineNumber){
				// this line contains macro name, and ala
			// add this to the mdt 
			boolean alaEntryFound = false;
			boolean spaceFound = false;
			String str = "";
			int strlen = line.length();
			String macroName = "";
			ArrayList<String> ala = new ArrayList<>();
			for(int i = 0; i<strlen; i++){

				char current = line.charAt(i);
				if( spaceFound || i==strlen-1 ){
					if(alaEntryFound ){
					//ala entry
						str = (i == strlen-1)?str+current:str; // when macro ends with a var
						System.out.println("adding to ala "+str);
						ala.add(str);
						alaEntryFound = false;
					}else{
						// mnt entry 
						System.out.println("macro name found "+str);
						
						macroName = str;

					}
					str = "";
					spaceFound = false;


				}
				if (current == ' '){
					spaceFound = true;

				}
				else if( current == '&'){
					// found an ala entry 
					//todo add to ala

					alaEntryFound = true;


				}

				str += current;

			}	

			return new MacroNameEntry(macroName, mdtLineNumber, ala);

	}
}