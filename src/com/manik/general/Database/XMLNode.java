//$Id$
package com.manik.general.Database;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLNode{
	private Node node = null;
	private Map<String, String> attributes = new LinkedHashMap<>();
	private NodeList childs = null;
	
	public XMLNode(Node node){
		this.node = node;
		this.childs = node.getChildNodes();
		if(this.node != null && this.node.getNodeType() == Node.ELEMENT_NODE){
			Element e = (Element) this.node;
			NamedNodeMap attrs = e.getAttributes();
			for(int i = 0; i < attrs.getLength(); i++){
				Node attr = attrs.item(i);
				this.attributes.put(attr.getNodeName(), attr.getNodeValue());
			}
		}
	}
	
	public String getAttribute(String key){
		return key != null && this.attributes.containsKey(key) ? this.attributes.get(key) : null;
	}
	
	public Map<String, String> getAllAttributes(){
		return this.attributes;
	}
	
	public boolean hasAttribute(String key){
		return key != null && this.attributes.containsKey(key);
	}
	
	public List<Node> getAllChildrens(){
		return getChildren(null);
	}
	
	public List<Node> getChildren(String name){
		List<Node> childs = new ArrayList<>();
		if(this.childs != null){
			for(int i = 0; i < this.childs.getLength(); i++){
				Node node = this.childs.item(i);
				if(node.getNodeType() == Node.ELEMENT_NODE && (name == null || (name != null && node.getNodeName().equals(name)))){
					childs.add(node);
				}
			}
		}
		return childs;
	}
	
	public Node getFirstChildren(String name){
		Node child = null;
		if(this.childs != null){
			for(int i = 0; i < this.childs.getLength(); i++){
				Node node = this.childs.item(i);
				if(node.getNodeType() == Node.ELEMENT_NODE && (name == null || (name != null && node.getNodeName().equals(name)))){
					child = node;break;
				}
			}
		}
		return child;
	}

	public static List<Node> getChildNodes(NodeList list, String name){
		List<Node> nodes = new ArrayList<>();
		if(list != null && list.getLength() > 0){
			for(int i = 0; i < list.getLength(); i++){
				Node node = list.item(i);
				if(node.getNodeType() == Node.ELEMENT_NODE && (name == null || (name != null && name.equals(node.getNodeName())))){
					nodes.add(node);
				}
			}
		}
		return nodes;
	}
}
