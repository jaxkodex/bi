package bi.colegios.data.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class ParserMain {

	public static void main(String[] args) {
		try {
			File f = new File("/home/jaxkodex/Documentos/Tesis II/data/2013.zip");
			InputStream is = new FileInputStream(f);
			UploadDataParser udp = new UploadDataParser(is);
			udp.parse();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
