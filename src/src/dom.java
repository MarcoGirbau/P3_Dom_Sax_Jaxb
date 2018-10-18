/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Document;

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
    
    public String recorrerdomymostrar()
    {
        String[] datos_nodos = null;
        String salida = "";
        Node node;
        
        Node raiz = doc.getFirstChild();//Obtenemos el primer nodo
        NodeList nodelist = raiz.getChildNodes();//Obtenemos una lista de nodos con todos los nodos del hijo raiz
        
        for(int i = 0; i < nodelist.getLength(); i++)//Procesamos todos los nodos hijos
        {
            node = nodelist.item(0);
            
            if(node.getNodeType() == Node.ELEMENT_NODE)
            {
                datos_nodos = procesarLibro(node);//nodo libro
                
                salida = salida + "\n" + "Publicado en " + datos_nodos[0];
                salida = salida + "\n" + "El autor es " + datos_nodos[2];
                salida = salida + "\n" + "El titulo es " + datos_nodos[1];
                salida = salida + "\n -----------";
            }
        }
        return salida;
    }
    
    public String[] procesarLibro(Node n)
    {
        String[] datos = new String[3];
        Node ntemp = null;
        int contador = 1;
        
        datos[0] = n.getAttributes().item(0).getNodeValue();//Obtenemos el valor del primer atributo del nodo
        NodeList nodos = n.getChildNodes();//Obtenemos los hijos del libro 
        
        for(int i = 0; i < nodos.getLength(); i++)
        {
            ntemp = nodos.item(i);
            if(ntemp.getNodeType() == Node.ELEMENT_NODE)
            {
                //Para obtener el texto con el titulo y autor se accede al nodo TEXT hijo de ntemp y se saca su valor 
                datos[contador] = ntemp.getChildNodes().item(0).getNodeValue();
                contador++;
            }
        }
        return datos;
    }
}
