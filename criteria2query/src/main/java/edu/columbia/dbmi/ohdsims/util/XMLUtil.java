package edu.columbia.dbmi.ohdsims.util;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import edu.columbia.dbmi.ohdsims.pojo.Attribute;
import edu.columbia.dbmi.ohdsims.pojo.Criteria;
import edu.columbia.dbmi.ohdsims.pojo.Entity;

public class XMLUtil {

	public static List<Criteria> parseXML(String xmlstr) {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(new InputSource(new StringReader(xmlstr)));

			NodeList list = doc.getElementsByTagName("sent");
			List<Criteria> criterialist = new ArrayList<Criteria>();
			for (int i1 = 0; i1 < list.getLength(); i1++) {
				HashSet<Attribute> ahs = new HashSet<Attribute>();
				Criteria criteria = new Criteria();
				Element element = (Element) list.item(i1);
				String content = element.getElementsByTagName("text").item(0).getTextContent();
				criteria.setSentence(content);
				NodeList nl = element.getElementsByTagName("entity");
				List<Entity> entities = new ArrayList<Entity>();
				for (int i = 0; i < nl.getLength(); i++) {
					Entity entity = new Entity();
					String entityname = nl.item(i).getTextContent();
					String entityclass = nl.item(i).getAttributes().getNamedItem("class").getTextContent();
					String index = nl.item(i).getAttributes().getNamedItem("index").getTextContent();
					int startindex = Integer.valueOf(nl.item(i).getAttributes().getNamedItem("start").getTextContent());
					if (nl.item(i).getAttributes().getNamedItem("relation") == null) {
						entity.setEntityname(entityname);
						entity.setDomain(entityclass);
						entity.setIndex(index);
						entity.setRelations(null);
						entity.setStartindex(startindex);
						entities.add(entity);
					} else {
						String relations = nl.item(i).getAttributes().getNamedItem("relation").getTextContent();
						HashMap<String, String> erelations = new HashMap<String, String>();
						if (relations.equals("None") == false) {
							String[] arr = relations.split("\\|");
							for (int j = 0; j < arr.length; j++) {
								erelations.put(arr[j].split(":")[0], arr[j].split(":")[1]);
								System.out.println(arr[j].split(":")[1] + "----" + arr[j].split(":")[0]);
							}
							entity.setEntityname(entityname);
							entity.setDomain(entityclass);
							entity.setIndex(index);
							entity.setRelations(erelations);
							entity.setStartindex(startindex);
							System.out.println("entityname=" + entityname);
							System.out.println("entityclass=" + entityclass);
							System.out.println("index=" + index);
							System.out.println("relations=" + relations);
							entities.add(entity);
						} else {
							entity.setEntityname(entityname);
							entity.setDomain(entityclass);
							entity.setIndex(index);
							entity.setRelations(null);
							entity.setStartindex(startindex);
							entities.add(entity);
						}
					}
				}
				criteria.setEntities(entities);
				NodeList alist = element.getElementsByTagName("attribute");
				for (int i = 0; i < alist.getLength(); i++) {
					Attribute att = new Attribute();
					String attributes = alist.item(i).getTextContent();
					String attributesclass = alist.item(i).getAttributes().getNamedItem("class").getTextContent();
					String index = alist.item(i).getAttributes().getNamedItem("index").getTextContent();
					att.setContent(attributes);
					att.setDomain(attributesclass);
					att.setIndex(index);
					System.out.println("attributes=" + attributes);
					System.out.println("attributesclass=" + attributesclass);
					System.out.println("index=" + index);
					ahs.add(att);
				}
				criteria.setAhs(ahs);
				criterialist.add(criteria);

				// /**
				// * for增强循环遍历
				// */
				// System.out.println("-------------");
				// for (Entity entity : entities) {
				// System.out.println("------entity-------");
				// System.out
				// .println(entity.getEntityname() + " -->" + entity.getDomain()
				// + "-->" + entity.getIndex());
				// for (Map.Entry<String, String> entry :
				// entity.getRelations().entrySet()) {
				// System.out.println("Key = " + entry.getKey() + ", Value = " +
				// entry.getValue());
				//
				// }
				// System.out.println("------entity end-------");
				// }
			}
			return criterialist;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
