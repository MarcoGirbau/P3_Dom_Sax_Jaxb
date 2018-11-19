/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import java.io.File;
import java.io.FileOutputStream;
import javax.swing.JFileChooser;
import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.*;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

/**
 * @author Marco Girbau
 */
public class dom 
{
    Document doc = null; //este doc representa al arbol dom
    
    public int abrir_xml_dom (File fichero)
    {
        try 
        {
           DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();//Se crea un objeto DocumentBuilderFactory
           factory.setIgnoringComments(true);//Para que no tenga en cuenta los comments
           factory.setIgnoringElementContentWhitespace(true);//Para que no tenga en cuenta los espacios en blanco
           DocumentBuilder builder = factory.newDocumentBuilder();//Cargamos aqui la estructura del arbol dom a partir del xml
           doc = builder.parse(fichero);
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
            node = nodelist.item(i);
            
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

    String recorrerdomymostrar(Document doc) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    //Método para añadir un nuevo nodo al DOM.
    
    //Método para añadir un nuevo nodo al DOM.
    
    public int annadirDom(Document doc, String titulo, String autor, String anno)
    {
        try
        {
            Node nTitulo=doc.createElement("Titulo");
            Node nTitulo_text= doc.createTextNode(titulo);
            //añadimos el nodo de texto creado como hijo del elemento título.
            nTitulo.appendChild(nTitulo_text);
            //Autor.
            Node nAutor=doc.createElement("Autor");
            Node nAutor_text=doc.createTextNode(autor);
            //añadimos el nodo de texto.
            nAutor.appendChild(nAutor_text);
            //Creamos un nodo de tipo libro.
            Node nLibro=doc.createElement("Libro");
            //Añadimos el atributo.
            ((Element)nLibro).setAttribute("publicado",anno);
            //Se añade a este nodo Libro el nodo autor y titulo creado antes.
            nLibro.appendChild(nTitulo);
            nLibro.appendChild(nAutor);
            
            //Obtenemos el primer nodo del documento y le añadimos como hijo el nodo Libro
            Node raiz=doc.getChildNodes().item(0);
            raiz.appendChild(nLibro);
            
            return 0;
            
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return -1;
        }
    }
    
    public int guardarDomComoFile(String nombreArchivo) 
    {
        try 
        {
            File archivo_xml = new File(nombreArchivo);

            OutputFormat format = new OutputFormat(doc); //especificamos el formato de salida.
            //Especificamos que la salida esté indentada
            format.setIndenting(true);
            //Escribe el contenido en el file.
            XMLSerializer serializer = new XMLSerializer(new FileOutputStream(archivo_xml), format);

            serializer.serialize(doc);

            System.out.println(nombreArchivo);

            return 0;

        } 
        catch (Exception e) 
        {
            System.out.println("No se ha guardado el archivo");
            return -1;
        }

    }
}
