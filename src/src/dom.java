/*
 * Que añada, guarde y luego que pueda modificar
 */
package src;


import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import java.io.File;
import java.io.FileOutputStream;
import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


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
                salida = salida + "\n" + "La editorial es " + datos_nodos[3];
                salida = salida + "\n -----------";
            }
        }
        return salida;
    }
    
    public String[] procesarLibro(Node n)
    {
        String[] datos = new String[4];//Es cuatro porque ponemos la editorial
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
    
    public int annadirDom(Document doc, String titulo, String autor, String anno, String editorial)
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
            
            Node nEditorial=doc.createElement("Editorial");
            Node neditorial_text=doc.createTextNode(editorial);
            
            nEditorial.appendChild(neditorial_text);
            //añadimos el nodo de texto.
            nAutor.appendChild(nAutor_text);
            //Creamos un nodo de tipo libro.
            Node nLibro=doc.createElement("Libro");
            //Añadimos el atributo.
            ((Element)nLibro).setAttribute("publicado",anno);
            //Se añade a este nodo Libro el nodo autor y titulo creado antes.
            nLibro.appendChild(nTitulo);
            nLibro.appendChild(nAutor);
            nLibro.appendChild(nEditorial);
            
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
    
    public int guardardomcomofile(String n) 
    {
        try 
        {
            //Crea un fichero llamado n
            File archivo_xml = new File(n);
            //Especifica el formato de salida
            OutputFormat format = new OutputFormat(doc);
            //Especifica que la salida esté indentada.
            format.setIndenting(true);
            //Escribe el contenido en el FILE
            XMLSerializer serializer = new XMLSerializer(new FileOutputStream(archivo_xml), format);
            serializer.serialize(doc);
            System.out.println("dzfbsdb");
            return 0;
        } catch (Exception e) {
            return -1;
        }
    }
}
