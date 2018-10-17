/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

import java.io.File;
import javax.swing.text.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Node;

/**
 *
 * @author xp
 */
public class dom 
{
    Document doc = null; //este doc representa al al arbol dom
    
    public int abrir_xml_dom (File Fichero)
    {
        try 
        {
           DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();//Se crea un objeto DocumentBuilderFactory
           factory.setIgnoringComments(true);//Para que no tenga en cuenta los comments
           factory.setIgnoringElementContentWhitespace(true);//Para que no tenga en cuenta los espacios en blanco
           DocumentBuilder builder = factory.newDocumentBuilder();//Cargamos aqui la estructura del arbol dom a partir del xml
           doc = (Document) builder.parse(Fichero);
           return 0;
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            return -1;
        }
    }
}
