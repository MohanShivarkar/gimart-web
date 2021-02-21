package helpers;

import java.io.*;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlbeans.XmlCursor;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.officeDocument.x2006.math.CTOMath;
import org.openxmlformats.schemas.officeDocument.x2006.math.CTOMathPara;

import org.w3c.dom.Node;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.transform.stream.StreamResult;

import java.awt.Desktop;

import java.util.List;
import java.util.ArrayList;

public class WordReadFormulas {

	static File stylesheet = new File(
			"E:\\Mandroid\\Projects\\Dr Raj\\Extracting-Math-formulas-using-Apache-poi-in-java-master\\Extracting-Math-formulas-using-Apache-poi-in-java-master\\OMML2MML.XSL");
	static TransformerFactory tFactory = TransformerFactory.newInstance();
	static StreamSource stylesource = new StreamSource(stylesheet);

	static String getMathML(CTOMath ctomath) throws Exception {
		Transformer transformer = tFactory.newTransformer(stylesource);

		Node node = ctomath.getDomNode();

		DOMSource source = new DOMSource(node);
		StringWriter stringwriter = new StringWriter();
		StreamResult result = new StreamResult(stringwriter);
		transformer.setOutputProperty("omit-xml-declaration", "yes");
		transformer.transform(source, result);

		String mathML = stringwriter.toString();
		stringwriter.close();

		// The native OMML2MML.XSL transforms OMML into MathML as XML having special
		// name spaces.
		// We don't need this since we want using the MathML in HTML, not in XML.
		// So ideally we should changing the OMML2MML.XSL to not do so.
		// But to take this example as simple as possible, we are using replace to get
		// rid of the XML specialities.
		mathML = mathML.replaceAll("xmlns:m=\"http://schemas.openxmlformats.org/officeDocument/2006/math\"", "");
		mathML = mathML.replaceAll("xmlns:mml", "xmlns");
		mathML = mathML.replaceAll("mml:", "");

		return mathML;
	}

	public static void main(String[] args) throws Exception {

		String URL = "E:\\Mandroid\\Projects\\Kale Sir\\Client Given\\Exam Papers\\2.docx";
		XWPFDocument document = new XWPFDocument(new FileInputStream(URL));

		try (FileInputStream fis = new FileInputStream(URL)) {
			XWPFDocument file = new XWPFDocument(OPCPackage.open(fis));
			XWPFWordExtractor ext = new XWPFWordExtractor(file);
			// System.out.println(ext.getText());
		} catch (Exception e) {
			System.out.println(e);
		}

		// storing the found MathML in a AllayList of strings
		List<String> mathMLList = new ArrayList<String>();

		// getting the formulas out of all body elements
		for (IBodyElement ibodyelement : document.getBodyElements()) {
			// System.out.println(ibodyelement.getBody());
			// System.out.println(ibodyelement.getElementType());
			// ibodyelement.getBody();
			// ibodyelement.getElementType();

			if (ibodyelement.getElementType().equals(BodyElementType.PARAGRAPH)) {

				XWPFParagraph paragraph = (XWPFParagraph) ibodyelement;
				// XmlCursor cursor= paragraph.getBody()
				System.out.println(paragraph.getCTP().getOMathList());
				// System.out.println("ctomath:="+paragraph.getCTP().getOMathList().get(0));
				System.out.println(paragraph.getText());
				for (CTOMath ctomath : paragraph.getCTP().getOMathList()) {
					System.out.println("ctomath:=" + ctomath);
					System.out.println("MAth Equation:=" + getMathML(ctomath));
					mathMLList.add(getMathML(ctomath));
				}
				for (CTOMathPara ctomathpara : paragraph.getCTP().getOMathParaList()) {
					for (CTOMath ctomath : ctomathpara.getOMathList()) {
						mathMLList.add(getMathML(ctomath));
					}
				}
			} else if (ibodyelement.getElementType().equals(BodyElementType.TABLE)) {
				XWPFTable table = (XWPFTable) ibodyelement;
				for (XWPFTableRow row : table.getRows()) {
					for (XWPFTableCell cell : row.getTableCells()) {
						for (XWPFParagraph paragraph : cell.getParagraphs()) {
							for (CTOMath ctomath : paragraph.getCTP().getOMathList()) {
								mathMLList.add(getMathML(ctomath));
							}
							for (CTOMathPara ctomathpara : paragraph.getCTP().getOMathParaList()) {
								for (CTOMath ctomath : ctomathpara.getOMathList()) {
									mathMLList.add(getMathML(ctomath));
								}
							}
						}
					}
				}
			}
		}

		int i = 1;
		for (String mathML : mathMLList) {
			System.out.println(" (" + i + ") " + mathML);
		}

		// document

		// creating a sample HTML file
		String encoding = "UTF-8";
		FileOutputStream fos = new FileOutputStream("result.html");
		OutputStreamWriter writer = new OutputStreamWriter(fos, encoding);
		writer.write("<!DOCTYPE html>\n");
		writer.write("<html lang=\"en\">");
		writer.write("<head>");
		writer.write("<meta charset=\"utf-8\"/>");

		// using MathJax for helping all browsers to interpret MathML
		writer.write("<script type=\"text/javascript\"");
		writer.write(" async src=\"https://cdnjs.cloudflare.com/ajax/libs/mathjax/2.7.1/MathJax.js?config=MML_CHTML\"");
		writer.write(">");
		writer.write("</script>");

		writer.write("</head>");
		writer.write("<body>");
		writer.write("<p>Following formulas was found in Word document: </p>");

		int i1 = 1;
		for (String mathML : mathMLList) {
			writer.write("<p>Formula" + i1++ + ":</p>");
			writer.write(mathML);
			writer.write("<p/>");
			System.out.println(" (" + i1 + ") " + mathML);
		}

		writer.write("</body>");
		writer.write("</html>");
		writer.close();

		Desktop.getDesktop().browse(new File("C:\\Users\\Ajaz PC\\Desktop\\New folder\\result.html").toURI());

//		try {
//			String URL = "E:\\Mandroid\\Projects\\Dr Raj\\Extracting-Math-formulas-using-Apache-poi-in-java-master\\Extracting-Math-formulas-using-Apache-poi-in-java-master\\Docdemo.docx";
//	        FileInputStream fis=new FileInputStream(URL);
//	        //this class is used to extract the content
//	        XWPFDocument docx=new XWPFDocument(fis);
//	        List<XWPFParagraph> paragraphList=docx.getParagraphs();
//	        for(XWPFParagraph paragraph:paragraphList){
//	            System.out.println(paragraph.getParagraphText());
//	        }
//		} catch (Exception e) {
//			// TODO: handle exception
//			System.out.println(e);
//		}

	}

}
