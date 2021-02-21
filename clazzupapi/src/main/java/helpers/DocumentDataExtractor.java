package helpers;

import java.io.FileInputStream;

import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

public class DocumentDataExtractor {

	public static void main(String[] args) throws Exception {

		try {
			String URL = "E:\\Mandroid\\Projects\\Kale Sir\\Client Given\\Exam Papers\\2.docx";
			XWPFDocument docx = new XWPFDocument(new FileInputStream(URL));

			// using XWPFWordExtractor Class
			XWPFWordExtractor we = new XWPFWordExtractor(docx);
			System.out.println(we.getText());
		} catch (Exception e) {
			System.err.println(e);
		}

	}
}
