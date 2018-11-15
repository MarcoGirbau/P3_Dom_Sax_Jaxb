/*
 * Cuando encuentre la etiqueta libros que imprima "Estos son los libros del documento seleccionado:".
 */
package src;

import java.io.File;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author Marco Girbau
 */
public class sax 
{
    SAXParser parser;
    File ficheroXML;
    ManejadorSAX sh;
    
    public int abrir_xml_sax(File fichero)
    {
        try 
        {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            parser = factory.newSAXParser();
            sh = new ManejadorSAX();
            ficheroXML = fichero;
            return 0;
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            return -1;
        }
    }
    public String recorrersaxymostrar(File fXML, ManejadorSAX sh, SAXParser parser)//la verdad es que utilizar variables con nombres normales acaba siendo util para saber lo que es cada cosa xd
    {
        try
        {
            parser.parse(fXML,sh);
            return sh.cadena_resultado;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return "error al parsear con SAX";
        }        
    }
}

class ManejadorSAX extends DefaultHandler
{
    int ultimoelemento;
    String cadena_resultado = "";
    String aux = "";
    
    public ManejadorSAX()
    {
        ultimoelemento = 0;
    }
    
    @Override public void startElement(String uri, String localName, String qName, Attributes atts)throws SAXException
    {
        if(qName.equals("libro"))
        {
            cadena_resultado = cadena_resultado + "\nPublicado en;" + atts.getValue(atts.getQName(0)) + "\n"; 
            ultimoelemento = 1;
        }
        else if(qName.equals("titulo"))
        {
            ultimoelemento = 2;
            cadena_resultado = cadena_resultado + "\nEl título es;";
        }
        else if(qName.equals("autor"))
        {
            ultimoelemento = 3;
            cadena_resultado = cadena_resultado + "\nEl autor es;";
        }
        else if(qName.equals("libros"))//Mejora del sax original
        {
            aux = "Se van a mostrar los libros de este documento: ";
            cadena_resultado = aux + cadena_resultado;
        }
    }
    
    @Override public void endElement(String uri, String localName, String qName)throws SAXException
    {
        if(qName.equals("libro"))
        {
            System.out.println("He encontrado el final de un elemento");
            cadena_resultado = cadena_resultado + "\n ---------------------";
        }
    }
    
    @Override public void characters (char[] ch, int start, int length) throws SAXException
    {
        if(ultimoelemento == 2)
        {
            for(int i = start; i < length + start; i++)
            {
                cadena_resultado = cadena_resultado + ch[i];
            }
        }
        else if(ultimoelemento == 3)
        {
            for(int i = start; i < length + start; i++)
            {
                cadena_resultado = cadena_resultado + ch[i];
            }
        }
    }  
}
