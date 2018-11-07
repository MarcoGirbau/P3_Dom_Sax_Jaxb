/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

import java.io.File;
import java.util.jar.Attributes;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import jdk.internal.org.xml.sax.SAXException;
import jdk.internal.org.xml.sax.helpers.DefaultHandler;

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
}

class ManejadorSAX extends DefaultHandler
{
    int ultimoelemento;
    String cadena_resultado = "";
    
    public ManejadorSAX()
    {
        ultimoelemento = 0;
    }
    
    public void startElement (String uri, String localName, String qName, Attributes atts) throws SAXException
    {
        if(qName.equals("Libro"))
        {
            //cadena_resultado = cadena_resultado + "\nPublicado en: " + atts.getValue(atts.getqName(0)) + "\n ";
            ultimoelemento = 1;
        }
        else if(qName.equals("Titulo"))
        {
            ultimoelemento = 2;
            cadena_resultado = cadena_resultado + "\nEl titulo es: ";
        }
        else if(qName.equals("Autor"))
        {
            ultimoelemento = 3;
            cadena_resultado = cadena_resultado + "\nEl autor es: ";
        }
    }
    public void endElement (String uri, String localName, String qName, Attributes atts) throws SAXException
    {
        if(qName.equals("Libro"))
        {
            System.out.println("He encontrado el final de un elemento.");
            cadena_resultado = cadena_resultado + "\n ------------------";
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
